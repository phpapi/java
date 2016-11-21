package com.caozj.test;

import com.caozj.codegenerate.DescAnnotation;
import com.caozj.framework.util.common.AnnotationUtil;
import com.caozj.model.RegistryTable;
import com.caozj.permission.model.User;

public class TA {

	public static void main(String[] args) {
		System.out.println(AnnotationUtil.getFieldsAnnotations(RegistryTable.class));
		System.out.println(AnnotationUtil.getClassAnnotations(RegistryTable.class)[0].getClass().getCanonicalName());
		System.out.println(AnnotationUtil.getMethodAnnotations(TController.class, "testChinese")[0].getClass().getCanonicalName());
		System.out.println(AnnotationUtil.getFiledAnnotions(RegistryTable.class, "code")[0].getClass().getCanonicalName());
		System.out.println(AnnotationUtil.getFiledAnnotion(RegistryTable.class, "code", DescAnnotation.class).getClass().getCanonicalName());
		System.out.println(AnnotationUtil.getFiledAnnotions(RegistryTable.class, "id"));
		System.out.println(AnnotationUtil.getClassAnnotation(User.class, DescAnnotation.class));
	}

}
