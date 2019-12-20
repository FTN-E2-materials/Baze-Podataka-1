package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example02_QueryElegant {

	public static void main(String[] args) {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(generateQuery())) {

			System.out.println("Radnici: ");
			System.out.printf("%-4s %-8s %-8s %-2s\n", "MBR", "IME", "PREZIME", "BROJ_PROJEKATA");

			while (resultSet.next()) {
				int mbr = resultSet.getInt("mbr");
				String ime = resultSet.getString(2); // Enumeration starts at 1
				String prezime = resultSet.getString("prezime"); // 'prz' cannot be used because alias is defined
				int broj_projekata = resultSet.getInt("br_projekata"); // either number or alias must be used used for
																		// expression-based columns

				System.out.printf("%-4d %-8.8s %-8.8s %-2d\n", mbr, ime, prezime, broj_projekata);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static String generateQuery() {
		String query = "select radnik.mbr, ime, prz prezime, count(spr) br_projekata"
				+ " from radnik left outer join radproj on radnik.mbr = radproj.mbr group by radnik.mbr, ime, prz"
				+ " having count(spr)<3 order by br_projekata desc, mbr asc";

		return query;
	}

}
