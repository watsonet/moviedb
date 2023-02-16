package ui;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import ui.Watched;

public class UserData {
	private Connection con;
	private String username;
//	private JMenu menu;
	JTextField newMediaName = null;
	JTextField newSubName = null;
	protected Object[][] watchedList = new String[0][0];
	protected String[] watchedColumns;
	protected Object[][] subList = new String[0][0];
	protected String[] subColumns;
	Watched watched;
	Subbed subbed;
	JTable table1;
	JTable table2;
//	JTextField newSodaManf = null;
	

	public UserData(Connection con, String username) {
		this.con = con;
		this.watched = new Watched(con);
		this.subbed = new Subbed(con);
		this.username = Main.currentUser;
		String[] a = {"ID", "Title", "Rating", "Release Date", "Watched?"};
		this.watchedColumns = a;
		String[] b = {"Service ID", "Streaming Service Name", "Subscribed?"};
		this.subColumns = b;
		table1 = new JTable();
		table2 = new JTable();

	}
	public void refresh() {
		JPanel panel;
//		panel.dispose();
		panel = createTabbedPane();
		panel.repaint();
//		table1.invalidate();
//		table1.validate();
//		table1.repaint();
//		MainFrame.frame.invalidate();
//		MainFrame.frame.validate();
//		MainFrame.frame.repaint();
//		table2.repaint();
	}
	public JPanel createTabbedPane() {
		System.out.println("creating tabbed pane");
		JTabbedPane tabPane = new JTabbedPane();
		
		System.out.println("creating sub panel");
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout()); 
		
		System.out.println("creating watched panel");
		JPanel watchedPanel = new JPanel();
		watchedPanel.setLayout(new BorderLayout());
		
		tabPane.add("Watched", watchedPanel);
		tabPane.add("Subscribed", subPanel);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		
		System.out.println("adding tabs");
		userPanel.add(tabPane, BorderLayout.NORTH);
//		watchedPanel.add(createWatchedPane(), BorderLayout.CENTER);
		
		System.out.println("adding subbed");
		subPanel.add(subbedTable());
		
		System.out.println("adding watched");
		watchedPanel.add(watchedTable(), BorderLayout.SOUTH);
//		System.out.println("print table");
//		subPanel.add(createSubPane(), BorderLayout.CENTER);
		

//		MainFrame.frame.repaint();
		
		return userPanel;
	}
	
//	public JPanel createWatchedPane() {
//		JPanel watchPanel = new JPanel();
//		watchPanel.setLayout(new FlowLayout());
////		newMediaName = new JTextField();
////		newMediaName.setColumns(10);
////		newMediaName.setPreferredSize(new Dimension(100, 30));
////		watchPanel.add(new JLabel("Media Name:"));
////		watchPanel.add(newMediaName);
////		userPanel.add(newMediaName, BorderLayout.EAST);
//		JButton addWatchedButton = new JButton("Refresh");
//		addWatchedButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				refresh();
//			}
//			
//		});
//		watchPanel.add(addWatchedButton);
//		return watchPanel;
//	}
	public JPanel watchedTable() {
		JPanel watchPanel = new JPanel();
		this.watchedList = watched.getMediaInfo();		
		
//		JTable table1 = new JTable();
		DefaultTableModel model;
		model = new DefaultTableModel(watchedList, watchedColumns) {
			public Class getColumnClass(int column) {
				if (column == watchedColumns.length-1) {
					return Boolean.class;
				}
				else {
					return String.class;
				}
			}
		};
		table1.setModel(model);
		table1.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (e.getColumn() == table1.getColumnCount() - 1) {
//					table.getClientTProperty("ID");
//					System.out.println(e.getColumn());
//					System.out.println(table.getClientProperty("ID"));
					System.out.println(table1.getValueAt(e.getLastRow(), 0));
					System.out.println(table1.getValueAt(e.getLastRow(), e.getColumn()));
					boolean val = (boolean) table1.getValueAt(e.getLastRow(), e.getColumn());
					int MID = (int) table1.getValueAt(e.getLastRow(), 0);
					
					if (val) {
						try {
							CallableStatement cs = con.prepareCall("{? = call addWatched(?, ?)}");
							cs.setString(2, Main.currentUser);
							cs.setLong(3, MID);
							cs.registerOutParameter(1, Types.INTEGER);
							
							cs.execute();
							int returnValue = cs.getInt(1);
							System.out.println("called addWatched - " + Main.currentUser + ": " + returnValue);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
					else {
						try {
							CallableStatement cs = con.prepareCall("{? = call delWatched(?, ?)}");
							cs.setString(2, Main.currentUser);
							cs.setLong(3, MID);
							cs.registerOutParameter(1, Types.INTEGER);
							
							cs.execute();
							int returnValue = cs.getInt(1);
							System.out.println("called delWatched - " + Main.currentUser + ": " + returnValue);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}

				}
			}
		});
		JScrollPane table1Pane = new JScrollPane(table1);
		watchPanel.add(table1Pane);
		return watchPanel;
	}
	
