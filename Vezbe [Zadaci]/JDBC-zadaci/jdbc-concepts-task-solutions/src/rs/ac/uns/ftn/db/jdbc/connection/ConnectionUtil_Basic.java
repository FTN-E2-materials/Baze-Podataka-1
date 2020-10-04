package rs.ac.uns.ftn.db.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil_Basic {
	private static Connection instance = null;

	public static Connection getConnection() throws SQLException {
		if (instance == null || instance.isClosed()) {
			try {
				// Register JDBC driver
				Class.forName(ConnectionParams.DRIVER).newInstance();

				// Open a connection using DriverManager class
				instance = DriverManager.getConnection(ConnectionParams.LOCAL_CONNECTION_STRING,
						ConnectionParams.USERNAME, ConnectionParams.PASSWORD);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return instance;
	}
}
