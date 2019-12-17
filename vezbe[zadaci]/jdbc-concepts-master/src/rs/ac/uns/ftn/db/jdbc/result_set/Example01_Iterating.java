package rs.ac.uns.ftn.db.jdbc.result_set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example01_Iterating {

	static final String query = "select mbr, ime, prz from radnik";

	public static void main(String[] args) throws SQLException {
		System.out.println("---------- TYPE - FORWARD ----------");
		iterateForward();

		System.out.println("\n---------- TYPE - SCROLLABLE ----------");
		iterateScrollable();
	}

	private static void printRowData(ResultSet resultSet) throws SQLException {
		int mbr = resultSet.getInt(1);
		String ime = resultSet.getString(2);
		String prezime = resultSet.getString(3);

		System.out.printf("%-4d %-10s %-10s\n", mbr, ime, prezime);
	}

	private static void iterateForward() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();

				// By default, ResultSet can only move forward
				PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (!resultSet.isBeforeFirst()) { // check if any data matches criteria
				System.out.println("No data found!");
			} else {
				System.out.printf("%-4s %-10s %-10s\n", "MBR", "IME", "PREZIME");
				
				// resultSet.first(); resultSet.absolute(1); ... not allowed!
				
				while (resultSet.next()) {
					printRowData(resultSet);
				}
			}
		}
	}

	private static void iterateScrollable() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();

				// Scrollable ResultSets can be used to move forward and backward through the cursor area.
				// They can be either sensitive (TYPE_SCROLL_SENSITIVE) or insensitive (TYPE_SCROLL_SENSITIVE) to
				// external updates on fetched rows.
				// With CONCUR_READ_ONLY we declare that the resultSet is read only (no updates are allowed).
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.printf("%-4s %-10s %-10s\n", "MBR", "IME", "PREZIME");

			resultSet.first(); // move to the first row
								// must be used before relative if resultSet.isBeforeFirst() == true
			printRowData(resultSet);

			resultSet.relative(1); // moves relative to the current row
									// with argument 1 - identical to calling next()
			printRowData(resultSet);

			resultSet.absolute(resultSet.getRow() + 1); // moves absolute from the beginning
														// getRow() returns current position
			printRowData(resultSet);

			resultSet.next(); // moves to the next row (if it exists)
			printRowData(resultSet);

			resultSet.absolute(6);
			resultSet.previous(); // moves to the previous row (if it exists)
			printRowData(resultSet);

			resultSet.last(); // move to the last row
			printRowData(resultSet);

			resultSet.afterLast();

		}
	}

}
