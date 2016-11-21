package com.caozj.model;

import java.io.Serializable;
import java.util.Date;

public class DemoGener implements Serializable {

	private static final long serialVersionUID = 937947466666172749L;

	private int id;

	private String name;

	private Date age;

	private long num;

	private double d;

	private float f;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DemoGener [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", num=");
		builder.append(num);
		builder.append(", d=");
		builder.append(d);
		builder.append(", f=");
		builder.append(f);
		builder.append("]");
		return builder.toString();
	}

}
