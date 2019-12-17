package rs.ac.uns.ftn.db.jdbc.pozoriste.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDeleteDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeScenaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;

public interface PrikazivanjeDAO extends CRUDDao<Prikazivanje,Integer>{
	
	//metoda koja prikazuje sve predstave koje se prikazuju na odredjenoj sceni
	List<PrikazivanjeScenaDTO> findBySceneId(Integer idScene) throws SQLException;
	
	//metoda koja racuna ukupan broj gledalaca, prosecan broj gledalaca i broj prikazivanja za svaku predstavu
	HashMap<Integer, PrikazivanjeDTO> findSumAvgCountForShowingShow() throws SQLException;
	
	//metoda koja vraca id predstava koje se prikazuju bez duplikata
	List<Integer> findAllDistinctShowFromShowing() throws SQLException;
	
	//metoda koja prikazuje predstave ciji je broj gledalaca veci od broja sedista
	List<PrikazivanjeDeleteDTO> findShowingForDeleting() throws SQLException;
	
	//metoda koja prikazuje sva prikazivanje odredjene predstave
	List<Prikazivanje> findShowingByShowId(Integer idpred) throws SQLException;
	
	//metoda koja vrsi jednu transakcionu obradu u zadatku 5
	void deleteAndInsertIntoShowing(PrikazivanjeDeleteDTO deletedItem) throws SQLException;
	//metoda koja obezbedjuje konekciju kao parametar da bi se transakciona obrada vrsila nad istom konekcijom
	void deleteByIdExtended(Connection c, PrikazivanjeDeleteDTO deletedItem) throws SQLException;
	//metoda koja obezbedjuje konekciju kao parametar da bi se transakciona obrada vrsila nad istom konekcijom
	void save(Connection c, Prikazivanje entity) throws SQLException;
	

}
