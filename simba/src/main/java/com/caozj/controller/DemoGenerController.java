package com.caozj.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.controller.form.EasyUIPageForm;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.ext.ExtPageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.DemoGener;
import com.caozj.service.DemoGenerService;

@Controller
@RequestMapping("/demoGener")
public class DemoGenerController {

	@Autowired
	private DemoGenerService demoGenerService;

	@RequestMapping("/list.do")
	public String list(ModelMap model) {
		return "demoGener/list";
	}

	@RequestMapping("/listDataOfExt.do")
	public String listDataOfExt(ModelMap model, int start, int limit) {
		Pager page = new Pager(start, limit);
		List<DemoGener> list = demoGenerService.page(page);
		String message = new JsonResult(new ExtPageGrid(list, page.getTotalCount())).toJson();
		model.put("message", message);
		return "message";
	}
	
	@RequestMapping("/listDataOfEasyUI.do")
	public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form) {
		Pager page = new Pager((form.getPage()-1) * form.getRows(), form.getRows());
		List<DemoGener> list = demoGenerService.page(page);
		String message = JsonUtil.toJson(new PageGrid(page.getTotalCount(), list));
		model.put("message", message);
		return "message";
	}

	@RequestMapping("/toAdd.do")
	public String toAdd() {
		return "demoGener/add";
	}

	@RequestMapping("/add.do")
	public String add(DemoGener demoGener, ModelMap model) {
		demoGenerService.add(demoGener);
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		DemoGener demoGener = demoGenerService.get(id);
		model.put("demoGener", demoGener);
		return "demoGener/update";
	}

	@RequestMapping("/update.do")
	public String update(DemoGener demoGener, ModelMap model) {
		demoGenerService.update(demoGener);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/batchDelete.do")
	public String batchDelete(Integer[] ids, ModelMap model) {
		List<Integer> idList = Arrays.asList(ids);
		demoGenerService.batchDelete(idList);
		model.put("message", new JsonResult().toJson());
		return "message";
	}
	
	@RequestMapping("/delete.do")
	public String delete(int id, ModelMap model) {
		demoGenerService.delete(id);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/show.do")
	public String show(int id, ModelMap model) {
		DemoGener demoGener = demoGenerService.get(id);
		model.put("demoGener", demoGener);
		return "demoGener/show";
	}
	
	@RequestMapping("/get.do")
	public String get(int id, ModelMap model) {
		DemoGener demoGener = demoGenerService.get(id);
		model.put("message", new JsonResult(demoGener).toJson());
		return "message";
	}

}
