package rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.proxy.annotation.Pre;
import rs.ac.uns.ftn.db.jdbc.pozoriste.connection.HikariCP;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PrikazivanjeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;
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
		List<Prikazivanje> listaPrikazivanja = new ArrayList<Prikazivanje>();
		String upit = "select rbr, datumpri, vremepri, brojgled, predstava_idpred, scena_idsce from prikazivanje";
		try (Connection connection = HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(upit);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Prikazivanje prikazivanje = new Prikazivanje(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
						resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6));
				listaPrikazivanja.add(prikazivanje);
				
			}
		}

		return listaPrikazivanja;
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

	@Override
	public PrikazivanjeDTO nadjiSumAvgCount(Integer id) throws SQLException { // ocekuje ID predstave kao parametar
		PrikazivanjeDTO prikazivanjeDTO = null;
		String upit = "select sum(brojgled),avg(brojgled),count(*) from prikazivanje group by predstava_idpred having predstava_idpred = ?";
		
		try(Connection connection = HikariCP.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(upit);){
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.isBeforeFirst()) {
					resultSet.next();
					prikazivanjeDTO = new PrikazivanjeDTO(resultSet.getInt(1),resultSet.getFloat(2),resultSet.getInt(3));

				}
			}
		}
		
		return prikazivanjeDTO;
	}

}
