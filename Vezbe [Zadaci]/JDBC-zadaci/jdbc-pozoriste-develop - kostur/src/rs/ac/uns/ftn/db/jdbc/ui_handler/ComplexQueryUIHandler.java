package rs.ac.uns.ftn.db.jdbc.ui_handler;

import java.sql.SQLException;

import oracle.jdbc.proxy.annotation.Pre;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PredstavaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PrikazivanjeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.ScenaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.UlogaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PozoristeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PredstavaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PrikazivanjeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.ScenaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.UlogaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dto.PredstavaDTO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Predstava;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Prikazivanje;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Scena;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Uloga;

public class ComplexQueryUIHandler {
	private static final PozoristeDAO pozoristeDAO = new PozoristeDAOImpl();
	private static final ScenaDAO scenaDAO = new ScenaDAOImpl();
	private static final PrikazivanjeDAO prikazivanjeDAO = new PrikazivanjeDAOImpl();
	private static final PredstavaDAO predstavaDAO = new PredstavaDAOImpl();
	private static final UlogaDAO ulogaDAO = new UlogaDAOImpl();
	public void handleComplexQueryMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite funkcionalnost:");
			System.out.println("\n1 - Zadatak1 ");// Za svako pozoriste prikazati listu scena koje ima. Ukoliko
													// pozoriste nema scenu ispisati: NEMA SCENE");

			System.out.println("\n2 - Zadatak2 ");// Prikazati informacije o predstavama koje se prikazuju. Pored
													// osnovnih informacija o predstavama prikazati sva prikazivanja za
													// svaku od njih. Za svako pozoriste prikazati ukupan broj
													// gledalaca, prosecan broj gledalaca i broj prikazivanja.");

