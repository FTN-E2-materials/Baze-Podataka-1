package rs.ac.uns.ftn.fb.jdbc.exam.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.exam.connection.HikariCP;
import rs.ac.uns.ftn.db.jdbc.exam.dao.RadnikDAO;
import rs.ac.uns.ftn.db.jdbc.exam.model.Radnik;

public class RadnikDAOImpl implements RadnikDAO {

	@Override
	public int count() throws SQLException {
		String upit = "select count(*) from radnik";
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet resultSet = preparedStatement.executeQuery()
			){
			if(resultSet.next()) {
				return resultSet.getInt(1);
			}else {
				return -1;
			}
		}
		
		
		
	}

	@Override
	public void delete(Radnik entity) throws SQLException {
		String upit = "delete from radnik where mbr = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);	
			){
			
			preparedStatement.setInt(1, entity.getMbr());
			preparedStatement.executeUpdate();
			
		}

	}

	@Override
	public void deleteAll() throws SQLException {
		String upit = "delete from radnik";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.executeUpdate();
		}
		
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		String upit = "delete from radnik where mbr = ? ";
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		String upit = "select * from radnik where mbr = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.setInt(1, id);
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				return resultSet.isBeforeFirst();							// ako jeste vrati true inace vrati false
			}
		}
		
	}

	@Override
	public Iterable<Radnik> findAll() throws SQLException {
		String upit = "select mbr, ime, prz, sef, plt, pre, god from radnik";
		List<Radnik> listaRadnika = new ArrayList<Radnik>();
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet resultSet = preparedStatement.executeQuery()){
			
			while(resultSet.next()) {
				Radnik radnik = new Radnik(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getDate(7));
				listaRadnika.add(radnik);
			}
		}
		return listaRadnika;
	}

	@Override
	public Iterable<Radnik> findAllById(Iterable<Integer> ids) throws SQLException {
		String upit = "select mbr, ime, prz, sef, plt, pre, god from radnik where mbr in (";
		List<Radnik> listaRadnika = new ArrayList<Radnik>();
		
		for(@SuppressWarnings("unused") Integer id: ids) {
			upit += "?,";
		}
		String query = upit.substring(0, upit.length()-1);
		query += ")";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			){
			
			int i =0;
			for(Integer id : ids) {
				preparedStatement.setInt(++i, id);
			}
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
					Radnik radnik = new Radnik(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
							resultSet.getInt(4), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getDate(7));
					listaRadnika.add(radnik);
				}
			}
			
		}
		
		return listaRadnika;
	}

	@Override
	public Radnik findById(Integer id) throws SQLException {
		String upit = "select mbr, ime, prz, sef, plt, pre, god from radnik where mbr = ?";
		Radnik radnik = null;
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.isBeforeFirst()) {
					resultSet.next();
					
					radnik = new Radnik(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4),
							resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getDate(7));
				}
				
				
			}
		}
		return radnik;
	}

	@Override
	public void save(Radnik entity) throws SQLException {
		String insertuj = "insert into radnik (ime, prz, sef, plt, pre, god, mbr) values(?, ?, ?, ?, ?, ?, ?)";
		String apdejtuj = "update radnik set ime = ?, prz = ?, sef = ?, plt = ?, pre = ?, god = ? where mbr = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement( existsById(entity.getMbr()) ? apdejtuj : insertuj )){
			int i =0;
			preparedStatement.setString(++i, entity.getIme());
			preparedStatement.setString(++i, entity.getPrz());
			preparedStatement.setInt(++i, entity.getSef());
			preparedStatement.setDouble(++i, entity.getPlt());
			preparedStatement.setDouble(++i, entity.getPre());
			preparedStatement.setDate(++i, (Date) entity.getGod());
			preparedStatement.setInt(++i, entity.getMbr());
			
			preparedStatement.executeUpdate();
			
		}
	}

	@Override
	public void saveAll(Iterable<Radnik> entities) throws SQLException {
		String insertuj = "insert into radnik (ime, prz, sef, plt, pre, god, mbr) values(?, ?, ?, ?, ?, ?, ?)";
		String apdejtuj = "update radnik set ime = ?, prz = ?, sef = ?, plt = ?, pre = ?, god = ? where mbr = ?";
		
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatementInsert = connection.prepareStatement(insertuj);
			PreparedStatement preparedStatementUpdate = connection.prepareStatement(apdejtuj);){
			
			connection.setAutoCommit(false);
			
			for(Radnik entity : entities) {
				
				PreparedStatement preparedStatement;
				
				if(existsById(entity.getMbr())) {
					preparedStatement = preparedStatementUpdate;
				}else {
					preparedStatement = preparedStatementInsert;
				}
				
				int i=0;
				
				preparedStatement.setString(++i, entity.getIme());
				preparedStatement.setString(++i, entity.getPrz());
				preparedStatement.setInt(++i, entity.getSef());
				preparedStatement.setDouble(++i, entity.getPlt());
				preparedStatement.setDouble(++i, entity.getPre());
				preparedStatement.setDate(++i, (Date) entity.getGod());
				preparedStatement.setInt(++i, entity.getMbr());
				
				preparedStatement.executeUpdate();
			}
			
			connection.commit();
		}

	}

}
