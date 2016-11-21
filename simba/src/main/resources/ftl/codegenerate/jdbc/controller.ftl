package ${packageName}.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
<#if pageType!="treeTable">
import ${packageName}.controller.form.EasyUIPageForm;
import ${packageName}.framework.model.easyui.PageGrid;
import ${packageName}.framework.util.jdbc.Pager;
</#if>
<#if pageType=="treeTable">
import ${packageName}.framework.util.common.FastJsonUtil;
import com.caozj.model.constant.ConstantData;
import javax.servlet.http.HttpServletRequest;
</#if>
import ${packageName}.framework.model.json.JsonResult;
import ${packageName}.framework.util.common.JsonUtil;
import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;

/**
 * ${classDesc} Controller
 * 
 * @author caozj
 *  
 */
@Controller
@RequestMapping("/${firstLower}")
public class ${className}Controller {

	@Autowired
	private ${className}Service ${firstLower}Service;
<#if pageType!="treeTable">
	@RequestMapping("/list.do")
	public String list(ModelMap model) {
		return "${firstLower}/list";
	}
	
	@RequestMapping("/listDataOfEasyUI.do")
	public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form) {
		Pager page = new Pager((form.getPage()-1) * form.getRows(), form.getRows());
		List<${className}> list = ${firstLower}Service.page(page);
		String message = JsonUtil.toJson(new PageGrid(page.getTotalCount(), list));
		model.put("message", message);
		return "message";
	}
	
	@RequestMapping("/toAdd.do")
	public String toAdd() {
		return "${firstLower}/add";
	}
	
	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		${className} ${firstLower} = ${firstLower}Service.get(id);
		model.put("${firstLower}", ${firstLower});
		return "${firstLower}/update";
	}
</#if>	


<#if pageType=="treeTable">
	private static final String rootName = "${classDesc}树";

	@RequestMapping("/list.do")
	public String list(Integer parentID, ModelMap model) {
		if (parentID == null) {
			parentID = ConstantData.TREE_ROOT_ID;
		}
		String parentName = rootName;
		if (parentID != ConstantData.TREE_ROOT_ID) {
			parentName = ${firstLower}Service.get(parentID).getText();
		}
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		model.put("parentID", parentID);
		model.put("parentName", parentName);
		return "${firstLower}/list";
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
	@RequestMapping("/listChildren${className}.do")
	public String listChildren${className}(Integer id, Boolean showRoot, ModelMap model, HttpServletRequest request) {
		int parentID = ConstantData.TREE_ROOT_ID;
		if (id != null) {
			parentID = id;
		} else if (showRoot != null && showRoot) {
			${className} root = buildRoot${className}();
			model.put("message", JsonUtil.toJson(Arrays.asList(root)));
			return "message";
		}
		List<${className}> list = ${firstLower}Service.listChildren(parentID);
		model.put("message", FastJsonUtil.toJson(list));
		return "message";
	}
	
	private ${className} buildRoot${className}() {
		${className} root = new ${className}();
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
		return "${firstLower}/add";
	}
	
	@RequestMapping("/toUpdate.do")
	public String toUpdate(int id, ModelMap model) {
		${className} ${firstLower} = ${firstLower}Service.get(id);
		model.put("${firstLower}", ${firstLower});
		model.put("rootID", ConstantData.TREE_ROOT_ID);
		return "${firstLower}/update";
	}
</#if>	

	@RequestMapping("/add.do")
	public String add(${className} ${firstLower}, ModelMap model) {
		${firstLower}Service.add(${firstLower});
		model.put("message", new JsonResult().toJson());
		return "message";
	}

	@RequestMapping("/update.do")
	public String update(${className} ${firstLower}, ModelMap model) {
		${firstLower}Service.update(${firstLower});
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/batchDelete.do")
	public String batchDelete(Integer[] ids, ModelMap model) {
		List<Integer> idList = Arrays.asList(ids);
		${firstLower}Service.batchDelete(idList);
		model.put("message", new JsonResult().toJson());
		return "message";
	}
	
	@RequestMapping("/delete.do")
	public String delete(int id, ModelMap model) {
		${firstLower}Service.delete(id);
		model.put("message", JsonUtil.successJson());
		return "message";
	}

	@RequestMapping("/show.do")
	public String show(int id, ModelMap model) {
		${className} ${firstLower} = ${firstLower}Service.get(id);
		model.put("${firstLower}", ${firstLower});
		return "${firstLower}/show";
	}
	
	@RequestMapping("/get.do")
	public String get(int id, ModelMap model) {
		${className} ${firstLower} = ${firstLower}Service.get(id);
		model.put("message", new JsonResult(${firstLower}).toJson());
		return "message";
	}

}
