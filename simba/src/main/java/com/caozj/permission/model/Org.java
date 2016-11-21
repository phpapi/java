package com.caozj.permission.model;

import java.io.Serializable;

import com.caozj.framework.model.ext.ExtTreeNode;

/**
 * 机构
 * 
 * @author caozj
 *
 */
public class Org extends ExtTreeNode implements Serializable {

	private static final long serialVersionUID = 5050560047226916827L;

	private int parentID;

	/**
	 * 排序
	 */
	private int orderNo;

	// ///扩展属性////
	/**
	 * 树节点状态,easyUI使用
	 */
	private String state;

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

}
