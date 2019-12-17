package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PredstavaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;

public interface PredstavaDAO extends CRUDDao<Predstava, Integer> {

	// metoda koja vraca ukupan broj uloga po predstavi
	int findCountOfRoles(int idPred) throws SQLException;
	
	//metoda koja vraca najposecenije predstave (moze ih biti vise jednako posecenih zbog toga je lista, a ne jedna predstava)
	List<PredstavaDTO> findMostVisitedShows() throws SQLException;
}
