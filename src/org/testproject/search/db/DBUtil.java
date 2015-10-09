package org.testproject.search.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String URL = "jdbc:mysql://localhost:3306/words";
	private static String USER = "root";
	private static String PWD = "root";
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

	static {
		try {
			Class.forName(DRIVER_NAME).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PWD);
	}

}
