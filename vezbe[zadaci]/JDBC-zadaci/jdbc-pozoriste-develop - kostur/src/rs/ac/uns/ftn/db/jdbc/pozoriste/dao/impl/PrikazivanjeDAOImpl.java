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
		// TODO Auto-generated method stub
		return null;
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

	
	
	
	
	
	
	
	
	
}
