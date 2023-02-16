package ui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public abstract class UserTables {
	protected static Connection con;
	protected Object[][] mediaList = new Object[0][0];
	protected Object[][] sortedMediaList = new Object[0][0];
	protected Object[] columnNames;
	static JTable table;

	public UserTables(Connection con) {
		Medias.con = con;
		table = new JTable();
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
	 * 1) Its useful for testing 2) Can be used as a fallback 3) Potentially use it
	 * as a basis for getMediaHostedInfo and future methods like that
	 */
	protected abstract Object[][] getMediaInfo();

	protected abstract Object[][] getMediaHostedInfo();

	protected abstract Object[][] getMediaActedInfo();

	private JPanel createPanel(Type type) {
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Select search terms");

		if (type == Type.ACTED) {
			this.mediaList = getMediaActedInfo();
		} else if (type == Type.HOSTED) {
			this.mediaList = (String[][]) getMediaHostedInfo();
		} else {
			// ideally don't use this, but its there as a fallback and/or for testing
			this.mediaList = getMediaInfo();
//			System.out.println("work");
		}

		JTable table = new JTable();
		DefaultTableModel model;
//		table.setModel(new DefaultTableModel(mediaList, columnNames));
		model = new DefaultTableModel(mediaList, columnNames) {
			@Override
			public Class getColumnClass(int column) {
//				for (int i = 0; i < columnNames.length; i++) {
//					System.out.println(columnNames[i]);
//					
//				}
				if (column == columnNames.length - 1) {
					return Boolean.class;
				} else {
					return String.class;
				}
			}
		};
//		mediaList[columnNames.length-1].DefaultCellEditor();

		table.setModel(model);
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (e.getColumn() == table.getColumnCount() - 1) {
//					table.getClientTProperty("ID");
//					System.out.println(e.getColumn());
//					System.out.println(table.getClientProperty("ID"));
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
//						UserData user = new UserData(con, Main.currentUser);
//						UserData.refresh();
//						user.createTabbedPane();
//						MainFrame frame = new MainFrame(con.);
//						SwingUtilities.updateComponentTreeUI(MainFrame.frame);
//						MainFrame.frame.invalidate();
//						MainFrame.frame.validate();
//						MainFrame.frame.repaint();
						model.fireTableDataChanged();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				}
			}
		});

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

	protected Object[][] convertArrayListToArray(ArrayList<Object[]> l) {
		Object[][] a = l.toArray(new Object[0][0]);
		return a;
	}

	protected void sortMediaList(char first, JTable table) {
		if (first == '\0') {
			table.setModel(new DefaultTableModel(mediaList, columnNames) {
//				@Override 
//				public Class getColumnClass(int column) {
//					if (column == columnNames.length-1) {
//						return Boolean.class;
//					}
//					else {
//						return String.class;
//					}
//				}
			});
			return;
		}

		ArrayList<Object[]> sorted = new ArrayList<>();
		for (Object[] s : mediaList) {
			// s[0] will always be Title
			if (((String) s[0]).toLowerCase().charAt(0) == first) {
				sorted.add(s);
			}
		}

		this.sortedMediaList = convertArrayListToArray(sorted);

		table.setModel(new DefaultTableModel(sortedMediaList, columnNames) {
//			@Override 
//			public Class getColumnClass(int column) {
//				if (column == columnNames.length-1) {
//					return Boolean.class;
//				}
//				else {
//					return String.class;
//				}
//			}
		});

	}
}
