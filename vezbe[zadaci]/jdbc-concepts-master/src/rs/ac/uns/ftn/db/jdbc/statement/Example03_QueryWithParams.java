package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example03_QueryWithParams {

	public static void main(String[] args) {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(generateQuery())) {

			if (!resultSet.isBeforeFirst()) { // check if any data matches criteria
				System.out.println("No data found!");
			} else {
				System.out.printf("%-4s %s\n", "SPR", "NAZIV PROJEKTA");

				while (resultSet.next()) {
					String spr = resultSet.getString(1);
					String nap = resultSet.getString(2);
					System.out.printf("%-4s %s\n", spr, nap);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static String generateQuery() {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("MBR: ");
			String mbr = sc.nextLine();

			String query = "select spr, nap from projekat where spr in(select spr from radproj where mbr = " + mbr + ")";

			return query;
		}

	}

}
