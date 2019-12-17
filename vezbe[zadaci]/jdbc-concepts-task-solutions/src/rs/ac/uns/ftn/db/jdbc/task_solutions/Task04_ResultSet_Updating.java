package rs.ac.uns.ftn.db.jdbc.task_solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Task04_ResultSet_Updating {

	static final String queryRadnik = "select mbr, ime, prz, god from radnik order by mbr desc";
	static int mbrValue = 0;

	static final String queryRadProj = "select mbr, spr, brc from radproj";

	public static void main(String[] args) throws SQLException {
		System.out.println("---------- INSERTING ROW ----------");
		insertRowIntoRadnik();
		insertRowIntoRadproj();

	}

	private static void insertRowIntoRadnik() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatementRadnik = connection.prepareStatement(queryRadnik,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = preparedStatementRadnik.executeQuery();) {

			resultSet.absolute(1); // holds current max value for the primary key (order by desc used)
			int currentMaxMbrValue = resultSet.getInt(1);
			mbrValue = currentMaxMbrValue + 10; // calculate next value for the primary key

			resultSet.moveToInsertRow();

			resultSet.updateInt(1, mbrValue);
			resultSet.updateString("ime", "TestIme");
			resultSet.updateString("prz", "TestPrezime");

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			resultSet.updateDate(4, new Date(formatter.parse("18-12-1993").getTime()));

			resultSet.insertRow();

			System.out.println("Inserted row with mbr=" + mbrValue + " to Radnik");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void insertRowIntoRadproj() throws SQLException {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatementRadnik = connection.prepareStatement(queryRadProj,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultSet = preparedStatementRadnik.executeQuery();) {

			resultSet.moveToInsertRow();

			resultSet.updateInt(1, mbrValue);
			resultSet.updateInt("spr", 10);
			resultSet.updateInt("brc", 5);

			resultSet.insertRow();

			System.out.println("Inserted row with mbr=" + mbrValue + " to RadProj");
		}

	}

}
