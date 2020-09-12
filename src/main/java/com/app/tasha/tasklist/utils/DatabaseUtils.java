package com.app.tasha.tasklist.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;

public class DatabaseUtils {
	
	//@Value("jdbc_driver_name: com.mysql.jdbc.Driver")
	private static String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	//@Value("database_url: jdbc:mysql//localhost/tasklist-database")
	private static String DATABASE_URL = "jdbc:mysql://localhost:3306/tasklist-database";
	//@Value("database_username: root")
	private static String DB_USERNAME = "root";
	//@Value("database_password: Root@123")
	private static String DB_PASSWORD = "Root@123";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER_NAME);
		Connection connection = DriverManager.getConnection(DATABASE_URL, DB_USERNAME, DB_PASSWORD);
		return connection;
	}

}
