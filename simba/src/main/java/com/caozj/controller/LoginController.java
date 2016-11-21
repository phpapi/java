package com.caozj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.session.SessionUtil;
import com.caozj.framework.util.code.EncryptUtil;
import com.caozj.permission.model.Org;
import com.caozj.permission.model.OrgExt;
import com.caozj.permission.model.Permission;
import com.caozj.permission.model.Role;
import com.caozj.permission.model.User;
import com.caozj.permission.model.UserExt;
import com.caozj.permission.service.RoleService;
import com.caozj.permission.service.UserService;

/**
 * 登陆控制器
 * 
 * @author caozj
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {

  @Value("${administrator.username}")
  private String adminUserName;

  @Value("${administrator.password}")
  private String adminPassword;

  @Value("${login.captcha.enabled}")
  private String captchaEnabled;

  @Value("${key}")
  private String key;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  /**
   * 进入登陆界面
   * 
   * @return
   */
  @RequestMapping("/toLogin.do")
  public String toLogin(HttpServletRequest request, ModelMap model) {
    if (SessionUtil.isLogin(request.getSession())) {
      return "index";
    }
    model.put("captchaEnabled", captchaEnabled);
    return "login";
  }

  /**
   * 登陆
   * 
   * @param userName
   * @param password
   * @param model
   * @param request
   * @return
   */
  @RequestMapping("/login.do")
  public String login(String userName, String password, ModelMap model,
      HttpServletRequest request) {
    if (SessionUtil.isLogin(request.getSession())) {
      return "index";
    }
    String view = null;
    if ("true".equals(captchaEnabled) && !checkCaptcha(request)) {
      view = "login";
      model.put("errMsg", "验证码错误");
    } else if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
      view = "login";
      model.put("errMsg", "用户名和密码不能为空");
    } else if (checkAccount(userName, password, request.getSession())) {
      view = "index";
    } else {
      view = "login";
      model.put("errMsg", "用户名或者密码错误");
    }
    model.put("userName", userName);
    model.put("password", password);
    model.put("captchaEnabled", captchaEnabled);
    return view;
  }

  /**
   * 检查验证码是否正确
   * 
   * @param request
   * @return
   */
  private boolean checkCaptcha(HttpServletRequest request) {
    return SessionUtil.checkCaptcha(request.getSession(), request.getParameter("captcha"));
  }

  /**
   * 退出
   * 
   * @param request
   * @return
   */
  @RequestMapping("/logout.do")
  public String logout(HttpServletRequest request, ModelMap model) {
    SessionUtil.clearSession(request.getSession());
    model.put("captchaEnabled", captchaEnabled);
    return "login";
  }

  /**
   * 检查账号密码是否正确
   * 
   * @param userName
   * @param password
   * @param session
   * @return
   */
  private boolean checkAccount(String userName, String password, HttpSession session) {
    boolean isAdmin = checkAdmin(userName, password);
    if (isAdmin) {
      User user = new User();
      user.setAccount(userName);
      user.setName("超级管理员");
      SessionUtil.setUser(session, user);
      SessionUtil.setAdmin(session);
      return true;
    }
    boolean exist = userService.checkUser(userName, password);
    if (!exist) {
      return false;
    }
    User user = userService.get(userName);
    UserExt userExt = userService.getUserExt(userName);
    List<Role> roleList = userService.listRoleByAccount(userName);
    List<Permission> permissionList = new ArrayList<Permission>();
    for (Role role : roleList) {
      List<Permission> list = roleService.listByRole(role.getName());
      permissionList.addAll(list);
    }
    List<Org> orgList = userService.listOrgByUser(userName);
    List<OrgExt> orgExtList = userService.listOrgExtByUser(userName);
    SessionUtil.setUser(session, user);
    SessionUtil.setUserExt(session, userExt);
    SessionUtil.setRoles(session, roleList);
    SessionUtil.setPermissions(session, permissionList);
    SessionUtil.setOrgs(session, orgList);
    SessionUtil.setOrgExts(session, orgExtList);
    return true;
  }

  /**
   * 验证用户名密码是否为超级管理员
   * 
   * @param userName
   * @param password
   * @return
   */
  private boolean checkAdmin(String userName, String password) {
    String un = EncryptUtil.md5(userName + key);
    if (!adminUserName.equals(un)) {
      return false;
    }
    String md = EncryptUtil.md5(password + key);
    if (!md.equals(adminPassword)) {
      return false;
    }
    return true;
  }
}
