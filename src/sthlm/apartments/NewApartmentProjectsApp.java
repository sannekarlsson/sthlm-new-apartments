package sthlm.apartments;

import javax.swing.SwingUtilities;

public class NewApartmentProjectsApp {
	
	private final static String FILE_NAME = "assets/ApartmentProjects.ser";
	private final static String URL = "https://bostad.stockholm.se/nyproduktion/";

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				runApp();

			}
		});

	}

	public static void runApp() {

		Database database = new Database();

		// Get previous projects
		database.loadPreviousProjectsFromFile(FILE_NAME);

		try {

			// Get current projects from url
			database.initializeCurrentProjects(URL);

			// Save current projects to file
			database.saveCurrentProjectsToFile(FILE_NAME);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Display newly added projects or message if none
			new MainView(database);
		}

	}

}
