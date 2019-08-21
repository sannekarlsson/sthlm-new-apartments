package sthlm.apartments;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	
	private Database database;

	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel southPanel;
	private ProjectsPanel projectsPanel;
	
	public MainView(Database database) throws HeadlessException {
		super("Nya bostadsprojekt i Sthlm");
		
		this.database = database;
		
		initializeView();
	}
	
	private void initializeView() {
				
		setLayout(new BorderLayout());

		// NORTH
		northPanel = new JPanel();
		northPanel.setBorder(BorderFactory.createLineBorder(getBackground(),
				StyleProperties.getHorizontalMargin()));

		// WEST
		westPanel = new JPanel();
		westPanel.setBorder(BorderFactory.createLineBorder(getBackground(),
				StyleProperties.getVerticalMargin()));

		// CENTER -- PROJECTS
		projectsPanel = new ProjectsPanel();
		projectsPanel.setProjectLinkListener(new ProjectLinkListener() {
			
			@Override
			public void linkError() {
				JOptionPane.showMessageDialog(MainView.this, 
						"Kunde tyvärr inte nå det länkade projektet.",
						"Felmeddelande",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		projectsPanel.setApartmentProjects(database.getUpdatedProjects());

		// EAST
		eastPanel = new JPanel();
		eastPanel.setBorder(BorderFactory.createLineBorder(getBackground(),
				StyleProperties.getVerticalMargin()));

		// SOUTH
		southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createLineBorder(getBackground(),
				StyleProperties.getHorizontalMargin()));

		// Add elements
		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
		add(new JScrollPane(projectsPanel));

		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
