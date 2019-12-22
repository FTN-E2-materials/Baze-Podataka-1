package rs.ac.uns.ftn.db.jdbc.exam.ui_handler;

//import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.exam.dao.RadnikDAO;
import rs.ac.uns.ftn.db.jdbc.exam.model.Radnik;
import rs.ac.uns.ftn.fb.jdbc.exam.dao.impl.RadnikDAOImpl;

public class RadnikUIHandler {
	private static final RadnikDAO radnikDAO = new RadnikDAOImpl();

	public void handleRadnikMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad sa radnicima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog");
			System.out.println("4 - Unos vise");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("7 - Prikaz svih odredjenih id-eva");
			System.out.println("X - Izlazak iz rukovanja radnicima");

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
			case "7":
				showAllById();
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}
	private void showAll() {
		
	}
	private void showAllById() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(10);
		ids.add(20);
		
		try {
			Iterable<Radnik> radnici = radnikDAO.findAllById(ids);
			for(Radnik radnik : radnici) {
				System.out.println(radnik);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Radnik radnik = radnikDAO.findById(10);
			System.out.println(radnik);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void showById() {

	}

	private void handleSingleInsert() {

	}

	private void handleUpdate() {

	}

	private void handleDelete() {

	}

	private void handleMultipleInserts() {

	}

}
