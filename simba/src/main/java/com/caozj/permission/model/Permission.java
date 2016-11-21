package com.caozj.permission.model;

import java.io.Serializable;

import com.caozj.framework.model.ext.ExtTreeNode;

public class Permission extends ExtTreeNode implements Serializable {

	private static final long serialVersionUID = 4619631216312892345L;

	/**
	 * 权限代表的url，多个用,隔开
	 */
	private String url;

	private int parentID;

	// ///扩展属性////
	/**
	 * 树节点状态,easyUI使用
	 */
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

}
