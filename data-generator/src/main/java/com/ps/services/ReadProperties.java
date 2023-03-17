package com.ps.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

	private static ReadProperties instance = null;
	private Properties properties = null;

	private ReadProperties() {
		properties = new Properties();
		FileInputStream configInputStream = null;
		try {

			configInputStream = new FileInputStream(new File("config/config.properties"));
			properties.load(configInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				configInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ReadProperties getInstance() {
		if (instance == null)
			instance = new ReadProperties();

		return instance;
	}

	public String getValue(String key) {
		return this.properties.getProperty(key, String.format("The key %s does not exists!", key));
	}
}