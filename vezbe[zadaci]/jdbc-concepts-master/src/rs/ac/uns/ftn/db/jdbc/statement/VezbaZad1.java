package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;
/*
 * 	Koristeci JDBC API, napisati program koji ce izlistati mbr, ime, prz
 *	radnika koji rade na projektu sa sifrom 10, a ne rade na projektu sa
 *	sifrom 30.
 *
 */


public class VezbaZad1 {

	public static void main(String[] args) {

		try( 	Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(generateQuery()) ){	
			
			if (!resultSet.isBeforeFirst()) { 							// check if any data matches criteria
				System.out.println("No data found!");
			} else {
				

				while (resultSet.next()) {
				
				}
			} /* kraj if-a */
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	private static String generateQuery() {
		
		String query = "";

		return query;
		
	}
	
}
