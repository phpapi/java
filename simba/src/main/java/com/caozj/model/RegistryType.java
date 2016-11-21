package com.caozj.model;

import java.io.Serializable;

import com.caozj.codegenerate.DescAnnotation;

/**
 * 注册类型
 * 
 * @author caozj
 *
 */
@DescAnnotation(desc = "注册类型")
public class RegistryType implements Serializable {

	private static final long serialVersionUID = 3417111265402798750L;

	private int id;

	@DescAnnotation(desc = "名称")
	private String text;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistryType [id=");
		builder.append(id);
		builder.append(", text=");
		builder.append(text);
		builder.append(", parentID=");
		builder.append(parentID);
		builder.append("]");
		return builder.toString();
	}

}
