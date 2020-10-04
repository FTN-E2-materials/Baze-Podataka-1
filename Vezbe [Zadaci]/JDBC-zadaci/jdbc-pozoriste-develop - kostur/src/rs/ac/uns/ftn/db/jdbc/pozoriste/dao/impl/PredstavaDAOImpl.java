package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PredstavaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PredstavaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;

public class PredstavaDAOImpl implements PredstavaDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Predstava entity) throws SQLException {
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
	public Iterable<Predstava> findAll() throws SQLException {
		List<Predstava> listaPredstava = new ArrayList<Predstava>();
		String upit = "select idpred, nazivpred, trajanje, godinapre from predstava";
		// int idpred, String nazivpred, String trajanje, int godinapre
		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Predstava predstava = new Predstava(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4));
				listaPredstava.add(predstava);
			}
		}

		return listaPredstava;
	}

	@Override
	public Iterable<Predstava> findAllById(Iterable<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predstava findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Predstava entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(Iterable<Predstava> entities) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean imaPrikazivanje(Integer id) throws SQLException {
		String upit = "select * from prikazivanje where predstava_idpred = ?";

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
	public List<PredstavaDTO> nadjiSaNajvecim() throws SQLException {
		List<PredstavaDTO> listaPredstava = new ArrayList<PredstavaDTO>();
		String upit = "select predstava_idpred,nazivpred, round(avg(brojgled),2) as prosecan_broj_gledalaca from prikazivanje,predstava where prikazivanje.predstava_idpred = predstava.idpred group by predstava_idpred,nazivpred having round(avg(brojgled),2) >= all(select round(avg(brojgled),2) from prikazivanje group by predstava_idpred)";
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet resultSet = preparedStatement.executeQuery()){
			if(resultSet.isBeforeFirst()) {
				while(resultSet.next()) {
					PredstavaDTO predstava = new PredstavaDTO(resultSet.getInt(1),resultSet.getString(2),resultSet.getFloat(3));
					listaPredstava.add(predstava);
				}
			}
			
		}
		
		return listaPredstava;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
