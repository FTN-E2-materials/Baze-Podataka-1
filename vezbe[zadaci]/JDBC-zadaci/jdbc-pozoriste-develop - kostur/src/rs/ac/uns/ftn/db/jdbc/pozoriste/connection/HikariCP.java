package rs.ac.uns.ftn.db.jdbc.pozoriste.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * Klasa koja nam omogucava ConnectionPool iz koga uzimamo konekcije kada nam trebaju
 * a ne da za svaku konekciju opterecujemo bazu.
 * 
 * Na kolokvijumu ce biti potrebno iskucati citavu konekciju da li na ovaj nacin preko Hikari-a 
 * ili neki drugi nacin.
 * 
 */
public class HikariCP {
	
	private static HikariConfig hikariConfig = new HikariConfig();
	private static HikariDataSource hikariDS;
	
	static {							// posto zelimo samo jednom podesiti podesavanja,pravimo staticki blok( pokrece se samo prilikom prvog pokretanje app)
		hikariConfig.setJdbcUrl(ConnectionParams.LOCAL_CONNECTION_STRING);
		hikariConfig.setUsername(ConnectionParams.USERNAME);
		hikariConfig.setPassword(ConnectionParams.PASSWORD);
		
		hikariConfig.setMaximumPoolSize(5);
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		
		hikariDS = new HikariDataSource(hikariConfig);
	}
	
	private HikariCP() {}				// Po defaultu se kreira public konstruktor,mi pravimo private radi ogranicenja instanciranja konekcije
	
	public static Connection getConnection() throws SQLException {
		return hikariDS.getConnection();
	}
	
}
