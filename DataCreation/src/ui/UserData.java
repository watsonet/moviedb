package ui;

import java.sql.Connection;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UserData {
	private Connection con;
	private String username;
//	private JMenu menu;
	JTextField newMediaName = null;
	JTextField newSubName = null;
	protected String[][] watchedList = new String[0][0];
	protected String[] watchedColumns;
	protected String[][] subList = new String[0][0];
	protected String[] subColumns;
//	JTextField newSodaManf = null;
	

	public UserData(Connection con, String username) {
		this.con = con;
		this.username = Main.currentUser;
		String[] a = {"Title", "Rating", "Release Date", "Runtime"};
		this.watchedColumns = a;
		String[] b = {"Name"};
		this.subColumns = b;

	}

	public JPanel createTabbedPane() {
		JTabbedPane mediaPane = new JTabbedPane();
		
		JPanel watchedPanel = new JPanel();
		watchedPanel.setLayout(new BorderLayout());
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
//		JPanel actorPanel = ;
		
		mediaPane.add("Watched", watchedPanel);
		mediaPane.add("Subscribed", subPanel);
//		mediaPane.add("Actors/Actresses", actorPanel);
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(mediaPane, BorderLayout.NORTH);
		watchedPanel.add(createWatchedPane(), BorderLayout.CENTER);
		subPanel.add(createSubPane(), BorderLayout.CENTER);


//		userPanel.setLayout(new FlowLayout());
//		newMediaName = new JTextField();
//		newMediaName.setColumns(10);
////		newMediaName.setPreferredSize(new Dimension(100, 30));
//		userPanel.add(new JLabel("Media Name:"));
//		userPanel.add(newMediaName);
////		userPanel.add(newMediaName, BorderLayout.EAST);
//		JButton addWatchedButton = new JButton("Add Media Watched");
//		userPanel.add(addWatchedButton);
//		
//		newSubName = new JTextField();
//		newSubName.setColumns(10);
//
////		newSubName.setPreferredSize(new Dimension(100, 30));
//		userPanel.add(new JLabel("Subscription Name:"));
//		userPanel.add(newSubName);
////		userPanel.add(newSubName, BorderLayout.WEST);
//		JButton addSubButton = new JButton("Add Service Subscription");
//		userPanel.add(addSubButton);
////		setLayout(new BorderLayout());
////		JPanel watchedPanel = watchedPanel();
////		add(createWatchedPanel, "Center");
//		
////		userPanel.setLayout(new BorderLayout(1,2));
//		JTable table1 = new JTable();
//		table1.setModel(new DefaultTableModel(watchedList, watchedColumns));
//		JScrollPane table1Pane = new JScrollPane(table1);
////		userPanel.add(table1Pane, BorderLayout.LINE_END);
//		
//		JTable table2 = new JTable();
//		table2.setModel(new DefaultTableModel(subList, subColumns));
//		JScrollPane table2Pane = new JScrollPane(table2);
////		userPanel.add(table2Pane, BorderLayout.LINE_START);

		//		JPanel searchOptionsPanel = new JPanel();
//		searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.X_AXIS));
//
//		JPanel textFieldPanel = new JPanel();
//		createTextBoxes(textFieldPanel, table);
//		searchOptionsPanel.add(textFieldPanel);
//
//		searchPanel.add(text);
//		searchPanel.add(searchOptionsPanel);
//		p1.add(searchPanel, BorderLayout.NORTH);
//		p1.add(tablePane, BorderLayout.SOUTH);
		return userPanel;
	}
	
	public JPanel createWatchedPane() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		newMediaName = new JTextField();
		newMediaName.setColumns(10);
//		newMediaName.setPreferredSize(new Dimension(100, 30));
		userPanel.add(new JLabel("Media Name:"));
		userPanel.add(newMediaName);
//		userPanel.add(newMediaName, BorderLayout.EAST);
		JButton addWatchedButton = new JButton("Add Media Watched");
		userPanel.add(addWatchedButton);
		
		newSubName = new JTextField();
		newSubName.setColumns(10);

//		newSubName.setPreferredSize(new Dimension(100, 30));
//		userPanel.add(new JLabel("Subscription Name:"));
//		userPanel.add(newSubName);
////		userPanel.add(newSubName, BorderLayout.WEST);
//		JButton addSubButton = new JButton("Add Service Subscription");
//		userPanel.add(addSubButton);
//		setLayout(new BorderLayout());
//		JPanel watchedPanel = watchedPanel();
//		add(createWatchedPanel, "Center");
		
//		userPanel.setLayout(new BorderLayout(1,2));
		JTable table1 = new JTable();
		table1.setModel(new DefaultTableModel(watchedList, watchedColumns));
		JScrollPane table1Pane = new JScrollPane(table1);
		userPanel.add(table1Pane, BorderLayout.LINE_END);
		
//		JTable table2 = new JTable();
//		table2.setModel(new DefaultTableModel(subList, subColumns));
//		JScrollPane table2Pane = new JScrollPane(table2);
//		userPanel.add(table2Pane, BorderLayout.LINE_START);

		//		JPanel searchOptionsPanel = new JPanel();
//		searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.X_AXIS));
//
//		JPanel textFieldPanel = new JPanel();
//		createTextBoxes(textFieldPanel, table);
//		searchOptionsPanel.add(textFieldPanel);
//
//		searchPanel.add(text);
//		searchPanel.add(searchOptionsPanel);
//		p1.add(searchPanel, BorderLayout.NORTH);
//		p1.add(tablePane, BorderLayout.SOUTH);
		return userPanel;
	}
	
	public JPanel createSubPane() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		newMediaName = new JTextField();
		newMediaName.setColumns(10);
//		newMediaName.setPreferredSize(new Dimension(100, 30));
		userPanel.add(new JLabel("Subscription Name:"));
		userPanel.add(newMediaName);
//		userPanel.add(newMediaName, BorderLayout.EAST);
		JButton addSubButton = new JButton("Add Service Subscription");
		userPanel.add(addSubButton);
		
		newSubName = new JTextField();
		newSubName.setColumns(10);

		JTable table1 = new JTable();
		table1.setModel(new DefaultTableModel(subList, subColumns));
		JScrollPane table1Pane = new JScrollPane(table1);
		userPanel.add(table1Pane, BorderLayout.LINE_END);
		return userPanel;
	}
	
	private JPanel watchedPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		newMediaName = new JTextField();
		newMediaName.setPreferredSize(new Dimension(100, 30));
//		newSodaManf = new JTextField();
//		newSodaManf.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("Media Name:"));
		toReturn.add(newMediaName);

//		toReturn.add(new JLabel("Manufacturer:"));
//		toReturn.add(newSodaManf);

		JButton addButton = new JButton("Add Media Watched");
		toReturn.add(addButton);
//		addButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				UserData.this.addWatched();
//
//			}
//
//		});
		return toReturn;
//	}
//
//	private boolean addWatched() {
//		if (UserData.addWatched(newMediaName.getText())) {
//			newMediaName.setText("");
////			newSodaManf.setText("");
//			return true;
//		}
//		return false;
//	}return userPanel;
	}
}
