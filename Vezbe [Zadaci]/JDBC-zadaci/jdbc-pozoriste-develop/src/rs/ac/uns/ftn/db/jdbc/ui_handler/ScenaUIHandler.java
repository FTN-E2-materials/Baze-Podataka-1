package rs.ac.uns.ftn.db.jdbc.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.ScenaDAO;
import rs.ac.uns.ftn.db.jdbc.pozoriste.dao.impl.ScenaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.pozoriste.model.Scena;

public class ScenaUIHandler {
	
	private static final ScenaDAO scenaDAO = new ScenaDAOImpl();

	public void handleScenaMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad scenama:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jedne scene");
			System.out.println("4 - Unos vise scena");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja scenama");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById();
				break;
			case "3":
				
				break;
			case "4":
				
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}
	
	private void showAll() {
		System.out.println(Scena.getFormattedHeader());

		try {
			for (Scena scena : scenaDAO.findAll()) {
				System.out.println(scena);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private void showById() {
		System.out.println("IDSCE: ");
		int id = Integer.parseInt(MainUIHandler.sc.nextLine());

		try {
			Scena scena = scenaDAO.findById(id);

			System.out.println(Scena.getFormattedHeader());
			System.out.println(scena);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
