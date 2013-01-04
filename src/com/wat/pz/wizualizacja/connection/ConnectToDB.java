package com.wat.pz.wizualizacja.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ProgressMonitor;

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

	public void addData(String x, String y, String index) {
		if (statement != null && conn != null) {
			String query = "INSERT INTO pomiar (WynikX, WynikY, PlotIndex) VALUES ("
					+ x + "," + y + "," + index + ")";

			try {
				// statement.executeQuery(query);

				statement.executeUpdate(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public int countData() {
		int wynik = 0;
		if (statement != null && conn != null) {
			try {
				result = statement.executeQuery("Select count(*) from pomiar");
				if (result != null) {
					result.next();
					wynik = Integer.parseInt(result.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return wynik;
	}

	public int WriteData(int index, int progress, ProgressMonitor monitory, BufferedWriter bf) {
		if (statement != null && conn != null) {
			String query = "Select WynikX, WynikY from pomiar where PlotIndex = "
					+ index;

			try {
				result = statement.executeQuery(query);
				if (result != null) {

					while (result.next()) {
						
						try {
							bf.newLine();
							bf.write(result.getString(1));
							bf.newLine();
							bf.write(result.getString(2));
							bf.newLine();
							bf.write(String.valueOf(index));
							//System.out.println("get" + result.getString(2));
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						progress++;
						
						// System.out.println("odczyt");
						
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return progress;
	}

	
	public void removeAllRows(){
		if (statement != null && conn != null) {
			try {
				statement.executeUpdate("Delete from pomiar");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
