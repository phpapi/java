package com.caozj.permission.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.constant.ConstantData;
import com.caozj.permission.model.Permission;
import com.caozj.permission.service.PermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/list.do")
	public String list(Integer parentID,ModelMap model) {
		if (parentID == null) {
			parentID = ConstantData.TREE_ROOT_ID;
		}
		String parentName = "权限树";
		if (parentID != ConstantData.TREE_ROOT_ID) {
			parentName = permissionService.get(parentID).getText();
		}
		model.put("parentID", parentID);
		model.put("parentName", parentName);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "permission/listPermission";
	}

	/**
	 * 获取子权限数据
	 * 
	 * @param node
	 *            ext会使用这个参数来传递父节点id
	 * @param id
	 *            easyui会使用这个参数来传递父节点id
	 * @param showRoot
	 *            是否显示根节点
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/listChildrenPermission.do")
	public String listChildrenPermission(Integer node, Integer id, Boolean showRoot, ModelMap model, HttpServletRequest request) {
		int parentID = ConstantData.TREE_ROOT_ID;
		if (node != null) {
			parentID = node;
		} else if (id != null) {
			parentID = id;
		} else if (showRoot != null && showRoot) {
			Permission permission = buildRootPermission();
			model.put("message", JsonUtil.toJson(Arrays.asList(permission)));
			return "message";
		}
		List<Permission> list = permissionService.listChildren(parentID);
		model.put("message", FastJsonUtil.toJson(list));
		return "message";
	}

	private Permission buildRootPermission() {
		Permission root = new Permission();
		root.setId(ConstantData.TREE_ROOT_ID);
		root.setText("权限树");
		root.setLeaf(false);
		root.setState("closed");
		return root;
	}

	@RequestMapping("/toAdd.do")
	public String toAdd(Integer parentID, ModelMap model) {
		model.put("parentID", parentID);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "permission/addPermission";
	}

	@RequestMapping("/add.do")
	public String add(Permission permission, ModelMap model) {
		permissionService.add(permission);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		Permission permission = permissionService.get(id);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		model.put("permission", permission);
		return "permission/updatePermission";
	}

	@RequestMapping("/update.do")
	public String update(Permission permission, ModelMap model) {
		permissionService.update(permission);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/batchDelete.do")
	public String batchDelete(Integer[] ids, ModelMap model) {
		List<Integer> idList = Arrays.asList(ids);
		permissionService.batchDelete(idList);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/delete.do")
	public String delete(int id, ModelMap model) {
		permissionService.delete(id);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/show.do")
	public String show(int id, ModelMap model) {
		Permission permission = permissionService.get(id);
		model.put("permission", permission);
		return "permission/show";
	}

	@RequestMapping("/get.do")
	public String get(int id, ModelMap model) {
		Permission permission = permissionService.get(id);
		model.put("message", new JsonResult(permission).toJson());
		return "message";
	}

}