			System.out.println("\n3 - Zadatak3 ");// Prikazati nazive scena i broj sedista za sve scene ciji broj
													// sedista je u intervalu plus/minus 20% od broja sedista koje ima
													// scena Joakim Vujic pozorista Knjazevsko-srpski teatar Kragujevac.
													// Za sve predstave koje se prikazuju na tim scenama izracunati
													// ukupan broj gledalaca. Prikazati samo one predstave ciji je
													// ukupan broj gledalaca veci od 700. Za te predstave prikazati
													// ukupan broj uloga na toj predstavi. Za scene na kojima se ne
													// prikazuju predstave ispisati poruku ");
			System.out.println("\n4 - Zadatak4 ");// Prikazati id, nazive i prosecan broj gledalaca predstava koje imaju
													// najveci prosecan broj gledalaca. Za te predstave prikazati listu
													// uloga. Pored toga prikazati koliko ukupno ima muskih uloga i
													// koliko ukupno ima zenskih uloga..");
//			System.out.println(
//					"\n5 - Prikazati prikazivanja predstava u narednom periodu na scenama na kojima je broj gledalaca veci od broja sedista na toj sceni. Obrisati te torke. Raspodeliti sedista tako da se uvedu novi termini prikazivanja ove predstave na toj sceni. Zauzeti pune scene onoliko puta koliko je potrebno, i napuniti poslednju scenu sa onoliko mesta koliko je ostalo. Za novi datum uneti danasnji datum. Ispisati sva prikazivanja.");
//			System.out.println(
//					"\n6 - Za podelu napraviti CRUD operacije i kroz aplikaciju uneti nekoliko torki u tabelu Podela. Nakon toga napraviti izvestaj koji ce za svakog glumca prikazati ime glumca, naziv predstave, uloge u predstavi i honorar za koju je dobio najveci honorar. Za glumce koji nemaju podelu ispisati umesto naziva predstave i uloge NEMA, a umesto honorara staviti 0.");
//			System.out.println(
//					"\n7 - Prikazati sve uloge koje nisu podeljene. Zatim za te uloge prikazati spisak glumaca koji rade u pozoristu u kome se ta predstava prikazuje u nekom narednom periodu, a koji nemaju nijedan angazman.");
//			System.out.println(
//					"\n8 - Prikazati za svakog glumca kojem je dodeljena neka uloga, mbg, prezg, imeg, imepred, imeulo i spisak drugih glumaca kojima je dodeljena ista uloga. Pored toga neophodno je prikazati ukupan broj drugih glumaca kojima je dodeljena ista uloga.");
//			System.out.println(
//					"\n9 - Prikazati za svakog glumca kom je dodeljena uloga, mbg, prezg, imeg, kao i imepred, imeulo prikazati spisak glumaca za dodeljenu ulogu. Pored toga prikazati udeo tog glumca u ukupnom honoraru koji se izdvaja za dodeljenu ulogu za sve njene glumce. Udeo izraziti u procentima zaokruzeno na dve decimale.");
//			System.out.println(
//					"\n10 - Prikazati maticni broj, ime, prezime i platu glumca i listu ostalih glumaca i njihovih honorara za tu ulogu. Prikazati samo one glumce ciji je honorar za neku ulogu veci od prosecnog honorara za tu ulogu. ");
//			System.out.println(
//					"\n11 - Za svakog glumca prikazati koliki je ukupni honorar glumaca na svim njegovim predstavama. Uzeti u obzir samo glumce koji glume i u predstavama koje se ne prikazuju u njihovom maticnom pozoristu.");
//			System.out.println(
//					"\n12 - Rebalansirati opterecenje glumcima starijim od 60 godina tako da u jednoj nedelji mogu da glume u najvise dve predstave."+
//					"\nSve uloge koje ostanu slobodne rebalansiranjem ovakvih glumaca, dodeliti drugim glumcima koji su bili prethodno u podeli za tu ulogu."+
//					"\nAko ne postoje takvi glumci prikazati uloge koje su ostale neupraznjene."+
//					"\nUkoliko novom podelom postoje prikazivanja predstava koje se daju u istom terminu sa istim glumcem u razlicitim ulogama, premestiti jedno od prikazivanja u drugi termin.");
//			System.out.println(
//					"\n13 -  Omoguciti interaktivni unos novog pozorista za koji je potrebno uneti sve osnovne podatke, scene i mesto pozorista."
//					+"\nUkoliko mesto ne postoji dodati ga u bazu podataka, a ukoliko postoji samo povezati. Scene kreirati u bazi podataka.");
//			
//			
			System.out.println("\nX - Izlazak iz kompleksnih upita");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				zadatak1();
				break;
			case "2":
				zadatak2();
				break;
			case "3":
				//zadatak3();
				break;
			case "4":
				zadatak4();
				break;
			case "5":
				// TODO implementirati
				break;
			case "6":
				// TODO implementirati
				break;
			case "7":
				// TODO implementirati
				break;
			case "8":
				// TODO implementirati
				break;
			case "9":
				// TODO implementirati
				break;
			case "10":
				// TODO implementirati
				break;
			case "11":
				// TODO implementirati
				break;
			case "12":
				// TODO implementirati
				break;
			case "13":
				// TODO implementirati
				break;

			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	private void zadatak1() {
		// Za svako pozorište prikazati listu scena koje ima. Ukoliko pozorište
		// nema scenu ispisati: NEMA SCENE
		try {
			System.out.println(Pozoriste.getFormattedHeader());
			for (Pozoriste pozoriste : pozoristeDAO.findAll()) {
				System.out.println(pozoriste);
				System.out.println(Scena.getFormattedHeader());
				int ispisScena = 0;
				for (Scena scena : scenaDAO.findAll()) {
					if (scena.getIdpoz() == pozoriste.getId()) {
						System.out.println(scena);
						ispisScena++;
					}
				}

				if (ispisScena == 0) {
					System.out.println("NEMA SCENE!");
				}
				System.out.println("\n\n");

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Prikazati informacije o predstavama koje se prikazuju. Pored osnovnih
	// informacija o predstavama prikazati sva prikazivanja za svaku od njih. Za
	// svako prikazivanje prikazati ukupan broj gledalaca, prosecan broj gledalaca i
	// broj prikazivanja.");
	private void zadatak2() {
		try {
			for(Predstava predstava : predstavaDAO.findAll()) {
				boolean nijeIspisanaPredstava = true;
				//System.out.println(Prikazivanje.getFormattedHeader());
				for(Prikazivanje prikazivanje : prikazivanjeDAO.findAll()) {
					if(predstavaDAO.imaPrikazivanje(predstava.getIdpred()) == true && nijeIspisanaPredstava == true) {
						System.out.println(Predstava.getFormattedHeader());
						System.out.println(predstava);
						nijeIspisanaPredstava= false;
					}
					if(predstava.getIdpred() == prikazivanje.getIdpred()) {
						System.out.println("\t\t" + prikazivanje);
					}
				}
				System.out.println("\t\t----- UKUPAN BROJ GLEDALACA ----- PROSECAN BROJ GLEDALACA ----- BROJ PRIKAZIVANJA -----");
				System.out.println(prikazivanjeDAO.nadjiSumAvgCount(predstava.getIdpred()));
				System.out.println("\n\n");
				
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void zadatak3() {

	}
	
	
	// Prikazati id, nazive i prosecan broj gledalaca predstava koje imaju
	// najveci prosecan broj gledalaca. Za te predstave prikazati listu
	// uloga. Pored toga prikazati koliko ukupno ima muskih uloga i
	// koliko ukupno ima zenskih uloga..");
	private void zadatak4() {
		try {
			System.out.println("\tID\t\t\tNAZIV_PREDSTAVE\t\t\tPROSECAN_BROJGLEDALACA");
			for(PredstavaDTO predstava : predstavaDAO.nadjiSaNajvecim()) {
				System.out.println(predstava);
				System.out.println("\t\t"+"---------------------ULOGE-------------------------");
				// funkcija kojoj prosledim predstava_idpred i on vrati listu uloga s tim predstava_idpred
				for(Uloga uloga : ulogaDAO.nadjiUloge(predstava.getIdp()) ) {
					System.out.println("\t\t" + Uloga.getFormattedHeader());
					System.out.println("\t\t" + uloga);
				}
				System.out.println("\n");
				System.out.println("Muskih uloga: " + ulogaDAO.nadjiBrojMuskihUloga(predstava.getIdp()));
				System.out.println("Zenskih uloga: " + ulogaDAO.nadjiBrojZenskihUloga(predstava.getIdp()));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void zadatak5() {

	}

}