//	public JPanel createSubPane() {
//		JPanel subPanel = new JPanel();
//		subPanel.setLayout(new FlowLayout());
////		newSubName = new JTextField();
////		newSubName.setColumns(10);
////		subPanel.add(new JLabel("Subscription Name:"));
////		subPanel.add(newMediaName, BorderLayout.NORTH);
//		
////		JButton addSubButton = new JButton("Add Service Subscription");
////		subPanel.add(addSubButton);
//		
//
////		this.subList = subbed.getMediaInfo();		
////
////		JTable table1 = new JTable();
////		table1.setModel(new DefaultTableModel(subList, subColumns));
////		
////		JScrollPane table1Pane = new JScrollPane(table1);
////		subPanel.add(table1Pane, BorderLayout.SOUTH);
//		
//		return subPanel;
//	}
	
	@SuppressWarnings("serial")
	public JPanel subbedTable() {
		JPanel subPanel = new JPanel();
		this.subList = subbed.getMediaInfo();		
//		Object newSubList[][] = subList[subList.length][subList[].length];
//		JTable table1 = new JTable();
		DefaultTableModel model;
		model = new DefaultTableModel(subList, subColumns) {
			@Override 
			public Class getColumnClass(int column) {
				if (column == subColumns.length-1) {
					return Boolean.class;
				}
				else {
					return String.class;
				}
			}
		};
//		table1.revalidate();
		table2.setModel(model);
		table2.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (e.getColumn() == table2.getColumnCount() - 1) {
//					table.getClientTProperty("ID");
//					System.out.println(e.getColumn());
//					System.out.println(table.getClientProperty("ID"));
					System.out.println(table2.getValueAt(e.getLastRow(), 0));
					System.out.println(table2.getValueAt(e.getLastRow(), e.getColumn()));
					boolean val = (boolean) table2.getValueAt(e.getLastRow(), e.getColumn());
					int SID = (int) table2.getValueAt(e.getLastRow(), 0);
					
					if (val) {
						try {
							CallableStatement cs = con.prepareCall("{? = call addSubscribed(?, ?)}");
							cs.setString(2, Main.currentUser);
							cs.setLong(3, SID);
							cs.registerOutParameter(1, Types.INTEGER);
							
							cs.execute();
							int returnValue = cs.getInt(1);
							System.out.println("called addSub - " + Main.currentUser + ": " + returnValue);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
					else {
						try {
							CallableStatement cs = con.prepareCall("{? = call delSubscribed(?, ?)}");
							cs.setString(2, Main.currentUser);
							cs.setLong(3, SID);
							cs.registerOutParameter(1, Types.INTEGER);
							
							cs.execute();
							int returnValue = cs.getInt(1);
							System.out.println("called delSub - " + Main.currentUser + ": " + returnValue);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}

				}
			}
		});
		JScrollPane table1Pane = new JScrollPane(table2);
		subPanel.add(table1Pane);
		return subPanel;
	}
	
}
