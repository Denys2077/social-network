package com.denys.dw.connect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectorDB {

private static final String DB_USERNAME = "db.user";
	
	private static final String DB_PASSWORD = "db.password";
	
	private static final String DB_URL = "db.url";
	
	private static final String DB_DRIVER = "db.driver";
	
	private static Properties prop = null;
	
	private static BasicDataSource dataSource;
	
	static {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("D:\\Java_Advanced\\DiplomaWork\\src\\resources\\database.properties"));
			
			dataSource = new BasicDataSource();
			
			dataSource.setDriverClassName(prop.getProperty(DB_DRIVER));
			dataSource.setUrl(prop.getProperty(DB_URL));
			dataSource.setUsername(prop.getProperty(DB_USERNAME));
			dataSource.setPassword(prop.getProperty(DB_PASSWORD));
			
			dataSource.setMaxIdle(10000);
			dataSource.setMinIdle(100);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
}
