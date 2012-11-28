package com.wat.pz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
	private Connection conn;
	private Statement statement;
	private ResultSet result;

	public ConnectToDB() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/pomiar", "root", "21071991");
				statement = conn.createStatement();
			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addData(String value) {
		if (statement != null && conn != null) {
			String query = "INSERT INTO pomiar (WynikY) VALUES (" +value + ")" ;
			
			try {
				//statement.executeQuery(query);
				
				statement.executeUpdate(query);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
}
