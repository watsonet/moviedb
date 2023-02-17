package ui;

import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public abstract class Medias {
	protected static Connection con;
	private Object[][] mediaList = new Object[0][0];
	private Object[][] sortedMediaList = new Object[0][0];
	protected Object[] columnNames;
	protected JTable table = new JTable();
	protected CategoryToggle[] catToggles = new CategoryToggle[0];
	protected DefaultTableModel model;

	public Medias(Connection con) {
		Medias.con = con;
	}

	public JPanel createPane(Type type) {
		JPanel mediaPane = new JPanel();

		JPanel mediaPanel = createPanel(type);

		mediaPane.add(mediaPanel);

		return mediaPane;
	}

	private JPanel sortingPanel(JTextField searchField) {
		JPanel sortingPanel = new JPanel();

		for (int i = 0; i < this.columnNames.length; i++) {
			JButton b = new JButton((String) this.columnNames[i]);

			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String cat = b.getText();
					System.out.println("Pressed button " + cat);

					String query = searchField.getText();

					if (query.isBlank() || query == null) {
						return;
					}

					sortMediaList(query, cat, table);
				}
			});

			sortingPanel.add(b);
		}

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearSearch();
				searchField.setText("");
			}
		});

		sortingPanel.add(clearButton);

		return sortingPanel;
	}

	/**
	 * Abstract class that should implement a way to get information on a media's
	 * information and streaming platforms
	 * 
	 * @return Object[][]
	 */
	protected abstract Object[][] getMediaHostedInfo();

	/**
	 * Abstract class that should implement a way to get information on a media's
	 * information and cast
	 * 
	 * @return Object[][]
	 */
	protected abstract Object[][] getMediaActedInfo();

	private JPanel createPanel(Type type) {
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Select search terms");

		if (type == Type.HOSTED) {
			this.mediaList = getMediaHostedInfo();
		} else if (type == Type.ACTED) {
			this.mediaList = getMediaActedInfo();
		}

		this.model = new DefaultTableModel(mediaList, columnNames);

		this.model.setDataVector(mediaList, columnNames);

		this.table.setModel(model);

		initTable();

		JScrollPane tablePane = new JScrollPane(this.table);

		JPanel textFieldPanel = new JPanel();
		JTextField searchField = createTextBoxes(textFieldPanel, table, type);

		searchPanel.add(text);
		searchPanel.add(sortingPanel(searchField));
		searchPanel.add(textFieldPanel);

		p1.add(searchPanel, BorderLayout.NORTH);
		p1.add(tablePane, BorderLayout.SOUTH);

		return p1;
	}

	private JTextField createTextBoxes(JPanel panel, JTable table, Type type) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		String tabCategory = "";

		if (type == Type.ACTED) {
			tabCategory = "cast members";
		} else if (type == Type.HOSTED) {
			tabCategory = "services";
		} else {
			tabCategory = "help";
		}
		JLabel charStartLabel = new JLabel("Search for " + tabCategory + " or titles: ");
		JTextField charField = new JTextField(1); // He hates Mondays
		panel.add(charStartLabel);
		panel.add(charField);

		return charField;

	}

	/**
	 * Converts an ArrayList<Object[]> to an Object[][]
	 * 
	 * @param list
	 * @return Object[][]
	 */
	protected Object[][] convertArrayListToArray(ArrayList<Object[]> list) {
		Object[][] a = list.toArray(new Object[0][0]);
		return a;
	}

	private void sortMediaList(String queryUnformatted, String type, JTable table) {
		if (this.sortedMediaList.equals(new Object[0][0])) {
			this.sortedMediaList = this.mediaList;
		}

		if (queryUnformatted.isBlank() || queryUnformatted == null) {
			clearSearch();
			return;
		}

		String query = queryUnformatted.toLowerCase();
		ArrayList<Object[]> sorted = new ArrayList<>();

		/*
		 * The only things using strings for sorting are Title, Actor, and Service
		 * 
		 * Title is always the first, and when present, Actor/Service is always second
		 * 
		 * Actor and Service are never in the same table
		 */
		switch (type) {

		case "Title":
			for (Object[] s : mediaList) {
				if (((String) s[0]).toLowerCase().contains(query)) {
					sorted.add(s);
					System.out.println("Added " + ((String) s[0]));
				}
			}
			System.out.println("Sorted by " + type);
			break;
		case "Actor":
			for (Object[] s : mediaList) {
				if (((String) s[1]).toLowerCase().contains(query)) {
					sorted.add(s);
					System.out.println("Added " + ((String) s[1]));
				}
			}
			System.out.println("Sorted by " + type);
			break;
		case "Service":
			for (Object[] s : mediaList) {
				if (((String) s[1]).toLowerCase().contains(query)) {
					sorted.add(s);
					System.out.println("Added " + ((String) s[1]));
				}
			}
			System.out.println("Sorted by " + type);
			break;
		default:
			for (int i = 0; i < this.mediaList.length; i++) {
				// Use toggles to sort by ascending or descending

				// Only one at a time, so you can have date be asc, but everything else has to
				// be none
			}
			System.out.println("Sorted by other " + type);
		}

		this.sortedMediaList = convertArrayListToArray(sorted);

		this.model.setDataVector(this.sortedMediaList, columnNames);

		table.setModel(this.model);

	}

	private void clearSearch() {
		this.sortedMediaList = new Object[0][0];
		for (int i = 0; i < this.catToggles.length; i++) {
			this.catToggles[i] = CategoryToggle.NONE;
		}

		this.model.setDataVector(this.mediaList, columnNames);

		table.setModel(this.model);
	}

	private void initTable() {
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == table.getColumnCount() - 1) {
					System.out.println(table.getValueAt(e.getLastRow(), 0));
					int MID = (int) table.getValueAt(e.getLastRow(), 0);
					try {
						CallableStatement cs = con.prepareCall("{? = call addWatched(?, ?)}");
						cs.setString(2, Main.currentUser);
						cs.setLong(3, MID);
						cs.registerOutParameter(1, Types.INTEGER);

						cs.execute();
						int returnValue = cs.getInt(1);
						System.out.println(Main.currentUser + ": " + returnValue);
						model.fireTableDataChanged();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
	}
}
