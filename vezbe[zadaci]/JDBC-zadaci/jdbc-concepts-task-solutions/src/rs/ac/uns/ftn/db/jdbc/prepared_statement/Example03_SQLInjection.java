package rs.ac.uns.ftn.db.jdbc.prepared_statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example03_SQLInjection {
	public static void main(String[] args) {
		createAndPopulateUsersTable();

		String query = "select username, password from jdbc_users where username= ? and password= ?";

		try (Scanner sc = new Scanner(System.in);
				Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			// try entering: "' or 1='1"

			System.out.println("Username: ");
			preparedStatement.setString(1, sc.nextLine());

			System.out.println("Password: ");
			preparedStatement.setString(2, sc.nextLine());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (!resultSet.isBeforeFirst()) {
					System.out.println("Invalid username or password!");
				}

				while (resultSet.next()) {
					String username = resultSet.getString(1);
					String password = resultSet.getString(2);
					System.out.printf("Successfully logged in! Username: %s, password: %s\n", username, password);
				}
			}

			dropUsersTable(preparedStatement);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void createAndPopulateUsersTable() {
		String createTableStatement = "create table jdbc_users (username varchar(10) primary key, password varchar(10) not null)";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(createTableStatement);) {

			preparedStatement.execute();

			preparedStatement.executeUpdate("insert into jdbc_users values ('admin', 'admin')");
			preparedStatement.executeUpdate("insert into jdbc_users values ('user1', '1234')");
			preparedStatement.executeUpdate("insert into jdbc_users values ('user2', 'qwerty')");
			preparedStatement.executeUpdate("insert into jdbc_users values ('user3', 'password')");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void dropUsersTable(Statement statement) {
		try {
			statement.execute("drop table jdbc_users");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
