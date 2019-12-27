package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.HikariCP;
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
		// TODO Auto-generated method stub
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

	@Override
	public List<Uloga> nadjiUloge(Integer id) throws SQLException {
		List<Uloga> listaUloga = new ArrayList<Uloga>();
		String upit = "select imeulo, pol, vrstaulo, predstava_idpred from uloga where predstava_idpred = ?";
		//String imeulo, String pol, String vrstaulo, int idpred
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
					Uloga uloga = new Uloga(resultSet.getString(1),resultSet.getString(2),
							resultSet.getString(3),resultSet.getInt(4));
					listaUloga.add(uloga);
				}
			}
			
		}
		
		return listaUloga;
	}

	@Override
	public int nadjiBrojMuskihUloga(Integer id) throws SQLException {
		String upit = "select count(pol) from uloga where pol = 'm' and predstava_idpred = ? group by predstava_idpred";
		int brojacUloga=0;
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.isBeforeFirst()) {
					resultSet.next();
					brojacUloga=resultSet.getInt(1);
				}
			}
			
		}
		
		
		
		return brojacUloga;
	}
	@Override
	public int nadjiBrojZenskihUloga(Integer id) throws SQLException {
		String upit = "select count(pol) from uloga where pol = 'z' and predstava_idpred = ? group by predstava_idpred";
		int brojacUloga=0;
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.isBeforeFirst()) {
					resultSet.next();
					brojacUloga=resultSet.getInt(1);
				}
			}
			
		}
		
		
		
		return brojacUloga;
	}

}
