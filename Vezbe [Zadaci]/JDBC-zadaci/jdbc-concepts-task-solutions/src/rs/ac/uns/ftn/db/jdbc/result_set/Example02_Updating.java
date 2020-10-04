package rs.ac.uns.ftn.db.jdbc.result_set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example02_Updating {

	static final String query = "select spr, nap, ruk from projekat order by spr desc";
	static int sprValue;

	public static void main(String[] args) throws SQLException {
		System.out.println("---------- INSERTING ROW ----------");
		insertRow();
		selectAllRows();

		System.out.println("\n---------- UPDATING ROW ----------");
		updateRow();
		selectAllRows();

		System.out.println("\n---------- DELETING ROW ----------");
		deleteRow();
		selectAllRows();
	}

	private static void insertRow() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();

				// With CONCUR_UPDATABLE we declare that the resultSet can be used to update underlying table
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.absolute(1); // holds current max value for the primary key (order by desc used)
			int currentMaxSprValue = resultSet.getInt(1);
			sprValue = currentMaxSprValue + 10; // calculate next value for the primary key

			resultSet.moveToInsertRow(); // a special row, a buffer, which can be used to build up the row until all
											// column values have been set on the row
			
			// set values for new row
			resultSet.updateInt(1, sprValue);
			resultSet.updateString("nap", "Insert, spr=" + sprValue);
			resultSet.updateInt(3, 10);
			
			// confirm insert
			resultSet.insertRow();

			System.out.println("Inserted row with spr=" + sprValue);

			resultSet.beforeFirst();

			System.out.println(
					"NOTE: Insert affects the underlying table, not the resultSet, so the inserted row WON'T BE AVAILABLE in the original resultSet.");

			System.out.println("\n-- ORIGINAL ResultSet --");
			System.out.printf("%-4s %-18s %s\n", "SPR", "NAZIV PROJEKTA", "RUKOVODILAC");
			while (resultSet.next()) {
				printRowData(resultSet);
			}
		}
	}

	private static void updateRow() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();

				// With CONCUR_UPDATABLE we declare that the resultSet can be used to update underlying table
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.absolute(1);
			resultSet.updateString("nap", "Update, spr=" + sprValue);
			resultSet.updateRow();

			System.out.println("Updated row with spr=" + sprValue);

			resultSet.beforeFirst();

			System.out.println(
					"NOTE: Updates affect the underlying table and the resultSet, so the updated values WILL BE AVAILABLE in the original resultSet.");

			System.out.println("\n-- ORIGINAL ResultSet --");
			System.out.printf("%-4s %-18s %s\n", "SPR", "NAZIV PROJEKTA", "RUKOVODILAC");
			while (resultSet.next()) {
				printRowData(resultSet);
			}

		}

	}

	private static void deleteRow() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();

				// With CONCUR_UPDATABLE we declare that the resultSet can be used to update underlying table
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.absolute(1);
			resultSet.deleteRow();

			System.out.println("Deleted row with spr=" + sprValue);

			resultSet.beforeFirst();

			System.out.println("NOTE: Deletes affect the underlying table and the resultSet.");

			System.out.println("\n-- ORIGINAL ResultSet --");
			System.out.printf("%-4s %-18s %s\n", "SPR", "NAZIV PROJEKTA", "RUKOVODILAC");
			while (resultSet.next()) {
				printRowData(resultSet);
			}

		}
	}

	private static void selectAllRows() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			System.out.println("\n-- SELECT ALL --");
			System.out.printf("%-4s %-18s %s\n", "SPR", "NAZIV PROJEKTA", "RUKOVODILAC");
			while (resultSet.next()) {
				printRowData(resultSet);
			}

		}

	}

	private static void printRowData(ResultSet resultSet) throws SQLException {
		int spr = resultSet.getInt(1);
		String nap = resultSet.getString(2);
		int ruk = resultSet.getInt(3);

		System.out.printf("%-4d %-18.18s %d\n", spr, nap, ruk);
	}

}
