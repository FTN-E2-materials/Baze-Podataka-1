package rs.ac.uns.ftn.db.jdbc.exam.ui_handler;

import java.util.Scanner;

public class MainUIHandler {

	public static Scanner sc = new Scanner(System.in);

	private final RadnikUIHandler radnikUIHandler = new RadnikUIHandler();
	private final ProjekatUIHandler projekatUIHandler = new ProjekatUIHandler();
	private final RadProjUIHandler radProjUIHandler = new RadProjUIHandler();
	private final ComplexFunctionalityUIHandler complexFunctionalityUIHandler = new ComplexFunctionalityUIHandler();

	public void handleMainMenu() {

		String answer;
		do {
			// TODO: update menu
			System.out.println("\nOdaberite opciju:");
			System.out.println("\n1 - Rukovanje radnicima");
			System.out.println("\n2 - Rukovanje projektima");
			System.out.println("\n3 - Rukovanje radproj tabelom");
			System.out.println("\n4 - Kompleksne funkcionalnosti");
			System.out.println("X - Izlazak iz programa");

			answer = sc.nextLine();

			switch (answer) {
			case "1":
				radnikUIHandler.handleRadnikMenu();
				break;
			case "2":
				projekatUIHandler.handleProjekatMenu();
				break;
			case "3":
				radProjUIHandler.handleRadProjMenu();
				break;
			case "4":
				complexFunctionalityUIHandler.handleComplexFunctionalityMenu();
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));

		sc.close();
	}

}
