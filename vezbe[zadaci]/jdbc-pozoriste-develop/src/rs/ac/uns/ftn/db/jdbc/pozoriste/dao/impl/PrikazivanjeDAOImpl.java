package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PrikazivanjeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDeleteDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeScenaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;

public class PrikazivanjeDAOImpl implements PrikazivanjeDAO {

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from prikazivanje";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next())
				return resultSet.getInt(1);
			else {
				return -1;
			}
		}
	}

	@Override
	public void delete(Prikazivanje entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		String query = "delete from prikazivanje where rbr=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Prikazivanje> findAll() throws SQLException {
		String query = "select rbr, datumpri, vremepri, brojgled, predstava_idpred, scena_idsce from prikazivanje";
		List<Prikazivanje> prikazivanjeList = new ArrayList<Prikazivanje>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Prikazivanje prikazivanje = new Prikazivanje(resultSet.getInt(1), resultSet.getDate(2),
						resultSet.getDate(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
				prikazivanjeList.add(prikazivanje);
			}

		}
		return prikazivanjeList;
	}

	@Override
	public Iterable<Prikazivanje> findAllById(Iterable<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prikazivanje findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Prikazivanje entity) throws SQLException {
		String insertCommand = "insert into prikazivanje (rbr, datumpri, vremepri, brojgled, predstava_idpred, scena_idsce) values (?, ? , ?, ?,?, ?)";
		String updateCommand = "update prikazivanje set rbr=?, datumpri=?, vremepri=?, brojgled=?, predstava_idpred = ?, scena_idsce = ? where rbr=?";
		int i = 1;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(existsById(entity.getRbr()) ? updateCommand : insertCommand)) {

			preparedStatement.setInt(i++, count() + 2); // posto smo jednog obrisali da bismo postigli jedinstvenost
			preparedStatement.setDate(i++, (Date) entity.getDatumpri());
			preparedStatement.setDate(i++, (Date) entity.getVremepri());
			preparedStatement.setInt(i++, entity.getBrojgled());
			preparedStatement.setInt(i++, entity.getIdpred());
			preparedStatement.setInt(i++, entity.getIdsce());
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public void saveAll(Iterable<Prikazivanje> entities) throws SQLException {
		String insertCommand = "insert into prikazivanje (rbr, datumpri, vremepri, brojgled, predstava_idpred, scena_idsce) values (?, ? , ?, ?,?, ?)";
		String updateCommand = "update prikazivanje set rbr=?, datumpri=?, vremepri=?, brojgled=?, predstava_idpred = ?, scena_idsce = ? where rbr=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateCommand);
				PreparedStatement preparedStatementInsert = connection.prepareStatement(insertCommand)) {

			connection.setAutoCommit(false);

			for (Prikazivanje entity : entities) {
				int i = 1;

				PreparedStatement preparedStatement;
				if (existsById(entity.getRbr())) {
					preparedStatement = preparedStatementUpdate;
				} else {
					preparedStatement = preparedStatementInsert;
				}

				preparedStatement.setInt(i++, count() + 2); // posto smo jednog obrisali da bismo postigli jedinstvenost
				preparedStatement.setDate(i++, (Date) entity.getDatumpri());
				preparedStatement.setDate(i++, (Date) entity.getVremepri());
				preparedStatement.setInt(i++, entity.getBrojgled());
				preparedStatement.setInt(i++, entity.getIdpred());
				preparedStatement.setInt(i++, entity.getIdsce());
				preparedStatement.executeUpdate();
				preparedStatement.execute();
			}

			connection.commit();

		}

	}

	@Override
	public List<PrikazivanjeScenaDTO> findBySceneId(Integer idScene) throws SQLException {

		String query = "select predstava_idpred,sum(brojgled) from prikazivanje where scena_idsce = ? group by predstava_idpred";
		List<PrikazivanjeScenaDTO> prikazivanjeList = new ArrayList<PrikazivanjeScenaDTO>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, idScene);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					PrikazivanjeScenaDTO prikazivanje = new PrikazivanjeScenaDTO(resultSet.getInt(1),
							resultSet.getInt(2));
					prikazivanjeList.add(prikazivanje);
				}
			}
		}

		return prikazivanjeList;
	}

	@Override
	public HashMap<Integer, PrikazivanjeDTO> findSumAvgCountForShowingShow() throws SQLException {
		HashMap<Integer, PrikazivanjeDTO> result = new HashMap<Integer, PrikazivanjeDTO>();

		String query = "SELECT idpred, SUM(brojgled), AVG(brojgled) , COUNT(*) FROM Prikazivanje pr, Predstava p WHERE pr.predstava_idpred = idpred GROUP BY idpred";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					PrikazivanjeDTO prikazivanje = new PrikazivanjeDTO(resultSet.getInt(2), resultSet.getFloat(3),
							resultSet.getInt(4));
					result.put(resultSet.getInt(1), prikazivanje);
				}
			}
		}

		return result;
	}

	@Override
	public List<Integer> findAllDistinctShowFromShowing() throws SQLException {

		String query = "SELECT distinct idpred FROM Prikazivanje pr, Predstava p WHERE pr.predstava_idpred = idpred";
		List<Integer> result = new ArrayList<Integer>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					Predstava predstava = new Predstava(resultSet.getInt(1));
					result.add(predstava.getIdpred());
				}
			}
		}

		return result;
	}

	@Override
	public List<PrikazivanjeDeleteDTO> findShowingForDeleting() throws SQLException {
		String query = "select nazivsce,brojsed,pozoriste_idpoz,s.idsce,rbr,datumpri,vremepri,brojgled,predstava_idpred from scena s, prikazivanje p where p.scena_idsce = s.idsce and brojgled>s.brojsed and datumpri>sysdate";
		List<PrikazivanjeDeleteDTO> result = new ArrayList<PrikazivanjeDeleteDTO>();
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					PrikazivanjeDeleteDTO prikazivanje = new PrikazivanjeDeleteDTO(resultSet.getString(1),
							resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5),
							resultSet.getDate(6), resultSet.getDate(7), resultSet.getInt(8), resultSet.getInt(9));
					result.add(prikazivanje);
				}
			}
		}

		return result;

	}

	@Override
	public List<Prikazivanje> findShowingByShowId(Integer idpred) throws SQLException {
		String query = "select rbr,datumpri,vremepri,brojgled,predstava_idpred,scena_idsce from prikazivanje where predstava_idpred = ?";
		List<Prikazivanje> prikazivanjeList = new ArrayList<Prikazivanje>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, idpred);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					Prikazivanje prikazivanje = new Prikazivanje(resultSet.getInt(1), resultSet.getDate(2),
							resultSet.getDate(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
					prikazivanjeList.add(prikazivanje);
				}
			}
		}

		return prikazivanjeList;
	}

	@Override
	public void deleteAndInsertIntoShowing(PrikazivanjeDeleteDTO deletedItem) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			connection.setAutoCommit(false);
			deleteByIdExtended(connection, deletedItem);

			// koliko cemo napuniti celih sala
			int n = deletedItem.getBrojgled() / deletedItem.getBrojsed();

			// koliko ostane za poslednju salu koja nece biti puna
			int last = deletedItem.getBrojgled() % deletedItem.getBrojsed();

			int i;
			int currentMax = findMaxId();
			System.out.println("Current MAX:" + currentMax);

			for (i = 0; i < n; i++) {
				Prikazivanje p = getModifiedValues(deletedItem);
				p.setRbr(++currentMax);
				save(connection, p);
				System.out.println("SAVING: " + p);
			}

			Prikazivanje p = getModifiedValues(deletedItem);
			p.setBrojgled(last);
			p.setRbr(++currentMax);
			save(connection, p);
			System.out.println("SAVING: " + p);

			connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void deleteByIdExtended(Connection c, PrikazivanjeDeleteDTO deletedItem) throws SQLException {
		String query = "delete from prikazivanje where rbr=?";

		PreparedStatement preparedStatement = c.prepareStatement(query);
		{
			preparedStatement.setInt(1, deletedItem.getRbr());
			preparedStatement.executeUpdate();
		}

	}

	private Prikazivanje getModifiedValues(PrikazivanjeDeleteDTO pold) {
		Prikazivanje p_new = new Prikazivanje();
		p_new.setIdpred(pold.getPredstava_idpred());
		p_new.setRbr(pold.getRbr());
		p_new.setBrojgled(pold.getBrojsed());
		p_new.setIdsce(pold.getIdsce());
		// TODO namesiti danasnji datum i uzastopne termine
		p_new.setDatumpri(pold.getDatumpri());
		p_new.setVremepri(pold.getVremepri());
		return p_new;
	}

	public void save(Connection c, Prikazivanje entity) throws SQLException {
		String insertCommand = "insert into prikazivanje (rbr, datumpri, vremepri, brojgled, predstava_idpred, scena_idsce) values (?, ? , ?, ?,?, ?)";
		String updateCommand = "update prikazivanje set rbr=?, datumpri=?, vremepri=?, brojgled=?, predstava_idpred = ?, scena_idsce = ? where rbr=?";
		int i = 1;

		try (PreparedStatement preparedStatement = c
				.prepareStatement(existsById(entity.getRbr()) ? updateCommand : insertCommand)) {
			preparedStatement.setInt(i++, entity.getRbr());
			preparedStatement.setDate(i++, (Date) entity.getDatumpri());
			preparedStatement.setDate(i++, (Date) entity.getVremepri());
			preparedStatement.setInt(i++, entity.getBrojgled());
			preparedStatement.setInt(i++, entity.getIdpred());
			preparedStatement.setInt(i++, entity.getIdsce());
			preparedStatement.executeUpdate();
		}

	}

	private int findMaxId() throws SQLException {
		String query = "select max(rbr) from prikazivanje";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next())
				return resultSet.getInt(1);
			else {
				return -1;
			}
		}
	}

}
