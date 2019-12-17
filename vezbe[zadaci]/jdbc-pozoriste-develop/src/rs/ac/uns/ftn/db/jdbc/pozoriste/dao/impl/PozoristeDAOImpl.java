package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;

public class PozoristeDAOImpl implements PozoristeDAO {

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from pozoriste";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if(resultSet.next()){
				return resultSet.getInt(1);
			}else return -1;

		}
	}

	@Override
	public void delete(Pozoriste entity) throws SQLException {
		String query = "delete from pozoriste where idpoz=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, entity.getId());
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void deleteAll() throws SQLException {
		String query = "delete from pozoriste";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		String query = "delete from pozoriste where idpoz=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		String query = "select * from pozoriste where idpoz=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}

		}

	}

	@Override
	public Iterable<Pozoriste> findAll() throws SQLException {
		String query = "select idpoz, nazivpoz, adresapoz, sajt, mesto_idm  from pozoriste";
		List<Pozoriste> pozoristeList = new ArrayList<Pozoriste>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Pozoriste pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),resultSet.getString(5));
				pozoristeList.add(pozoriste);
			}

		}
		return pozoristeList;
	}

	@Override
	public Iterable<Pozoriste> findAllById(Iterable<Integer> ids) throws SQLException {
		List<Pozoriste> pozoristeList = new ArrayList<>();

		StringBuilder stringBuilder = new StringBuilder();

		String queryBegin = "select idpoz, nazivpoz, adresapoz, sajt, mesto_idm  from pozoriste where idpoz in(";
		stringBuilder.append(queryBegin);

		for (@SuppressWarnings("unused")
		Integer id : ids) {
			stringBuilder.append("?,");
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // delete last ','
		stringBuilder.append(")");

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());) {

			int i = 0;
			for (Integer id : ids) {
				preparedStatement.setInt(++i, id);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					pozoristeList.add(new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4),resultSet.getString(5)));
				}
			}
		}

		return pozoristeList;
	}

	@Override
	public Pozoriste findById(Integer id) throws SQLException {
		String query = "select idpoz, nazivpoz, adresapoz, sajt,mesto_idm  from pozoriste where idpoz = ?";
		Pozoriste pozoriste = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4),resultSet.getString(5));
				}
			}
		}

		return pozoriste;
	}

	@Override
	public void save(Pozoriste entity) throws SQLException {
		// idpoz intentionally in the last place, so that the order between commands remains the same
		String insertCommand = "insert into pozoriste (nazivpoz, adresapoz, sajt,mesto_idm, idpoz) values (?, ? , ?, ?,?)";
		String updateCommand = "update pozoriste set nazivpoz=?, adresapoz=?, sajt=?, mesto_idm=? where idpoz=?";
		int i = 1;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(existsById(entity.getId()) ? updateCommand : insertCommand)) {

			preparedStatement.setString(i++, entity.getNaziv());
			preparedStatement.setString(i++, entity.getAdresa());
			preparedStatement.setString(i++, entity.getSajt());
			preparedStatement.setString(i++, entity.getMesto());
			preparedStatement.setInt(i++, entity.getId());

			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void saveAll(Iterable<Pozoriste> entities) throws SQLException {
		String insertCommand = "insert into pozoriste (nazivpoz, adresapoz, sajt,mesto_idm, idpoz) values (?, ? , ?, ?,?)";
		String updateCommand = "update pozoriste set nazivpoz=?, adresapoz=?, sajt=?, mesto_idm=? where idpoz=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateCommand);
				PreparedStatement preparedStatementInsert = connection.prepareStatement(insertCommand)) {

			connection.setAutoCommit(false);

			for (Pozoriste entity : entities) {
				int i = 1;

				PreparedStatement preparedStatement;
				if (existsById(entity.getId())) {
					preparedStatement = preparedStatementUpdate;
				} else {
					preparedStatement = preparedStatementInsert;
				}

				preparedStatement.setString(i++, entity.getNaziv());
				preparedStatement.setString(i++, entity.getAdresa());
				preparedStatement.setString(i++, entity.getSajt());
				preparedStatement.setString(i++, entity.getMesto());
				preparedStatement.setInt(i++, entity.getId());

				preparedStatement.execute();
			}

			connection.commit();

		}

	}

	

}
