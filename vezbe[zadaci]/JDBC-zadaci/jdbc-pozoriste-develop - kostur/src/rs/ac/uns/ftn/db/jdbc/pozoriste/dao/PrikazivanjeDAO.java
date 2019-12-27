package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;

public interface PrikazivanjeDAO extends CRUDDao<Prikazivanje, Integer> {
	
	public PrikazivanjeDTO nadjiSumAvgCount(Integer id) throws SQLException;
}
