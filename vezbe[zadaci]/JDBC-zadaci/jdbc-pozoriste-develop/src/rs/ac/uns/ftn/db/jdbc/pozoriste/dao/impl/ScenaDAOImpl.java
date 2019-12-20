package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.ScenaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Scena;

public class ScenaDAOImpl implements ScenaDAO {

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from scena";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return -1;
			}
		}
	}

	@Override
	public void delete(Scena entity) throws SQLException {
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
	public Iterable<Scena> findAll() throws SQLException {
		String query = "select idsce, nazivsce, brojsed, pozoriste_idpoz from scena";
		List<Scena> scenaList = new ArrayList<Scena>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Scena scena = new Scena(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getInt(4));
				scenaList.add(scena);
			}

		}
		return scenaList;
	}

	@Override
	public Iterable<Scena> findAllById(Iterable<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scena findById(Integer id) throws SQLException {
		String query = "select idsce, nazivsce, brojsed, pozoriste_idpoz from scena where idsce = ?";
		Scena scena = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					scena = new Scena(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
							resultSet.getInt(4));
				}
			}
		}

		return scena;
	}

	@Override
	public void save(Scena entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(Iterable<Scena> entities) throws SQLException {
		// TODO Auto-generated method stub

	}

	public List<Scena> findSceneByTheatre(Integer theatreId) throws SQLException {
		String query = "select idsce,nazivsce, brojsed ,pozoriste_idpoz from scena where pozoriste_idpoz = ?";
		List<Scena> scenaList = new ArrayList<Scena>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, theatreId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					Scena scena = new Scena(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
							resultSet.getInt(4));
					scenaList.add(scena);
				}
			}
		}

		return scenaList;
	}

	@Override
	public List<Scena> findSceneForSpecificNumberOfSeats() throws SQLException {
		String query = "SELECT S.idsce,S.nazivsce, S.brojsed, S.pozoriste_idpoz "
				+ "FROM Scena S, Pozoriste P, Scena S1, Pozoriste P1 "
				+ "WHERE S.pozoriste_idpoz = P.idpoz AND S.brojsed >= S1.brojsed*0.8 AND "
				+ "S.brojsed <= S1.brojsed*1.2 AND S1.nazivsce = 'Scena Joakim Vujic' AND S1.pozoriste_idpoz = P1.idpoz AND "
				+ "P1.nazivpoz = 'Knjazevsko-srpski teatar Kragujevac'";
		List<Scena> scenaList = new ArrayList<Scena>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Scena scena = new Scena(resultSet.getInt(4), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getInt(4));
				scenaList.add(scena);
			}

		}
		return scenaList;
	}

}
