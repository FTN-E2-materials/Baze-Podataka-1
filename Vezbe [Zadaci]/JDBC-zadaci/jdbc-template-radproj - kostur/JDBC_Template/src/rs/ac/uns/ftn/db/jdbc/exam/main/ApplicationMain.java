package rs.ac.uns.ftn.db.jdbc.exam.main;

import rs.ac.uns.ftn.db.jdbc.exam.ui_handler.MainUIHandler;

public class ApplicationMain {

	public static void main(String[] args) {

		// set application log level
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");

		MainUIHandler mainUIHandler = new MainUIHandler();
		mainUIHandler.handleMainMenu();

	}

}
