package com.caozj.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.constant.ConstantData;
import javax.servlet.http.HttpServletRequest;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.model.RegistryType;
import com.caozj.service.RegistryTypeService;

/**
 * 注册类型 Controller
 * 
 * @author caozj
 *  
 */
@Controller
@RequestMapping("/registryType")
public class RegistryTypeController {

	@Autowired
	private RegistryTypeService registryTypeService;


	private static final String rootName = "注册类型树";

	@RequestMapping("/list.do")
	public String list(Integer parentID, ModelMap model) {
		if (parentID == null) {
			parentID = ConstantData.TREE_ROOT_ID;
		}
		String parentName = rootName;
		if (parentID != ConstantData.TREE_ROOT_ID) {
			parentName = registryTypeService.get(parentID).getText();
		}
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		model.put("parentID", parentID);
		model.put("parentName", parentName);
		return "registryType/list";
	}
	
	/**
	 * 获取子数据
	 * 
	 * @param id
	 *            easyui会使用这个参数来传递父节点id
	 * @param showRoot
	 *            是否显示根节点
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/listChildrenRegistryType.do")
	public String listChildrenRegistryType(Integer id, Boolean showRoot, ModelMap model, HttpServletRequest request) {
		int parentID = ConstantData.TREE_ROOT_ID;
		if (id != null) {
			parentID = id;
		} else if (showRoot != null && showRoot) {
			RegistryType root = buildRootRegistryType();
			model.put("message", JsonUtil.toJson(Arrays.asList(root)));
			return "message";
		}
		List<RegistryType> list = registryTypeService.listChildren(parentID);
		model.put("message", FastJsonUtil.toJson(list));
		return "message";
	}
	
	private RegistryType buildRootRegistryType() {
		RegistryType root = new RegistryType();
		root.setId(ConstantData.TREE_ROOT_ID);
		root.setText(rootName);
		root.setState("closed");
		return root;
	}
	
	@RequestMapping("/toAdd.do")
	public String toAdd(Integer parentID, ModelMap model) {
		if (parentID == null) {
			parentID = ConstantData.TREE_ROOT_ID;
		}
		model.put("parentID", parentID);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "registryType/add";
	}
	
	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		RegistryType registryType = registryTypeService.get(id);
		model.put("registryType", registryType);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "registryType/update";
	}

	@RequestMapping("/add.do")
	public String add(RegistryType registryType, ModelMap model) {
		registryTypeService.add(registryType);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/update.do")
	public String update(RegistryType registryType, ModelMap model) {
		registryTypeService.update(registryType);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/batchDelete.do")
	public String batchDelete(Integer[] ids, ModelMap model) {
		List<Integer> idList = Arrays.asList(ids);
		registryTypeService.batchDelete(idList);
		model.put("message", new JsonResult().toJson());
		return "message";
	}
	
	@RequestMapping("/delete.do")
	public String delete(int id, ModelMap model) {
		registryTypeService.delete(id);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/show.do")
	public String show(int id, ModelMap model) {
		RegistryType registryType = registryTypeService.get(id);
		model.put("registryType", registryType);
		return "registryType/show";
	}
	
	@RequestMapping("/get.do")
	public String get(int id, ModelMap model) {
		RegistryType registryType = registryTypeService.get(id);
		model.put("message", new JsonResult(registryType).toJson());
		return "message";
	}

}
