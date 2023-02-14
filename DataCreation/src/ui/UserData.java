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
//	JTextField newSodaManf = null;
	

	public UserData(Connection con, String username) {
		this.con = con;
		this.watched = new Watched(con);
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
		
		mediaPane.add("Watched", watchedPanel);
		mediaPane.add("Subscribed", subPanel);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(mediaPane, BorderLayout.NORTH);
		watchedPanel.add(createWatchedPane(), BorderLayout.SOUTH);
		subPanel.add(createSubPane(), BorderLayout.SOUTH);

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

//		this.watchedList = watched.getMediaInfo();		
		
		JTable table1 = new JTable();
		table1.setModel(new DefaultTableModel(watchedList, watchedColumns));

		JScrollPane table1Pane = new JScrollPane(table1);
		userPanel.add(table1Pane, BorderLayout.LINE_END);
		
		return userPanel;
	}
	
	public JPanel createSubPane() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		newMediaName = new JTextField();
		newMediaName.setColumns(10);
		userPanel.add(new JLabel("Subscription Name:"));
		userPanel.add(newMediaName);
		
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
	
	
}
