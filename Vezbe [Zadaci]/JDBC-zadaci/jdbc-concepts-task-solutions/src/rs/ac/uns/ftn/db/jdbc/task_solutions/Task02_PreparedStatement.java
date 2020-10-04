package rs.ac.uns.ftn.db.jdbc.task_solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Task02_PreparedStatement {

	public static void main(String[] args) {

		String query = "select count(*) from radproj where spr = ? and brc > ?";

		try (Scanner sc = new Scanner(System.in);
				Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			String answer;

			do {
				System.out.println("Odaberite opciju:");
				System.out.println("1 - Prikaz broja radnika po sifri projekta i broju casova");
				System.out.println("X - Izlazak iz programa");

				answer = sc.nextLine();

				if (answer.equalsIgnoreCase("1")) {
					countBySprAndBrc(sc, preparedStatement);
				}

			} while (!answer.equalsIgnoreCase("X"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void countBySprAndBrc(Scanner sc, PreparedStatement preparedStatement) throws SQLException {
		System.out.println("Spr: ");
		preparedStatement.setInt(1, Integer.parseInt(sc.nextLine()));

		System.out.println("Brc: ");
		preparedStatement.setInt(2, Integer.parseInt(sc.nextLine()));

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No data found!");
			} else {
				resultSet.next();
				System.out.println("Broj radnika: " + resultSet.getInt(1));

			}
		}
	}
}
