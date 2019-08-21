package sthlm.apartments;

import java.awt.Color;
import java.awt.Desktop;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

@SuppressWarnings("serial")
public class ProjectsPanel extends JPanel {
	
	private JEditorPane projectDisplay;
	private ProjectLinkListener projectLinkListener;
	
	public ProjectsPanel() {	
		
		projectDisplay = new JEditorPane("text/html", "");
		
		setBackground(Color.WHITE);
		projectDisplay.setBorder(BorderFactory.createLineBorder(Color.WHITE, StyleProperties.getHorizontalMargin()));
		projectDisplay.setEditable(false);
		projectDisplay.addHyperlinkListener(new LinkListener());

		HTMLEditorKit editorKit = new HTMLEditorKit();
		projectDisplay.setEditorKit(editorKit);

		StyleSheet styles = editorKit.getStyleSheet();
		styles.addRule("h1 {padding-top: 18px;}");
		styles.addRule("a {" + "color: #777777; " + "font: sans-serif; " + "}");

		add(projectDisplay);
		
	}
	
	/*
	 * Set apartment projects 
	 */
	public void setApartmentProjects(List<ApartmentProject> apartmentProjects) {
		
		StringBuilder projects = new StringBuilder();
		
		if (apartmentProjects == null) {
			projects.append("<h2>Kunde inte hitta några bostadsprojekt.</h2>" +
							"<p>Kontrollera internetuppkoppling eller om url och html-struktur har förändrats.</p>");
		} else if (apartmentProjects.isEmpty()) {
			projects.append("<h2>Inga nya bostadsprojekt har tillkommit sedan sist.</h2>");
			
		} else {
			
			for (ApartmentProject apProject : apartmentProjects) {

				projects.append("<h1>" + apProject.getArea() + " ");
				projects.append(apProject.getAddress() + "</h1>");

				if (!apProject.getCriteria().isEmpty())
					projects.append("" + apProject.getCriteria() + "\n");

				projects.append("<h3><a href=\"" + apProject.getLink()
						+ "\">Gå till projektsidan för " + apProject.getAddress()
						+ " - hos Bostad Stockholm</a></h3>");
			}	
		}
		
		projectDisplay.setText(projects.toString());
	}
	
	
	public void setProjectLinkListener(ProjectLinkListener projectLinkListener) {
		this.projectLinkListener = projectLinkListener;
	}
	
	/*
	 * Open a window/tab in user's default browser on link click
	 */
	class LinkListener implements HyperlinkListener {

		@Override
		public void hyperlinkUpdate(HyperlinkEvent linkEvent) {
			
			if (linkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				
				try {
					
					Desktop.getDesktop().browse(linkEvent.getURL().toURI());
					
				} catch (Exception e) {
					
					if (projectLinkListener != null) {
						projectLinkListener.linkError();
					}
				}
			}
		}	
		
	}

}
