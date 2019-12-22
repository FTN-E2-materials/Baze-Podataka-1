package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PrikazivanjeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDeleteDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;

public class PrikazivanjeDAOImpl implements PrikazivanjeDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

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

		try (Connection connection = HikariCP.getConnection();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(Iterable<Prikazivanje> entities) throws SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metoda koja vraca listu ID-eva prikazivanih predstava
	 * @throws SQLException 
	 */
	@Override
	public List<Integer> pronadjiPrikazivanePredstave() throws SQLException {
		String upit = "select distinct idpred from prikazivanje pr, predstava p where pr.predstava_idpred = idpred";
		
		List<Integer> result = new ArrayList<Integer>();
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
					Predstava predstava = new Predstava(resultSet.getInt(1));
					result.add(predstava.getIdpred());
					
				}
				
			}
			
		}
		
		return result;
	}

	/** 
	 * Metoda koja vraca listu svih prikazivanja odredjene predstave
	 * @throws SQLException 
	 */
	@Override
	public List<Prikazivanje> nadjiSvaPrikazivanjaPredstave(Integer idPredstave) throws SQLException {
		String query = "select rbr,datumpri,vremepri,brojgled,predstava_idpred,scena_idsce from prikazivanje where predstava_idpred = ?";
		List<Prikazivanje> prikazivanjeList = new ArrayList<Prikazivanje>();

		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, idPredstave);

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

	/**
	 * Metoda koja vraca HashMapu u kojoj je kljuc ID predstave koja se prikazuje.
	 * Pri cemu je vrednost entitet koji predstavlja tabelu koja ima obelezja:ukupan_broj_gledalaca, prosecan_broj_gledalaca, ukupan_broj_prikazivanja
	 * 
	 */
	@Override
	public HashMap<Integer, PrikazivanjeDTO> nadjiSumAvgCountPredstave() throws SQLException {
		HashMap<Integer,PrikazivanjeDTO> result = new HashMap<Integer,PrikazivanjeDTO>();
		
		String upit = "select idpred,sum(brojgled),avg(brojgled),count(*) from prikazivanje pr,predstava pe where pr.predstava_idpred = idpred group by idpred";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
					PrikazivanjeDTO prikazivanje = new PrikazivanjeDTO(resultSet.getInt(2), resultSet.getFloat(3),
							resultSet.getInt(4));
					result.put(resultSet.getInt(1), prikazivanje);
				}
			}
			
		}
		
		return result;
	}

	@Override
	public List<PrikazivanjeDeleteDTO> nadjiPredstaveZaBrisanje() throws SQLException {
		String query = "select nazivsce,brojsed,pozoriste_idpoz,s.idsce,rbr,datumpri,vremepri,brojgled,predstava_idpred from scena s, prikazivanje p where p.scena_idsce = s.idsce and brojgled>s.brojsed and datumpri>sysdate";
		List<PrikazivanjeDeleteDTO> result = new ArrayList<PrikazivanjeDeleteDTO>();
		try (Connection connection = HikariCP.getConnection();
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
	public void obrisiIUbaciUPredstavu(PrikazivanjeDeleteDTO deletedItem) throws SQLException {
		try ( Connection connection = HikariCP.getConnection()){
			connection.setAutoCommit(false);
			obrisiPoID(connection, deletedItem);
			
			// koliko cemo napuniti celih sala
			int n = deletedItem.getBrojgled() / deletedItem.getBrojsed();

			// koliko ostane za poslednju salu koja nece biti puna
			int last = deletedItem.getBrojgled() % deletedItem.getBrojsed();
		}
		
	}

	@Override
	public void obrisiPoID(Connection c, PrikazivanjeDeleteDTO deletedItem) throws SQLException {
		String upit = "delete from prikazivanje where rbr = ?";
		
		PreparedStatement preparedStatement = c.prepareStatement(upit);
		{
			preparedStatement.setInt(1, deletedItem.getRbr());
			preparedStatement.executeUpdate();
		}
		
	}

	private int findMaxId() throws SQLException {
		String query = "select max(rbr) from prikazivanje";

		try (Connection connection = HikariCP.getConnection();
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
