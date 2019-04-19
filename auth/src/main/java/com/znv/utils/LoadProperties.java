package com.znv.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class LoadProperties {
	
	public static Properties getProperties(String propName) {
		Properties prop = new Properties();
		try {
			File file = ResourceUtils.getFile("classpath:" + propName);
			InputStream in = new FileInputStream(file);
			prop.load(in);
		} catch (Exception e) {
			LogUtil.error(e.toString());
		}
		return prop;
	}

	public static String getProperty(String propName, String key) {
		Properties prop = getProperties(propName);
		return prop.getProperty(key);
	}

	public static void updateProperties(String propName, String keyname,
			String keyvalue) {
		try {
			File file = ResourceUtils.getFile("classpath:" + propName);
			OutputStream fos = new FileOutputStream(file);
			Properties prop = getProperties(propName);
			prop.setProperty(keyname, keyvalue);
			prop.store(fos, "modify at" + new Date().toString());// 保存键值对到文件中
		} catch (IOException e) {
			LogUtil.error(e.toString());
		}
	}

	public static void main(String[] args) {
		String propName = "kafka.properties";
		LoadProperties.updateProperties(propName, "cursor.1", "3");
		System.out.println(LoadProperties.getProperty(propName, "cursor.1"));
	}
}