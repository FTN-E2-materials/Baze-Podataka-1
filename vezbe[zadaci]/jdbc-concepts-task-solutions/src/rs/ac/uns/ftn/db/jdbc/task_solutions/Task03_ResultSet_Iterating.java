package rs.ac.uns.ftn.db.jdbc.task_solutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.connection.ConnectionUtil_Basic;

public class Task03_ResultSet_Iterating {

	public static void main(String[] args) {
		final String sefoviQuery = "select r.sef, r1.ime, r1.prz, r1.plt, count(r.mbr) from radnik r, radnik r1 where r.sef=r1.mbr group by r.sef, r1.ime, r1.prz, r1.plt";
		final String podredjeniQuery = "select ime, prz, plt from radnik where sef = ?";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatementSefovi = connection.prepareStatement(sefoviQuery);
				PreparedStatement preparedStatementPodredjeni = connection.prepareStatement(podredjeniQuery);
				ResultSet resultSetSefovi = preparedStatementSefovi.executeQuery();) {

			System.out.printf("%-10s %-10s %-10s %-6s", "IME", "PREZIME", "BR_PODREDJ", "PLATA");

			while (resultSetSefovi.next()) {
				System.out.printf("\n%-10s %-10s %-10d %-6d\n", resultSetSefovi.getString(2), resultSetSefovi.getString(3),
						resultSetSefovi.getInt(5), resultSetSefovi.getInt(4));

				preparedStatementPodredjeni.setInt(1, resultSetSefovi.getInt(1));
				ResultSet resultSetPodredjeni = preparedStatementPodredjeni.executeQuery();

				System.out.printf("\t%-10s %-10s %-6s\n", "IME", "PREZIME", "PLATA");

				while (resultSetPodredjeni.next()) {
					System.out.printf("\t%-10s %-10s %-6s\n", resultSetPodredjeni.getString(1),
							resultSetPodredjeni.getString(2), resultSetPodredjeni.getInt(3));

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
