package com.remoer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String ID = "remoer";
	final static String PW = "remoer";
	
	public static Connection getConnection() throws Exception {
		try {
			return DriverManager.getConnection(URL, ID, PW);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("DB 접속 중 오류 발생");
		}
	}

}
