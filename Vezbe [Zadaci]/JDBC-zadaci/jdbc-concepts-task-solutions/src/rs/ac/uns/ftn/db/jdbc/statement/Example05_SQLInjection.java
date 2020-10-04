package rs.ac.uns.ftn.db.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Example05_SQLInjection {
	public static void main(String[] args) {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				Statement statement = connection.createStatement();) {

			createAndPopulateUsersTable(statement);

			try (ResultSet resultSet = statement.executeQuery(generateQuery())) {

				if (!resultSet.isBeforeFirst()) {
					System.out.println("Invalid username or password!");
				}

				while (resultSet.next()) {
					String username = resultSet.getString(1);
					String password = resultSet.getString(2);
					System.out.printf("Successfully logged in! Username: %s, password: %s\n", username, password);
				}
			}

			dropUsersTable(statement);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void createAndPopulateUsersTable(Statement statement) {
		try {
			statement.execute(
					"create table jdbc_users (username varchar(10) primary key, password varchar(10) not null)");

			statement.executeUpdate("insert into jdbc_users values ('admin', 'admin')");
			statement.executeUpdate("insert into jdbc_users values ('user1', '1234')");
			statement.executeUpdate("insert into jdbc_users values ('user2', 'qwerty')");
			statement.executeUpdate("insert into jdbc_users values ('user3', 'password')");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private static String generateQuery() {
		try (Scanner sc = new Scanner(System.in)) {
			// try entering: "' or 1='1"

			System.out.println("Username: ");
			String username = sc.nextLine();

			System.out.println("Password: ");
			String password = sc.nextLine();

			String query = String.format(
					"select username, password from jdbc_users where username='%s' and password='%s'", username,
					password);

			return query;
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
