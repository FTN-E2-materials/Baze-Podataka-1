package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionParams;

public class Example01_Query {

	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// Register JDBC driver
			Class.forName(ConnectionParams.DRIVER).newInstance();

			// Open a connection using DriverManager class
			connection = DriverManager.getConnection(ConnectionParams.LOCAL_CONNECTION_STRING,
					ConnectionParams.USERNAME, ConnectionParams.PASSWORD);

			statement = connection.createStatement();

			String query = "select radnik.mbr, ime, prz prezime, count(spr) br_projekata"
					+ " from radnik left outer join radproj on radnik.mbr = radproj.mbr"
					+ " group by radnik.mbr, ime, prz" + " having count(spr)<3"
					+ " order by br_projekata desc, mbr asc";
			resultSet = statement.executeQuery(query);

			System.out.printf("%-4s %-8s %-8s %-2s\n", "MBR", "IME", "PREZIME", "BROJ_PROJEKATA");

			// resultSet.next() method returns false if there are no more rows
			while (resultSet.next()) {
				int mbr = resultSet.getInt("mbr");
				String ime = resultSet.getString(2); // Enumeration starts at 1
				String prezime = resultSet.getString("prezime"); // 'prz' cannot be used because alias is defined
				int broj_projekata = resultSet.getInt("br_projekata"); // either number or alias must be used used for
																		// expression based columns

				System.out.printf("%-4d %-8.8s %-8.8s %-2d\n", mbr, ime, prezime, broj_projekata);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();

		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
