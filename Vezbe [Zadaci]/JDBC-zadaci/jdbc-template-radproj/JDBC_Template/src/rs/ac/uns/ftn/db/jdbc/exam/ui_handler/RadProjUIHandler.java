package rs.ac.uns.ftn.db.jdbc.exam.ui_handler;

public class RadProjUIHandler {
	// private static final RadProjDAO radprojDAO = new RadProjDAOImpl();

	public void handleRadProjMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad sa radproj tabelom:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog");
			System.out.println("4 - Unos vise");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja radproj tabelom");

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
