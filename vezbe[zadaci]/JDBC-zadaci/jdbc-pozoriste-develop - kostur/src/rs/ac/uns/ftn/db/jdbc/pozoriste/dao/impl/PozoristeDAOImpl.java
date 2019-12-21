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

/**
 * Klasa koja implementira PozoristeDAO 
 * U njoj se sadrze sve osnovne funkcionalnosti za rad nad modelom Pozoriste
 * 
 * Na kolokvijumu se ocekuje da implementiramo sve ove metode
 */

public class PozoristeDAOImpl implements PozoristeDAO {
	
	/**
	 * Metoda koja prebrojava broj torki u tabeli/modelu Pozoriste
	 */
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from pozoriste";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery()	
			){
			
			if(resultSet.next() ) {
				return resultSet.getInt(1);
			}else {
				return -1;
			}
			
		}
		
	}

	/**
	 * Metoda koja sluzi za brisanje prosledjenog entiteta
	 */
	@Override
	public void delete(Pozoriste entity) throws SQLException {
		String query = "delete from pozoriste where idpoz = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
		    ){
			preparedStatement.setInt(1, entity.getId());
			preparedStatement.executeUpdate();
		}
		
		
	}
	
	/**
	 * Metoda koja sluzi za brisanje svih entiteta iz tabele Pozoriste
	 */
	@Override
	public void deleteAll() throws SQLException {
		String query = "delete from pozoriste";
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			){
			preparedStatement.executeUpdate();
		}
		
	}

	/**
	 * Metoda koja brise po prosledjenom id-u 
	 */
	@Override
	public void deleteById(Integer id) throws SQLException {
		String query = "delete from pozoriste where idpoz = ?";
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			){
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
		}
	}

	/**
	 * Metoda koja proverava da li postoji entitet sa prosledjenim id-om
	 */
	@Override
	public boolean existsById(Integer id) throws SQLException {
		String upit = "select * from pozoriste where idpoz = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			){
			preparedStatement.setInt(1, id);
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				return resultSet.isBeforeFirst();
			}
			
		}
	}

	/**
	 * Metoda koja pronalazi sva pozorista i vraca ih preko liste pozorista
	 */
	@Override
	public Iterable<Pozoriste> findAll() throws SQLException {
		String upit = "select idpoz, nazivpoz, adresapoz, sajt, idmes from pozoriste";
		List<Pozoriste> pozoristeList = new ArrayList<Pozoriste>();
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);
			ResultSet resultSet = preparedStatement.executeQuery()
			){
			
			while(resultSet.next() ) {
				Pozoriste pozoriste = new Pozoriste(resultSet.getInt(1),
						resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),
						resultSet.getString(5));
				pozoristeList.add(pozoriste);
			}
			
		}
		
		return pozoristeList;
		
	}

	/**
	 * Metoda koja pronalazi pozorista odredjenih ID-eva
	 */
	@Override
	public Iterable<Pozoriste> findAllById(Iterable<Integer> ids) throws SQLException {
		List<Pozoriste> pozoristeList = new ArrayList<>();
		
		StringBuilder stringBuilder = new StringBuilder();
		String pocetakUpita = "select idpoz, nazivpoz,adresapoz,sajt,idmes from pozoriste where idpoz in(";
		stringBuilder.append(pocetakUpita);
		
		// koliko imam ID-eva toliko stavim upitnika u moj upit
		for(@SuppressWarnings("unused") Integer id: ids) {					// posto id ne koristim nego mi samo sluzi za
			stringBuilder.append("?,");										// prolazak kroz svaki id, stavim da je unused
		}																	// da eclipse ne izbacuje bezveze warning
		
		stringBuilder.deleteCharAt(stringBuilder.length()-1);				// kako bih obrisao poslednji zarez
		stringBuilder.append(")");											// zatvorim zagradu u upitu
		
		String query = stringBuilder.toString();							// prebacim ga u String jer je bio prethodno StringBuilder tipa
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			){
			
			int i=0;
			for(Integer id : ids) {											// prolazim kroz svaki upitnik i zamenjujem ga 
				preparedStatement.setInt(++i, id);							// sa trenutnim id-em
			}
			
			try(ResultSet resultSet = preparedStatement.executeQuery(query)){
				if(resultSet.isBeforeFirst() ) {							// ako je pre pocetka,tj ako postoji
					resultSet.next(); 										// prebacim se na resenje
					
					pozoristeList.add(new Pozoriste(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5)) );
				}
				
			}
			
		}
		
		
		return pozoristeList;
	}

	/**
	 * Metoda koja pronalazi pozoriste sa odredjenim ID-em
	 */
	@Override
	public Pozoriste findById(Integer id) throws SQLException {
		String query = "select idpoz, nazivpoz, adresapoz, sajt, idmes from pozoriste where idpoz = ?";
		Pozoriste pozoriste = null;
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			){
			preparedStatement.setInt(1, id);
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.isBeforeFirst() ) {
					resultSet.next();
					pozoriste = new Pozoriste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5));
				}
			}
		}
		
		
		return pozoriste;
	}

	/**
	 * Metoda koja ubacuje entitet u tabelu ili ga menja ako vec postoji
	 */
	@Override
	public void save(Pozoriste entity) throws SQLException {
		String insertCommand = "insert into pozoriste (nazivpoz, adresapoz, sajt, idmes, idpoz) values (?, ?, ?, ?, ?, ?)";
		String updateCommand = "update pozoriste set nazivpoz=?, adresa=?, sajt=?, idmes=? where idpoz=?";
				
		int i = 0;							// inkrementer za postavljanje vrednosti umesto upitnika
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(existsById( entity.getId()) ? updateCommand : insertCommand)
			){
			
			preparedStatement.setString(++i, entity.getNaziv());
			preparedStatement.setString(++i, entity.getAdresa());
			preparedStatement.setString(++i, entity.getSajt());
			preparedStatement.setString(++i, entity.getMesto());
			preparedStatement.setInt(++i, entity.getId());
			
			preparedStatement.executeUpdate();
		}
		
		
				
	}

	/**
	 * Metoda koja ubacuje prosledjene entitete ili ih menja ako vec postoje
	 */
	@Override
	public void saveAll(Iterable<Pozoriste> entities) throws SQLException {
		String insertCommand = "insert into pozoriste (nazivpoz,adresapoz,sajt,idmes,idpoz) values (?, ?, ?, ?, ?)";
		String updateCommand = "update pozoriste set nazivpoz = ?, adresa=?, sajt=?, idmes=? where ipoz=?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateCommand);
			PreparedStatement preparedStatementInsert = connection.prepareStatement(insertCommand);
			){
			
			connection.setAutoCommit(false);						// iskljucujem autoCommit posto imamo vise entiteta pa ne zelimo
																	// da nam se desi da na pola transakcije dodje do prekida npr:
			// odlucimo da deo viska novca u kasi firme damo svakom radniku kao 13 tu platu, pa krenemo da delimo i uspemo da podelimo 
			// na samo 37% radnika.
			// U tom trenutku nestaje nam novca sto znaci da ne bi trebali da bilo kome damo 13 tu platu ili bi trebali da je smanjimo
			// - autoCommit bi posle svakog davanja 13 plate radniku(entitetu) odma isplatio to na racun( izvrio i updatovo )
			// i posle onda ne mozemo da mu skinemo sa racuna, zbog toga iskljucimo autoCommit koji nam da mogucnost da tek kada
			// zavrsimo sa svima, onda svima podelimo pare(commitujemo).
			
			for(Pozoriste entity : entities) {
				int i = 0;
				PreparedStatement preparedStatement;
				if(existsById(entity.getId())) {
					preparedStatement = preparedStatementUpdate;
				} else {
					preparedStatement = preparedStatementInsert;
				}
				
				preparedStatement.setString(++i, entity.getNaziv());
				preparedStatement.setString(++i, entity.getAdresa());
				preparedStatement.setString(++i, entity.getSajt());
				preparedStatement.setString(++i, entity.getMesto());
				preparedStatement.setInt(++i, entity.getId());
				
				preparedStatement.execute();
				
			}
 			
			connection.commit();
		}
		
	}

}












