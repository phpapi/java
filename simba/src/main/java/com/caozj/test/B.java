package com.caozj.test;

import java.io.Serializable;
import java.util.Date;

import com.caozj.framework.util.common.SerializeUtil;

public class B implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bName;

	private A a;

	private En en;

	private Date d;

	public En getEn() {
		return en;
	}

	public void setEn(En en) {
		this.en = en;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("B [bName=");
		builder.append(bName);
		builder.append(", a=");
		builder.append(a);
		builder.append(", en=");
		builder.append(en);
		builder.append(", d=");
		builder.append(d);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		b.setA(a);
		b.setbName("b");
		a.setBirthday(new Date());
		a.setId(99);
		a.setName("a");
		byte[] bs = SerializeUtil.serialize(b);
		B rb = (B) SerializeUtil.unserialize(bs);
		System.out.println(rb.toString());
	}

}
