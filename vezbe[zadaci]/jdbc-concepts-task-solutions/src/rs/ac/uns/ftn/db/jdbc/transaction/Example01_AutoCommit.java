package rs.ac.uns.ftn.db.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example01_AutoCommit {

	public static void main(String[] args) throws SQLException {
		createTempTables();

		System.out.println("--- DATA BEFORE UPDATE ---");
		printJoinRadnik2RadProj2();

		executeSimple("update radproj2 set brc = brc * 0.5");
		executeSimple("update radnik2 set plt = plt * 0.5");

		System.out.println("\n--- DATA AFTER UPDATE ---");
		printJoinRadnik2RadProj2();

		dropTempTables();

	}

	private static void printJoinRadnik2RadProj2() throws SQLException {
		String query = "select r.mbr, ime, prz, plt, spr, brc from radnik2 r inner join radproj2 rp on r.mbr = rp.mbr";

		try (Connection connection = ConnectionUtil_Basic.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet = preparedStatement.executeQuery()) {

				System.out.printf("%-4s %-8s %-8s %-6s %-4s %-4s\n", "MBR", "IME", "PREZIME", "PLATA", "SPR", "BRC");

				while (resultSet.next()) {
					System.out.printf("%-4d %-8.8s %-8.8s %-6d %-4d %-4d\n", resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5),
							resultSet.getInt(6));
				}

			}
		}
	}

	private static void createTempTables() throws SQLException {
		String createTableRadnik2Command = "create table radnik2 as select mbr, ime, prz, plt from radnik"
				+ " where mbr in(select mbr from radproj where spr=10)";
		String alterTableRadnik2Command = "alter table radnik2 add constraint CH_PLT check (plt > 5000)";
		String createTableRadProj2Command = "create table radproj2 as select mbr, spr, brc from radproj where spr=10";

		executeSimple(createTableRadnik2Command);
		executeSimple(alterTableRadnik2Command);
		executeSimple(createTableRadProj2Command);
	}

	private static void dropTempTables() throws SQLException {
		String dropTableRadnik2Command = "drop table radnik2";
		String dropTableRadProj2Command = "drop table radproj2";

		executeSimple(dropTableRadnik2Command);
		executeSimple(dropTableRadProj2Command);

	}

	private static final boolean executeSimple(String command) {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {

			return preparedStatement.execute(command);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
