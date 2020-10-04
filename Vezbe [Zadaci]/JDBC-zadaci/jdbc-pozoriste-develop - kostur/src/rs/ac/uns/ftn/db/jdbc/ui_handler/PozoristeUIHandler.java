package rs.ac.uns.ftn.db.jdbc.ui_handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.PozoristeDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.PozoristeDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Pozoriste;

public class PozoristeUIHandler {

	private static PozoristeDAO pozoristeDAO = new PozoristeDAOImpl();

	public void handlePozoristeMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad pozoristima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog pozorista");
			System.out.println("4 - Unos vise pozorista");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja pozoristima");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById();
				break;
			case "3":
				handleSingleInsert();
				break;
			case "4":
				handleMultipleInserts();
				break;
			case "5":
				handleUpdate();
				break;
			case "6":
				handleDelete();
				break;

			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	private void showAll() {
		try {
			System.out.println(Pozoriste.getFormattedHeader());

			for (Pozoriste pozoriste : pozoristeDAO.findAll()) {
				System.out.println(pozoriste);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private void showById() {
		try {
			System.out.println("UNESITE IDPOZ: ");
			int id = Integer.parseInt(MainUIHandler.sc.nextLine());

			System.out.println(Pozoriste.getFormattedHeader());
			System.out.println(pozoristeDAO.findById(id));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private void handleSingleInsert() {// idpoz, nazivpoz, adresapoz, sajt, mesto_idm

		try {
			System.out.println("UNESITE IDPOZ: ");
			int idpoz = Integer.parseInt(MainUIHandler.sc.nextLine());

			if (pozoristeDAO.existsById(idpoz)) {
				System.out.println("TO POZORISTE VEC POSTOJI, NE MOZES GA OPET UBACITI");
				return;
			}
			System.out.println("NAZIVPOZ: ");
			String nazivpoz = MainUIHandler.sc.nextLine();

			System.out.println("ADRESAPOZ: ");
			String adresapoz = MainUIHandler.sc.nextLine();

			System.out.println("SAJT: ");
			String sajt = MainUIHandler.sc.nextLine();

			System.out.println("MESTO_IDM: ");
			String mesto_idm = MainUIHandler.sc.nextLine();

			Pozoriste pozoriste = new Pozoriste(idpoz, nazivpoz, adresapoz, sajt, mesto_idm);

			pozoristeDAO.save(pozoriste);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private void handleUpdate() {
		try {
			System.out.println("UNESITE IDPOZ: ");
			int idpoz = Integer.parseInt(MainUIHandler.sc.nextLine());

			if (!pozoristeDAO.existsById(idpoz)) {
				System.out.println("TO POZORISTE NE POSTOJI, NE MOZES GA MENJATI AKO NE POSTOJI");
				return;
			}
			System.out.println("NAZIVPOZ: ");
			String nazivpoz = MainUIHandler.sc.nextLine();

			System.out.println("ADRESAPOZ: ");
			String adresapoz = MainUIHandler.sc.nextLine();

			System.out.println("SAJT: ");
			String sajt = MainUIHandler.sc.nextLine();

			System.out.println("MESTO_IDM: ");
			String mesto_idm = MainUIHandler.sc.nextLine();

			Pozoriste pozoriste = new Pozoriste(idpoz, nazivpoz, adresapoz, sajt, mesto_idm);

			pozoristeDAO.save(pozoriste);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void handleDelete() {
		try {
			System.out.println("UNESITE IDPOZ: ");
			int id = Integer.parseInt(MainUIHandler.sc.nextLine());

			pozoristeDAO.deleteById(id);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void handleMultipleInserts() {
		String odgovor;
		do {

			handleSingleInsert();
			System.out.println("Kraj programa - X");
			System.out.println("Nastavak programa - bilo koji karakter tastature");
			odgovor = MainUIHandler.sc.nextLine();

		} while (!odgovor.equalsIgnoreCase("X"));
	}

}
