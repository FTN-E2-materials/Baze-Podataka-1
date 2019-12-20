package rs.ac.uns.ftn.db.jdbc.ui_handler;

import java.util.Scanner;

public class MainUIHandler {

	public static Scanner sc = new Scanner(System.in);

	private final PozoristeUIHandler pozoristeUIHandler = new PozoristeUIHandler();
	private final ScenaUIHandler scenaUIHandler = new ScenaUIHandler();
	private final ComplexQueryUIHandler complexQueryUIHandler = new ComplexQueryUIHandler();
	
	public void handleMainMenu() {

		String answer;
		do {
			System.out.println("\nOdaberite opciju:");
			System.out.println("1 - Rukovanje pozoristima");
			System.out.println("2 - Rukovanje scenama");
			System.out.println("3 - Kompleksni upiti");
			System.out.println("X - Izlazak iz programa");

			answer = sc.nextLine();

			switch (answer) {
			case "1":
				pozoristeUIHandler.handlePozoristeMenu();
				break;
			case "2":
				scenaUIHandler.handleScenaMenu();
				break;
			case "3":
				complexQueryUIHandler.handleComplexQueryMenu();
			case "4":

			case "5":

			}

		} while (!answer.equalsIgnoreCase("X"));

		sc.close();
	}

}
