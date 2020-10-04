package rs.ac.uns.ftn.db.jdbc.pozoriste.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil_HikariCP {
	private static HikariConfig hikariConfig = new HikariConfig();
	private static HikariDataSource hikariDS;

	static { // needs to be static
		hikariConfig.setJdbcUrl(ConnectionParams.LOCAL_CONNECTION_STRING);
		hikariConfig.setUsername(ConnectionParams.USERNAME);
		hikariConfig.setPassword(ConnectionParams.PASSWORD);
		hikariConfig.setMaximumPoolSize(5); // default - 10
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("", "");
		hikariDS = new HikariDataSource(hikariConfig);
	}

	private ConnectionUtil_HikariCP() {
	}

	public static Connection getConnection() throws SQLException {
		return hikariDS.getConnection();
	}

}
