package ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private String connectionURL;
	private Connection connection;
	
	public DatabaseConnection(String username, String password) {
		this.connectionURL = String.format("jdbc:sqlserver://titan.csse.rose-hulman.edu;databaseName=MediaDB;user=%s;password={%s}", username, password);
	}
	
	public boolean connect() {
		boolean connected = false;
		
		try {
			connection = DriverManager.getConnection(connectionURL);
			connected = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(connected);
		
		return connected;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
