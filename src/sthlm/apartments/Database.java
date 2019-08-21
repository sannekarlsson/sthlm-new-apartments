package sthlm.apartments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Database {

	private Document doc;
	private List<ApartmentProject> previousApartmentProjects;
	private List<ApartmentProject> currentApartmentProjects;
	private List<ApartmentProject> updatedApartmentProjects;

	public Database() {
		this.currentApartmentProjects = new ArrayList<ApartmentProject>();
	}

	@SuppressWarnings("unchecked")
	protected void loadPreviousProjectsFromFile(String fileName) {

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				fileName))) {
			previousApartmentProjects = (ArrayList<ApartmentProject>) ois
					.readObject();
		} catch (Exception e) {
			// No previous projects available
			previousApartmentProjects = new ArrayList<ApartmentProject>();
		}
	}

	protected boolean saveCurrentProjectsToFile(String fileName) throws IOException {

		// Only save if fetched new ones, otherwise will erase previous ones
		if (currentApartmentProjects == null
				|| currentApartmentProjects.isEmpty()) {
			return false;
		}

		// Create file and directory if it doesn't exist
		File projectFile = new File(fileName);

		if (projectFile.getParent() != null) {
			projectFile.getParentFile().mkdirs();
		}

		projectFile.getAbsoluteFile().createNewFile();
		
		// Write to file
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(projectFile))) {
			oos.writeObject(currentApartmentProjects);

		} catch (FileNotFoundException fnfex) {
			return false;
		}

		return true;
	}

	protected void initializeCurrentProjects(String url) throws IOException {
		try {
			doc = Jsoup.connect(url).get();
			initializeApartmentProjects();
		} catch (IOException e) {
			throw new IOException(
					String.format(
							"Could not fetch data from %s\n"
						  + "Maybe the url has changed or there's no internet connection?",
							url));
		}
	}

	/*
	 * Store current apartment projects and filter out and store updated ones
	 * 
	 * If no element is found with the specified id or tag, leave the list of
	 * updatedApartmentProjects as null as it may indicate changes in the html
	 * document. Use null value to display proper message in the view.
	 */
	private void initializeApartmentProjects() {

		Element container = doc.getElementById("kommandeNyproduktionContainer");

		// No element found with the above id
		if (container == null) {
			return;
		}

		Elements rows = container.getElementsByTag("a");

		// Instantiate list as elements have been found at the above tag
		if (!rows.isEmpty()) {
			this.updatedApartmentProjects = new ArrayList<ApartmentProject>();
		}

		for (Element row : rows) {
			try {
				
				ApartmentProject apProject = createApartmentProject(row);
				currentApartmentProjects.add(apProject);
				addToUpdatedApartmentProjects(apProject);
				
			} catch (IllegalArgumentException e) {
				// Some of the html elements were not found or may have been altered.
				// Set updatedApartmentProjects to null so the view can display proper message.
				this.updatedApartmentProjects = null;
				return;
			}
		}
	}

	/*
	 * Create an instance of ApartmentProject from values in element
	 */
	private ApartmentProject createApartmentProject(Element row) {

		String area = row.select("div.m-apartment-card-np__area").text();
		String address = row.select("div.m-apartment-card-np__address").text();
		String link = row.absUrl("href");
		String criteria = row.select("span.m-tag").text();

		return new ApartmentProject(area, address, link, criteria);
	}

	private void addToUpdatedApartmentProjects(ApartmentProject apProject) {

		if (!previousApartmentProjects.contains(apProject)) {
			updatedApartmentProjects.add(apProject);
		}
	}

	protected List<ApartmentProject> getUpdatedProjects() {
		return updatedApartmentProjects;
	}

}
