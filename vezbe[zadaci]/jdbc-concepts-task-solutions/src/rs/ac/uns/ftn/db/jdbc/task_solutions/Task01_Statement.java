package rs.ac.uns.ftn.db.jdbc.task_solutions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionParams;

public class Task01_Statement {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName(ConnectionParams.DRIVER).newInstance();

		try (Connection connection = DriverManager.getConnection(ConnectionParams.LOCAL_CONNECTION_STRING,
				ConnectionParams.USERNAME, ConnectionParams.PASSWORD);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(generateQuery())) {

			System.out.println("Radnici: ");
			System.out.printf("%-4s %-8s %-8s\n", "MBR", "IME", "PREZIME");

			while (resultSet.next()) {
				int mbr = resultSet.getInt("mbr");
				String ime = resultSet.getString(2);
				String prezime = resultSet.getString("prezime");

				System.out.printf("%-4d %-8.8s %-8.8s\n", mbr, ime, prezime);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static String generateQuery() {
		String query = "select mbr, ime, prz prezime"
				+ " from radnik where mbr in(select mbr from radproj where spr = 10)"
				+ " and mbr not in(select mbr from radproj where spr = 30)";

		return query;
	}
}
