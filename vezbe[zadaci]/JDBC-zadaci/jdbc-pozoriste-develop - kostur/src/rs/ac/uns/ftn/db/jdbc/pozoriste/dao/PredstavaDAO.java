package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;




import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PredstavaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;


public interface PredstavaDAO extends CRUDDao<Predstava, Integer> {
	
	public boolean imaPrikazivanje(Integer id) throws SQLException;
	
	public List<PredstavaDTO> nadjiSaNajvecim() throws SQLException;
}
