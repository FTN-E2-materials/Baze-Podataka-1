package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Uloga;

public interface UlogaDAO extends CRUDDao<Uloga, Integer> {
	
	public List<Uloga> nadjiUloge(Integer id) throws SQLException;
	
	//ocekuje id predstave za koju ce traziti broj uloga
	public int nadjiBrojMuskihUloga(Integer id) throws SQLException;
	
	public int nadjiBrojZenskihUloga(Integer id) throws SQLException;
}
