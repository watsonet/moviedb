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
import ui.Watched;

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
	Watched watched;
	Subbed subbed;
//	JTextField newSodaManf = null;
	

	public UserData(Connection con, String username) {
		this.con = con;
		this.watched = new Watched(con);
		this.subbed = new Subbed(con);
		this.username = Main.currentUser;
		String[] a = {"Title", "Rating", "Release Date"};
		this.watchedColumns = a;
		String[] b = {"Streaming Service Name", "Subscribed?"};
		this.subColumns = b;

	}

	public JPanel createTabbedPane() {
		JTabbedPane tabPane = new JTabbedPane();
		
		JPanel watchedPanel = new JPanel();
		watchedPanel.setLayout(new BorderLayout());
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout()); 
		
		tabPane.add("Subscribed", subPanel);
		tabPane.add("Watched", watchedPanel);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(tabPane, BorderLayout.NORTH);
		watchedPanel.add(createWatchedPane(), BorderLayout.CENTER);
		watchedPanel.add(watchedTable());
		subPanel.add(createSubPane(), BorderLayout.CENTER);
		subPanel.add(subbedTable(),BorderLayout.SOUTH);

		return userPanel;
	}
	
	public JPanel createWatchedPane() {
		JPanel watchPanel = new JPanel();
		watchPanel.setLayout(new FlowLayout());
		newMediaName = new JTextField();
		newMediaName.setColumns(10);
//		newMediaName.setPreferredSize(new Dimension(100, 30));
		watchPanel.add(new JLabel("Media Name:"));
		watchPanel.add(newMediaName, BorderLayout.NORTH);
//		userPanel.add(newMediaName, BorderLayout.EAST);
		JButton addWatchedButton = new JButton("Add Media Watched");
		watchPanel.add(addWatchedButton, BorderLayout.NORTH);
		
//		newSubName = new JTextField();
//		newSubName.setColumns(10);

		
		
		return watchPanel;
	}
	public JPanel watchedTable() {
		JPanel watchPanel = new JPanel();
		this.watchedList = watched.getMediaInfo();		
		
		JTable table1 = new JTable();
		table1.setModel(new DefaultTableModel(watchedList, watchedColumns));

		JScrollPane table1Pane = new JScrollPane(table1);
		watchPanel.add(table1Pane, BorderLayout.PAGE_END);
		return watchPanel;
	}
	
	public JPanel createSubPane() {
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new FlowLayout());
//		newMediaName = new JTextField();
//		newMediaName.setColumns(10);
		newSubName = new JTextField();
		newSubName.setColumns(10);
		subPanel.add(new JLabel("Subscription Name:"));
		subPanel.add(newMediaName, BorderLayout.NORTH);
		
		JButton addSubButton = new JButton("Add Service Subscription");
		subPanel.add(addSubButton, BorderLayout.NORTH);
		

//		this.subList = subbed.getMediaInfo();		
//
//		JTable table1 = new JTable();
//		table1.setModel(new DefaultTableModel(subList, subColumns));
//		
//		JScrollPane table1Pane = new JScrollPane(table1);
//		subPanel.add(table1Pane, BorderLayout.SOUTH);
		
		return subPanel;
	}
	
	public JPanel subbedTable() {
		JPanel subPanel = new JPanel();
		this.subList = subbed.getMediaInfo();		

		JTable table1 = new JTable();
		table1.setModel(new DefaultTableModel(subList, subColumns));
		
		JScrollPane table1Pane = new JScrollPane(table1);
		subPanel.add(table1Pane);
		return subPanel;
	}
	
}
