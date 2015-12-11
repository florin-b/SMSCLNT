package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

	public Connection getProdConnection() throws SQLException {

		String url = "jdbc:oracle:thin:@10.1.3.94:1521:prd002";
		Properties properties = new Properties();
		properties.put("user", "user");
		properties.put("password", "password");

		return DriverManager.getConnection(url, properties);

	}

	public Connection getTestConnection() throws SQLException {

		String url = "jdbc:oracle:thin:@10.1.3.89:1527:tes";
		Properties properties = new Properties();
		properties.put("user", "user");
		properties.put("password", "password");

		return DriverManager.getConnection(url, properties);

	}

}
