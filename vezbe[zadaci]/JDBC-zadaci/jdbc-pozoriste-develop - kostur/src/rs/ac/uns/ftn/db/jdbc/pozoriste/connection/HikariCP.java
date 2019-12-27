package rs.ac.uns.ftn.db.jdbc.pozoriste.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCP {

	private static HikariConfig hikariConfig = new HikariConfig();
	private static HikariDataSource hikariDS;
	
	static {
		hikariConfig.setJdbcUrl(ConnectionParams.LOCAL_CONNECTION_STRING);
		hikariConfig.setUsername(ConnectionParams.USERNAME);
		hikariConfig.setPassword(ConnectionParams.PASSWORD);
	
		hikariConfig.setMaximumPoolSize(5);
		hikariConfig.addDataSourceProperty("checkPrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCheckSize", "250");
		
		hikariDS = new HikariDataSource(hikariConfig);
	}
	
	private HikariCP() {}
	
	public static Connection getConnection() throws SQLException {
		return hikariDS.getConnection();
	}
	
	
}
