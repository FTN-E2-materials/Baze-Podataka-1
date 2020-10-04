package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;

public class PozoristeDAOImpl implements PozoristeDAO {

	@Override
	public int count() throws SQLException {
		String upit = "select count(*) from pozoriste";

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				return resultSet.getInt(1);
			} else {
				return -1;
			}
		}

	}

	@Override
	public void delete(Pozoriste entity) throws SQLException {
		String upit = "delete from pozoriste where idpoz = ?";

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			preparedStatement.setInt(1, entity.getId());
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void deleteAll() throws SQLException {
		String upit = "delete from pozoriste";

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		String upit = "delete from pozoriste where idpoz = ?";

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		String upit = "select * from pozoriste where idpoz = ?";

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public Iterable<Pozoriste> findAll() throws SQLException {
		List<Pozoriste> listaPozorista = new ArrayList<Pozoriste>();
		String upit = "select idpoz, nazivpoz, adresapoz, sajt, mesto_idm from pozoriste";
		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Pozoriste pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				listaPozorista.add(pozoriste);
			}
		}

		return listaPozorista;
	}

	@Override
	public Iterable<Pozoriste> findAllById(Iterable<Integer> ids) throws SQLException {
		List<Pozoriste> listaPozorista = new ArrayList<Pozoriste>();
		String upit0 = "select idpoz, nazivpoz, adresapoz, sajt, mesto_idm from pozoriste where idpoz in(";

		for (@SuppressWarnings("unused")
		Integer id : ids) {
			upit0 += "?,";
		}
		String upit = upit0.substring(0, upit0.length() - 1);
		upit += ")";
		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			int i = 0;
			for (Integer id : ids) {
				preparedStatement.setInt(++i, id);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Pozoriste pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2),
							resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
					listaPozorista.add(pozoriste);
				}
			}
		}

		return listaPozorista;
	}

	@Override
	public Pozoriste findById(Integer id) throws SQLException {
		String upit = "select idpoz, nazivpoz, adresapoz, sajt, mesto_idm from pozoriste where idpoz = ?";
		Pozoriste pozoriste = null;
		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();
					pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5));
				}
			}
		}
		return pozoriste;
	}

	@Override
	public void save(Pozoriste entity) throws SQLException {
		String insertuj = "insert into pozoriste (nazivpoz, adresapoz, sajt, mesto_idm, idpoz)  values (?,?,?,?,?)";
		String apdejtuj = "update pozoriste set nazivpoz = ?, adresapoz = ?, sajt = ?, mesto_idm = ? where idpoz = ?";
		// idpoz, nazivpoz, adresapoz, sajt, mesto_idm

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(existsById(entity.getId()) ? apdejtuj : insertuj);) {
			int i = 0;
			preparedStatement.setString(++i, entity.getNaziv());
			preparedStatement.setString(++i, entity.getAdresa());
			preparedStatement.setString(++i, entity.getSajt());
			preparedStatement.setString(++i, entity.getMesto());
			preparedStatement.setInt(++i, entity.getId());

			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void saveAll(Iterable<Pozoriste> entities) throws SQLException {
		String insertuj = "insert into pozoriste (nazivpoz, adresapoz, sajt, mesto_idm, idpoz)  values (?,?,?,?,?)";
		String apdejtuj = "update pozoriste set nazivpoz = ?, adresapoz = ?, sajt = ?, mesto_idm = ? where idpoz = ?";
		// idpoz, nazivpoz, adresapoz, sajt, mesto_idm

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatementInsert = connection.prepareStatement(insertuj);
				PreparedStatement preparedStatementUpdate = connection.prepareStatement(apdejtuj);) {

			connection.setAutoCommit(false);
			PreparedStatement preparedStatement;
			for (Pozoriste pozoriste : entities) {
				if (existsById(pozoriste.getId())) {
					preparedStatement = preparedStatementUpdate;
				} else {
					preparedStatement = preparedStatementInsert;
				}
				int i = 0;
				preparedStatement.setString(++i, pozoriste.getNaziv());
				preparedStatement.setString(++i, pozoriste.getAdresa());
				preparedStatement.setString(++i, pozoriste.getSajt());
				preparedStatement.setString(++i, pozoriste.getMesto());
				preparedStatement.setInt(++i, pozoriste.getId());

				preparedStatement.executeUpdate();
			}

			connection.commit();
		}

	}

}
