package com.caozj.model;

import java.io.Serializable;

import com.caozj.framework.model.ext.ExtTreeNode;

/**
 * 菜单
 * 
 * @author caozj
 * 
 */
public class Menu extends ExtTreeNode implements Serializable {

	private static final long serialVersionUID = 962450806979152617L;

	/**
	 * 父菜单id
	 */
	private int parentID;

	/**
	 * 菜单url地址
	 */
	private String url;

	/**
	 * 排序
	 */
	private int orderNo;

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

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [parentID=");
		builder.append(parentID);
		builder.append(", url=");
		builder.append(url);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

}
