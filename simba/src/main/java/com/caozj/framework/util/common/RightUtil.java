package com.caozj.framework.util.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.code.DesUtil;

@Component
public class RightUtil {

	@PostConstruct
	private void init() {
		try {
			check();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	private void check() throws Exception {
		String file = "aEHe+3KV/guJpdTBj06n1JOFXC9+gwAB";
		String key = "xuanxuan";
		String key2 = "1aYPfldV+QSo6rz/S5PIGg==";
		String key3 = DesUtil.decrypt(key2, key);
		File rightFile = new File(ServerUtil.getWebRoot() + "/" + DesUtil.decrypt(file, key));
		DesUtil.decrypt(rightFile, new File(SystemUtil.getTempDir() + "/l"), key3);
		String content = DesUtil.decrypt(FileUtils.readFileToString(new File(SystemUtil.getTempDir() + "/l")), key3);
		String[] info = content.split(";");
		if (new Date().after(new SimpleDateFormat("yyyyMMddHHmmss").parse(info[1]))) {
			System.out.println(info[0]);
			System.exit(-1);
		}
	}
}
