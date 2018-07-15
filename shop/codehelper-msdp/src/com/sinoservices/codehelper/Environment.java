package com.sinoservices.codehelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;

public class Environment implements Serializable {
	private static final long serialVersionUID = 1L;
	private Properties properties = new Properties();

	private Connection conn = null;

	public Environment() {
		load();
	}

	private void load() {
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					"environment.properties");
			this.properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void store(Map map) {
		this.properties.putAll(map);
		try {
			String url = getClass().getClassLoader()
					.getResource("environment.properties").getPath();
			url = URLDecoder.decode(url, "UTF-8");
			OutputStream out = new FileOutputStream(url);
			this.properties.store(out, "");

			this.conn = openNewConnection(this);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Connection getConnection() {
		if (this.conn == null) {
			this.conn = openNewConnection(this);
		}
		return this.conn;
	}

	private Connection openNewConnection(Environment env) {
		Properties properties = new Properties();
		properties.setProperty("remarksReporting", "true");
		properties.setProperty("user", env.getUsername());
		properties.setProperty("password", env.getPassword());
		try {
			Class.forName(env.getDriverClassName());
			return DriverManager.getConnection(env.getUrl(), properties);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public String getProjectPath() {
		return this.properties.getProperty("project.path");
	}

	public String getPackageName() {
		return this.properties.getProperty("package.name");
	}

	public String getMoudleName() {
		return this.properties.getProperty("module.name");
	}

	public String getColSize() {
		return this.properties.getProperty("colSize");
	}

	public String getUrl() {
		return this.properties.getProperty("jdbc.url");
	}

	public String getDriverClassName() {
		return this.properties.getProperty("jdbc.driverClassName");
	}

	public String getUsername() {
		return this.properties.getProperty("jdbc.username");
	}

	public String getPassword() {
		return this.properties.getProperty("jdbc.password");
	}

	public String getAuthor() {
		return this.properties.getProperty("author");
	}

	public String getEmail() {
		return this.properties.getProperty("email");
	}

	public Properties getProperties() {
		return this.properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
