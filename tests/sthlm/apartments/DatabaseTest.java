package sthlm.apartments;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

	/*
	 * Start local test server on port 8000 in terminal:
	 * cd path/to/project/test-url
	 * python3 -m http.server 
	 */
	private Database database;
	private String testUrlFirst = "http://localhost:8000/Nyproduktion_Stockholm_first.html";
	private String testUrlSecond = "http://localhost:8000/Nyproduktion_Stockholm_second.html";
	private static String directory = "test-files";	
	private static String testFile = directory + "/testFile.txt";
	
	@Before
	public void setUp() {
		database = new Database();
	}
	
	@AfterClass
	public static void deleteTestAssets() {
		new File(testFile).delete();
		new File(directory).delete();
	}
	
	@Test
	public void testSaveToFile() throws IOException {
		assertFalse(database.saveCurrentProjectsToFile(directory + "/testFileFalse.txt"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testInitializationNullException() throws IOException {
		database.initializeCurrentProjects(testUrlFirst);
	}
	
	@Test (expected = IOException.class)
	public void testInitializationWithWrongUrl() throws IOException {
		database.initializeCurrentProjects("https://localhost/wrong/url");
	}
	
	@Test
	public void testInitialization() throws IOException {
		database.loadPreviousProjectsFromFile(directory + "/testFileNone.txt");
		database.initializeCurrentProjects(testUrlFirst);
		assertNotNull(database.getUpdatedProjects());
	}
	
	@Test
	public void testUpdatedProjects() throws IOException {
		database.loadPreviousProjectsFromFile(testFile);
		assertNull(database.getUpdatedProjects());
		
		database.initializeCurrentProjects(testUrlFirst);
		assertTrue(database.saveCurrentProjectsToFile(testFile));
		assertEquals(19, database.getUpdatedProjects().size());
		
		// Second visit to get updates between visits
		database.loadPreviousProjectsFromFile(testFile);
		database.initializeCurrentProjects(testUrlSecond);
		assertEquals(3, database.getUpdatedProjects().size());
	}
	
}
