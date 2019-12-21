package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Scena;

public interface ScenaDAO extends CRUDDao<Scena, Integer> {
	
	// dodajemo metodu koja ce vracati listu scena za odredjeno pozoriste
	public List<Scena> nadjiScenuPozorista(Integer idPozorista) throws SQLException;
}
