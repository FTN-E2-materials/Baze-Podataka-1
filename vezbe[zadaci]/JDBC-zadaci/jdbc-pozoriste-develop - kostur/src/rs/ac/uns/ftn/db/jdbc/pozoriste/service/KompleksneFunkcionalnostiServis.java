package rs.ac.uns.ftn.db.jdbc.pozoriste.service;

import java.sql.SQLException;
import java.util.HashMap;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PredstavaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PrikazivanjeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.ScenaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PozoristeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PredstavaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PrikazivanjeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.ScenaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PrikazivanjeDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;
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
	private static final PrikazivanjeDAO prikazivanjeDAO = new PrikazivanjeDAOImpl();
	private static final PredstavaDAO predstavaDAO = new PredstavaDAOImpl();

	/**
	 * Metoda koja prikazuje sve scene odgovarajuceg pozorista
	 */

	public void prikaziListuScena() {
		System.out.println(Pozoriste.getFormattedHeader());
		try {
			for (Pozoriste pozoriste : pozoristeDAO.findAll()) { // prolazak kroz sva pozorista
				System.out.println(pozoriste);
				System.out.println("\t\t---------------------SCENE--------------------\n");

				if (scenaDAO.nadjiScenuPozorista(pozoriste.getId()).size() != 0) {
					// ako za odredjeni idPozoriste ima scena prolazimo kroz njih
					System.out.println("\t\t" + Scena.getFormattedHeader());
					for (Scena scena : scenaDAO.nadjiScenuPozorista(pozoriste.getId())) {
						System.out.println("\t\t" + scena);
					}

				} else { // inace scena za to pozoriste
					System.out.println("\t\tNEMA SCENE");
				}
				System.out.println(); // stampanje samo u novi red

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

	}

	/**
	 * Metoda koja prikazuje predstave koje se prikazuju i sva prikazivanja za tu predstavu
	 */
	public void prikaziPrikazivanePredstave() {
		System.out.println(Predstava.getFormattedHeader());
		
		try {
			HashMap<Integer, PrikazivanjeDTO> resultMap = prikazivanjeDAO.nadjiSumAvgCountPredstave();		//za svaku predstavu ( njen id)
			// imam koliko je ukupno bilo ljudi na njoj za sva prikazivanja,prosecno ljudi i koliko se ukupno prikazivala
			for(Integer predstavaId : prikazivanjeDAO.pronadjiPrikazivanePredstave()) {	//prolazak kroz svaku predstavu
				Predstava predstava = predstavaDAO.findById(predstavaId);
				System.out.println(predstava);
				
				System.out.println("\t\t" + Prikazivanje.getFormattedHeader());
				for(Prikazivanje p : prikazivanjeDAO.nadjiSvaPrikazivanjaPredstave(predstava.getIdpred())) {
					System.out.println("\t\t" + p);
				}
				System.out.println("\t\t------UKUPAN BROJ GLEDALACA--------PROSECAN BROJ GLEDALAC--------BROJ PRIKAZIVANJA------");
				System.out.println("\t\t\t\t" + resultMap.get(predstava.getIdpred()).toString());
				
				System.out.println("\n\n");		
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
