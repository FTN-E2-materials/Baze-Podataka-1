package rs.ac.uns.ftn.db.jdbc.prepared_statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example02_QueryWithParams {

	public static void main(String[] args) {

		String query = "select mbr, ime, prz from radnik where ime like ? and prz like ?";

		try (Scanner sc = new Scanner(System.in);
				Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			String answer;

			do {
				System.out.println("Odaberite opciju:");
				System.out.println("1 - Odabir radnika po imenu i prezimenu");
				System.out.println("X - Izlazak iz programa");

				answer = sc.nextLine();

				if (answer.equalsIgnoreCase("1")) {
					selectRadnikByImeAndPrz(sc, preparedStatement);
				}

			} while (!answer.equalsIgnoreCase("X"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void selectRadnikByImeAndPrz(Scanner sc, PreparedStatement preparedStatement) throws SQLException {
		System.out.println("Ime: ");
		preparedStatement.setString(1, sc.nextLine());

		System.out.println("Prezime: ");
		preparedStatement.setString(2, sc.nextLine());

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No data found!");
			} else {
				System.out.printf("%-4s %-10s %-10s\n", "MBR", "IME", "PREZIME");

				while (resultSet.next()) {
					int mbr = resultSet.getInt(1);
					String ime = resultSet.getString(2);
					String prezime = resultSet.getString(3);
					System.out.printf("%-4d %-10s %-10s\n", mbr, ime, prezime);
				}
			}
		}
	}

}
