package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example04_DDL_DML_QL {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		createTableFazeProjekta();

		handleMenu();

		sc.close();
	}

	private static void handleMenu() {

		String response = null;
		do {
			System.out.println("\nChoose and option:");
			System.out.println("1 - Select all rows from Faze_Projekta");
			System.out.println("2 - Select row from Faze_Projekta by spr and sfp");
			System.out.println("3 - Insert row into Faze_Projekta");
			System.out.println("4 - Modify existing row from Faze_Projekta");
			System.out.println("5 - Delete existing row from Faze_Projekta");
			System.out.println("6 - Drop table Faze_Projekta");
			System.out.println("X - Exit");

			response = sc.nextLine();

			try {
				switch (response) {
				case "1":
					selectAll();
					break;
				case "2":
					selectBySprAndSfp();
					break;
				case "3":
					insertRow();
					break;
				case "4":
					modifyRow();
					break;
				case "5":
					deleteRow();
					break;
				case "6":
					dropTableFazeProjekta();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} while (!response.equalsIgnoreCase("X"));

	}

	private static void selectAll() throws SQLException {
		String query = "select spr, sfp, rukfp, nafp, datp from faze_projekta";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			System.out.printf("%-4s %-4s %-6s %-20s %-21s\n", "SPR", "SFP", "RUKFP", "NAFP", "DATP");

			while (resultSet.next()) {
				System.out.printf("%-4s %-4s %-6s %-20s %-21s\n", resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
			}

		}
	}

	private static void selectBySprAndSfp() throws SQLException {
		System.out.println("SPR: ");
		String spr = sc.nextLine();
		System.out.println("SFP: ");
		String sfp = sc.nextLine();

		String query = String.format("select * from faze_projekta where spr = %s and sfp = %s", spr, sfp);

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			if (!resultSet.isBeforeFirst()) {
				System.out.println("No rows selected!");

			} else {
				System.out.printf("%-4s %-4s %-6s %-20s %-21s\n", "SPR", "SFP", "RUKFP", "NAFP", "DATP");
				while (resultSet.next()) {
					System.out.printf("%-4s %-4s %-6s %-20s %-21s\n", resultSet.getString(1), resultSet.getString(2),
							resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
				}
			}

		}

	}

	private static void insertRow() throws SQLException {
		System.out.println("SPR: ");
		String spr = sc.nextLine();
		System.out.println("SFP: ");
		String sfp = sc.nextLine();
		System.out.println("RUKFP: ");
		String rukfp = sc.nextLine();
		System.out.println("NAFP: ");
		String nafp = sc.nextLine();
		System.out.println("DATP: ");
		String datp = sc.nextLine();

		String command = String.format(
				"insert into faze_projekta (spr, sfp, rukfp, nafp, datp) values (%s, %s, %s, '%s', to_date('%s', 'dd.MM.yyyy.'))",
				spr, sfp, rukfp, nafp, datp);

		executeUpdate(command);

		System.out.println("Row sucessfully inserted");

	}

	private static void deleteRow() throws SQLException {
		System.out.println("SPR: ");
		String spr = sc.nextLine();
		System.out.println("SFP: ");
		String sfp = sc.nextLine();

		String command = String.format("delete from faze_projekta where spr=%s and sfp=%s", spr, sfp);

		System.out.printf("%d row(s) affected by delete!", executeUpdate(command));
	}

	private static void modifyRow() throws SQLException {
		System.out.println("SPR: ");
		String spr = sc.nextLine();
		System.out.println("SFP: ");
		String sfp = sc.nextLine();
		System.out.println("RUKFP: ");
		String rukfp = sc.nextLine();
		System.out.println("NAFP: ");
		String nafp = sc.nextLine();
		System.out.println("DATP: ");
		String datp = sc.nextLine();

		String command = String.format(
				"update faze_projekta set rukfp=%s, nafp='%s', datp=to_date('%s', 'dd.MM.yyyy.') where spr=%s and sfp=%s",
				rukfp, nafp, datp, spr, sfp);

		System.out.printf("%d row(s) affected by update!", executeUpdate(command));

	}

	private static final int executeUpdate(String command) throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();) {

			return statement.executeUpdate(command);
		}
	}

	private static final boolean execute(String command) throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();) {

			return statement.execute(command);
		}
	}

	private static void createTableFazeProjekta() {
		String command = "create table faze_projekta (spr int, sfp int, rukfp int, nafp varchar2(10), datp date,"
				+ "constraint fp_pk primary key (spr, sfp), constraint fp_fk foreign key (rukfp) references radnik(mbr))";

		try {
			execute(command);
			System.out.println("Table Faze_Projekta successfully created!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void dropTableFazeProjekta() throws SQLException {
		String command = "drop table faze_projekta";

		execute(command);
		System.out.println("Table Faze_Projekta successfully dropped!");

	}
}
