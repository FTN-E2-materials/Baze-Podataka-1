package rs.ac.uns.ftn.db.jdbc.ui_handler;

import rs.ac.uns.ftn.db.jdbc.pozoriste.service.KompleksneFunkcionalnostiServis;

public class ComplexQueryUIHandler {

	// Pravim objekat klase KompleksneFunkcionalnostiServis kako bih pozivao metode iz te klase
	// a te metode izvrsavaju ove zadatke
	
	private static final KompleksneFunkcionalnostiServis kompleksniUpitServis = new KompleksneFunkcionalnostiServis();
	
	
	public void handleComplexQueryMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite funkcionalnost:");
			System.out.println(
					"\n1 - Za svako pozoriste prikazati listu scena koje ima. Ukoliko pozoriste nema scenu ispisati: NEMA SCENE");
			System.out.println(
					"\n2 - Prikazati informacije o predstavama koje se prikazuju. Pored osnovnih informacija o predstavama prikazati sva prikazivanja za svaku od njih. Za svaku predstavu prikazati ukupan broj gledalaca, prosecan broj gledalaca i broj prikazivanja.");

			System.out.println(
					"\n3 - Prikazati nazive scena i broj sedista za sve scene ciji broj sedista je u intervalu plus/minus 20% od broja sedista"
							+ "\n    koje ima scena Joakim Vujic pozorista Knjazevsko-srpski teatar Kragujevac."
							+ "\n    Za sve predstave koje se prikazuju na tim scenama izracunati ukupan broj gledalaca. Prikazati samo one predstave ciji je ukupan broj gledalaca veci od 700."
							+ "\n    Za te predstave prikazati ukupan broj uloga na toj predstavi. Za scene na kojima se ne prikazuju predstave ispisati poruku ");
			System.out.println(
					"\n4 - Prikazati id, nazive i prosecan broj gledalaca predstava koje imaju najveci prosecan broj gledalaca. Za te predstave prikazati listu uloga. Pored toga prikazati koliko ukupno ima muskih uloga i koliko ukupno ima zenskih uloga..");
			System.out.println(
					"\n5 - Prikazati prikazivanja predstava u narednom periodu na scenama na kojima je broj gledalaca veci od broja sedista na toj sceni. Obrisati te torke. Raspodeliti sedista tako da se uvedu novi termini prikazivanja ove predstave na toj sceni. Zauzeti pune scene onoliko puta koliko je potrebno, i napuniti poslednju scenu sa onoliko mesta koliko je ostalo. Za novi datum uneti danasnji datum. Ispisati sva prikazivanja.");
			System.out.println(
					"\n6 - Za podelu napraviti CRUD operacije i kroz aplikaciju uneti nekoliko torki u tabelu Podela. Nakon toga napraviti izvestaj koji ce za svakog glumca prikazati ime glumca, naziv predstave, uloge u predstavi i honorar za koju je dobio najveci honorar. Za glumce koji nemaju podelu ispisati umesto naziva predstave i uloge NEMA, a umesto honorara staviti 0.");
			System.out.println(
					"\n7 - Prikazati sve uloge koje nisu podeljene. Zatim za te uloge prikazati spisak glumaca koji rade u pozoristu u kome se ta predstava prikazuje u nekom narednom periodu, a koji nemaju nijedan angazman.");
			System.out.println(
					"\n8 - Prikazati za svakog glumca kojem je dodeljena neka uloga, mbg, prezg, imeg, imepred, imeulo i spisak drugih glumaca kojima je dodeljena ista uloga. Pored toga neophodno je prikazati ukupan broj drugih glumaca kojima je dodeljena ista uloga.");
			System.out.println(
					"\n9 - Prikazati za svakog glumca kom je dodeljena uloga, mbg, prezg, imeg, kao i imepred, imeulo prikazati spisak glumaca za dodeljenu ulogu. Pored toga prikazati udeo tog glumca u ukupnom honoraru koji se izdvaja za dodeljenu ulogu za sve njene glumce. Udeo izraziti u procentima zaokruzeno na dve decimale.");
			System.out.println(
					"\n10 - Prikazati maticni broj, ime, prezime i platu glumca i listu ostalih glumaca i njihovih honorara za tu ulogu. Prikazati samo one glumce ciji je honorar za neku ulogu veci od prosecnog honorara za tu ulogu. ");
			System.out.println(
					"\n11 - Za svakog glumca prikazati koliki je ukupni honorar glumaca na svim njegovim predstavama. Uzeti u obzir samo glumce koji glume i u predstavama koje se ne prikazuju u njihovom maticnom pozoristu.");
			System.out.println(
					"\n12 - Rebalansirati opterecenje glumcima starijim od 60 godina tako da u jednoj nedelji mogu da glume u najvise dve predstave."+
					"\nSve uloge koje ostanu slobodne rebalansiranjem ovakvih glumaca, dodeliti drugim glumcima koji su bili prethodno u podeli za tu ulogu."+
					"\nAko ne postoje takvi glumci prikazati uloge koje su ostale neupraznjene."+
					"\nUkoliko novom podelom postoje prikazivanja predstava koje se daju u istom terminu sa istim glumcem u razlicitim ulogama, premestiti jedno od prikazivanja u drugi termin.");
			System.out.println(
					"\n13 -  Omoguciti interaktivni unos novog pozorista za koji je potrebno uneti sve osnovne podatke, scene i mesto pozorista."
					+"\nUkoliko mesto ne postoji dodati ga u bazu podataka, a ukoliko postoji samo povezati. Scene kreirati u bazi podataka.");
			
			
			System.out.println("\nX - Izlazak iz kompleksnih upita");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				kompleksniUpitServis.prikaziListuScena();
				break;
			case "2":
				kompleksniUpitServis.prikaziPrikazivanePredstave();
				break;
			case "3":
				// TODO implementirati
				break;
			case "4":
				// TODO implementirati
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

}
