package sthlm.apartments;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ApartmentProjectTest {
	
	private ApartmentProject apProject;

	@Before
	public void setUp() throws Exception {
		apProject = new ApartmentProject("area", "address", "link", "");
	}

	@Test
	public void testString() {
		assertEquals("area address | link", apProject.toString());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyArguments() {
		new ApartmentProject("", "", "", "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullArguments() {
		new ApartmentProject(null, null, null, null);
	}
	
	@Test 
	public void testNullLinkAndCriteria() {
		apProject = new ApartmentProject("area", "address", null, null);
		assertNotNull(apProject);
	}
	
	@Test 
	public void testNullLinkAndCriteriaString() {
		apProject = new ApartmentProject("area", "address", null, null);
		assertEquals("area address", apProject.toString());
	}
	
	@Test
	public void testVariables() {
		assertEquals("area", apProject.getArea());
		assertEquals("address", apProject.getAddress());
		assertEquals("link", apProject.getLink());
		assertEquals("", apProject.getCriteria());
		ApartmentProject apArea2 = new ApartmentProject("area", "address", "link", "criteria");
		assertEquals("area", apArea2.getArea());
		assertEquals("address", apArea2.getAddress());
		assertEquals("link", apArea2.getLink());
		assertEquals("criteria", apArea2.getCriteria());
	}


}
