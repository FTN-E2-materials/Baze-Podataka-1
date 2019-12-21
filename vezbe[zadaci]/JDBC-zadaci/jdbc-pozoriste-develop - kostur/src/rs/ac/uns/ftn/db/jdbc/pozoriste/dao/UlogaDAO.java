package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Uloga;

public interface UlogaDAO extends CRUDDao<Uloga, Integer> { 
	
	List<Uloga> pronadjiUlogePredstave(int idpred) throws SQLException;
	
	Integer nadjiBrojUlogaPola(int idpred,String pol) throws SQLException;
	
}
