package com.caozj.codegenerate;

/**
 * 代码生成器生成的后台代码类型
 * 
 * @author caozj
 *
 */
public enum CODETYPE {

	JDBC("jdbc"),

	// mybatis对应的代码生成器暂未实现
	MYBATIS("mybatis");

	private String folderName;

	public String getFolderName() {
		return folderName;
	}

	private CODETYPE(String folderName) {
		this.folderName = folderName;
	}
}
