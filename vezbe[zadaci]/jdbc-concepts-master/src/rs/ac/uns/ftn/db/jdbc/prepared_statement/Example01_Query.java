package rs.ac.uns.ftn.db.jdbc.prepared_statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example01_Query {

	public static void main(String[] args) {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(generateQuery());
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.println("Radnici: ");
			System.out.printf("%-4s %-8s %-8s %-2s\n", "MBR", "IME", "PREZIME", "BROJ_PROJEKATA");

			while (resultSet.next()) {
				int mbr = resultSet.getInt("mbr");
				String ime = resultSet.getString(2);
				String prezime = resultSet.getString("prezime");
				int broj_projekata = resultSet.getInt("br_projekata");

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
