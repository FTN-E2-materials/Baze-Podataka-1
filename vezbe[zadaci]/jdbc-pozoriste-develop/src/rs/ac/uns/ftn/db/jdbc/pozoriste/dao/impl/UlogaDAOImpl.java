package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.UlogaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Uloga;

public class UlogaDAOImpl implements UlogaDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Uloga entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Uloga> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Uloga> findAllById(Iterable<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uloga findById(Integer id) throws SQLException {

		return null;

	}

	@Override
	public void save(Uloga entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(Iterable<Uloga> entities) throws SQLException {
		// TODO Auto-generated method stub

	}

	public List<Uloga> findRoleByTheatreId(int idpred) throws SQLException {
		String query = "select imeulo, pol, vrstaulo,predstava_idpred from uloga where predstava_idpred = ?";
		List<Uloga> result = new ArrayList<Uloga>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, idpred);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					Uloga u = new Uloga(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getInt(4));
					result.add(u);
				}
			}
		}

		return result;
	}

	@Override
	public Integer findCountForRoleGender(int idpred, String gender) throws SQLException {
		String query = "select count(pol) from uloga where predstava_idpred=? and pol=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, idpred);
			preparedStatement.setString(2, gender);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				} else
					return -1;
			}
		}
	}

}
