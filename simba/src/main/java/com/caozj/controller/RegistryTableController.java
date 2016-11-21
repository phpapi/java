package com.caozj.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.controller.form.EasyUIPageForm;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.RegistryTable;
import com.caozj.model.constant.ConstantData;
import com.caozj.service.RegistryTableService;
import com.caozj.service.RegistryTypeService;

/**
 * 注册表 Controller
 * 
 * @author caozj
 * 
 */
@Controller
@RequestMapping("/registryTable")
public class RegistryTableController {

	@Autowired
	private RegistryTableService registryTableService;

	@Autowired
	private RegistryTypeService registryTypeService;

	@RequestMapping("/list.do")
	public String list(Integer typeID, ModelMap model) {
		String typeName = "注册类型树";
		if (typeID == null) {
			typeID = ConstantData.TREE_ROOT_ID;
		}
		if (typeID != ConstantData.TREE_ROOT_ID) {
			typeName = registryTypeService.get(typeID).getText();
		}
		model.put("typeID", typeID);
		model.put("typeName", typeName);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "registryTable/list";
	}

	@RequestMapping("/listDataOfEasyUI.do")
	public String listDataOfEasyUI(Integer typeID, ModelMap model, EasyUIPageForm form) {
		Pager page = new Pager((form.getPage() - 1) * form.getRows(), form.getRows());
		List<RegistryTable> list = registryTableService.pageBy("typeID", typeID, page);
		String message = JsonUtil.toJson(new PageGrid(page.getTotalCount(), list));
		model.put("message", message);
		return "message";
	}

	@RequestMapping("/toAdd.do")
	public String toAdd(Integer typeID, ModelMap model) {
		model.put("typeID", typeID);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "registryTable/add";
	}

	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		RegistryTable registryTable = registryTableService.get(id);
		model.put("registryTable", registryTable);
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "registryTable/update";
	}

	@RequestMapping("/add.do")
	public String add(RegistryTable registryTable, ModelMap model) {
		registryTableService.add(registryTable);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/update.do")
	public String update(RegistryTable registryTable, ModelMap model) {
		registryTableService.update(registryTable);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/batchDelete.do")
	public String batchDelete(Integer[] ids, ModelMap model) {
		List<Integer> idList = Arrays.asList(ids);
		registryTableService.batchDelete(idList);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/delete.do")
	public String delete(int id, ModelMap model) {
		registryTableService.delete(id);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/show.do")
	public String show(int id, ModelMap model) {
		RegistryTable registryTable = registryTableService.get(id);
		model.put("registryTable", registryTable);
		return "registryTable/show";
	}

	@RequestMapping("/get.do")
	public String get(int id, ModelMap model) {
		RegistryTable registryTable = registryTableService.get(id);
		model.put("message", new JsonResult(registryTable).toJson());
		return "message";
	}

}
