package rs.ac.uns.ftn.db.jdbc.pozoriste.service;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.ScenaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PozoristeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.ScenaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Scena;

/**
 * Klasa u kojoj se implementiraju metode koje izvrsavaju kompleksne upita 
 * 
 * @author Vaxi
 *
 */

public class KompleksneFunkcionalnostiServis {

	private static final PozoristeDAO pozoristeDAO = new PozoristeDAOImpl();
	private static final ScenaDAO scenaDAO = new ScenaDAOImpl();
	
	/**
	 * Metoda koja prikazuje sve scene odgovarajuceg pozorista
	 */
	public void prikaziListuScena() {
		System.out.println(Pozoriste.getFormattedHeader());
		try {
			for (Pozoriste pozoriste : pozoristeDAO.findAll()) {	//prolazak kroz sva pozorista
				System.out.println(pozoriste);
				System.out.println("\t\t---------------------SCENE--------------------\n");
				
				if(scenaDAO.nadjiScenuPozorista(pozoriste.getId()).size() != 0 ) {
					// ako za odredjeni idPozoriste ima scena prolazimo kroz njih
					System.out.println("\t\t" + Scena.getFormattedHeader());
					for(Scena scena : scenaDAO.nadjiScenuPozorista(pozoriste.getId())) {
						System.out.println("\t\t"+scena);
					}
					
				}else { // inace scena za to pozoriste
					System.out.println("\t\tNEMA SCENE");
				}
				System.out.println(); //stampanje samo u novi red
				
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
		
		
	}
	
	/**
	 * Metoda koja prikazuje predstave koje se prikazuju i sva prikazivanja za tu predstavu
	 */
	public void prikaziPrikazivanePredstave() {
		//System.out.println(Predstava.getFormattedHeader());
		
		
		
	}
	
	
}

	