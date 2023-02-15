package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public abstract class Medias {
	protected static Connection con;
	protected String[][] mediaList = new String[0][0];
	protected String[][] sortedMediaList = new String[0][0];
	protected String[] columnNames;
	
	public Medias(Connection con) {
		Medias.con = con;
	}
	
	public JTabbedPane createPane() {
		JTabbedPane mediaPane = new JTabbedPane();
		
		JPanel everythingPanel = createPanel(null);
//		JPanel servicePanel = createPanel(Type.HOSTED);
		JPanel actorPanel = createPanel(Type.ACTED);
		
		mediaPane.add("Media", everythingPanel);
//		mediaPane.add("Streaming services", servicePanel);
		mediaPane.add("Actors/Actresses", actorPanel);
		
		return mediaPane;
	}

	/*
	 * Keeping getMediaInfo for a few reasons
	 * 
	 * 1) Its useful for testing
	 * 2) Can be used as a fallback
	 * 3) Potentially use it as a basis for getMediaHostedInfo and future methods like that
	 */
	protected abstract String[][] getMediaInfo();
	
	protected abstract String[][] getMediaHostedInfo();
	
	protected abstract String[][] getMediaActedInfo();
	
	private JPanel createPanel(Type type) {
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Select search terms");

		if (type == Type.ACTED) {
			this.mediaList = getMediaActedInfo();
		} else if (type == Type.HOSTED) {
			this.mediaList = getMediaHostedInfo();
		} else {
			// ideally don't use this, but its there as a fallback and/or for testing
			this.mediaList = getMediaInfo();			
		}

		JTable table = new JTable();
		table.setModel(new DefaultTableModel(mediaList, columnNames));
		JScrollPane tablePane = new JScrollPane(table);

		JPanel searchOptionsPanel = new JPanel();
		searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.X_AXIS));

		JPanel textFieldPanel = new JPanel();
		createTextBoxes(textFieldPanel, table);
		searchOptionsPanel.add(textFieldPanel);

		searchPanel.add(text);
		searchPanel.add(searchOptionsPanel);
		p1.add(searchPanel, BorderLayout.NORTH);
		p1.add(tablePane, BorderLayout.SOUTH);
		
		return p1;
	}
	
	protected void createTextBoxes(JPanel panel, JTable table) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel charStartLabel = new JLabel("Search by starting letter (empty is all)");
		JTextField charField = new JTextField(1);
		panel.add(charStartLabel);
		panel.add(charField);
		
		charField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = charField.getText();
				text = text.toLowerCase();
				if (text.length() <= 0) {
					sortMediaList('\0', table);
				} else {
					char first = text.charAt(0);
					
					sortMediaList(first, table);
				}
			}
		});
		
	}
	
	protected String[][] convertArrayListToArray(ArrayList<String[]> l) {
		String[][] a = l.toArray(new String[0][0]);
		return a;
	}
	
	protected void sortMediaList(char first, JTable table) {
		if (first == '\0') {
			table.setModel(new DefaultTableModel(mediaList, columnNames));
			return;
		}
		
		ArrayList<String[]> sorted = new ArrayList<>();
		for (String[] s: mediaList) {
			// s[0] will always be Title
			if (s[0].toLowerCase().charAt(0) == first) {
				sorted.add(s);
			}
		}
		
		this.sortedMediaList = convertArrayListToArray(sorted);
		
		table.setModel(new DefaultTableModel(sortedMediaList, columnNames));
	}
}
