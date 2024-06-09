package com.denys.dw.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
	private static Properties properties = null;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("D:\\Java_Advanced\\DiplomaWork\\src\\resources\\configuration.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ConfigurationManager() { }
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
