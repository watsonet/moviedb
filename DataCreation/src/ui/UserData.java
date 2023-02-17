package ui;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	private byte[] salt;
	private String hash;

	private boolean oldPass = false;
	private boolean newPass = false;

//	private char[] newPassword = new char[0];
	private char[] newPassword = new char[0];


	public UserData(Connection con, String username) {
		this.con = con;
		this.watched = new Watched(con);
		this.subbed = new Subbed(con);
		this.username = Main.currentUser;
		String[] a = { "ID", "Title", "Rating", "Release Date", "Watched?" };
		this.watchedColumns = a;
		String[] b = { "Service ID", "Streaming Service Name", "Subscribed?" };
		this.subColumns = b;
		table1 = new JTable();
		table2 = new JTable();
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

		JPanel profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

		tabPane.add("Watched", watchedPanel);
		tabPane.add("Subscribed", subPanel);
		tabPane.add("Profile", profilePanel);

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());

		System.out.println("adding tabs");
		userPanel.add(tabPane, BorderLayout.NORTH);

		System.out.println("adding subbed");
		subPanel.add(subbedTable());

		System.out.println("adding watched");
		watchedPanel.add(watchedTable());

		profile(profilePanel);

		return userPanel;
	}

	public JPanel watchedTable() {
		JPanel watchPanel = new JPanel();
		this.watchedList = watched.getMediaHostedInfo();

//		JTable table1 = new JTable();
		DefaultTableModel model;
		model = new DefaultTableModel(watchedList, watchedColumns) {
			public Class getColumnClass(int column) {
				if (column == watchedColumns.length - 1) {
					return Boolean.class;
				} else {
					return String.class;
				}
			}
		};
		table1.setModel(model);
		table1.removeColumn(table1.getColumn(model.getColumnName(0)));
		table1.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (e.getColumn() == table1.getColumnCount()) {
					boolean val = (boolean) table1.getValueAt(e.getLastRow(), e.getColumn() - 1);
					int MID = (int) table1.getModel().getValueAt(e.getLastRow(), 0);
					System.out.println(MID);
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
					} else {
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

	@SuppressWarnings("serial")
	public JPanel subbedTable() {
		JPanel subPanel = new JPanel();
		this.subList = subbed.getMediaHostedInfo();
		DefaultTableModel model;
		model = new DefaultTableModel(subList, subColumns) {
			@Override
			public Class getColumnClass(int column) {
				if (column == subColumns.length - 1) {
					return Boolean.class;
				} else {
					return String.class;
				}
			}
		};
		table2.setModel(model);
		table2.removeColumn(table2.getColumn(model.getColumnName(0)));
		table2.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (e.getColumn() == table2.getColumnCount()) {
					boolean val = (boolean) table2.getValueAt(e.getLastRow(), e.getColumn() - 1);
					int SID = (int) table2.getModel().getValueAt(e.getLastRow(), 0);
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
					} else {
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

	public JPanel profile(JPanel profilePanel) {
		JLabel profileLabel = new JLabel("Currently logged in as " + this.username);
		JLabel statusLabel = new JLabel("Please enter old and new passwords");

		JLabel oldPassLabel = new JLabel("Old password:");
		JPasswordField validateOldPassField = new JPasswordField();
		validateOldPassField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CallableStatement cs = con.prepareCall("{call checkUser(?)}");
					cs.setString(1, username);
					ResultSet rs = cs.executeQuery();
					int PWSalt = rs.findColumn("PasswordSalt");
					int PWHash = rs.findColumn("Password");

					while (rs.next()) {
						hash = rs.getString(PWHash);
						String saltStr = rs.getString(PWSalt);
						salt = dec.decode(saltStr);

						String pass = new String(validateOldPassField.getPassword());

						if (LoginFrame.hashPassword(salt, pass).compareTo(hash) != 0) {
							statusLabel.setText("Old password is not correct, please try again");
							oldPass = false;
						} else {
							oldPass = true;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		JLabel newPassLabel = new JLabel("New password:");
		JPasswordField newPasswordField = new JPasswordField();

		JLabel validateNewPassLabel = new JLabel("New password again:");
		JPasswordField validateNewPasswordField = new JPasswordField();

		newPasswordField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newPasswd = new String(newPasswordField.getPassword());
				String newPasswd2 = new String(validateNewPasswordField.getPassword());
				
				if (!newPasswd.equals(newPasswd2)) {
					statusLabel.setText("New passwords do not match, please try again");
					newPass = false;
				} else {
					newPass = true;
					statusLabel.setText("Passwords match!");
					newPassword = newPasswordField.getPassword();
				}
			}
		});

		validateNewPasswordField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newPasswd = new String(newPasswordField.getPassword());
				String newPasswd2 = new String(validateNewPasswordField.getPassword());
				
				if (!newPasswd.equals(newPasswd2)) {
					statusLabel.setText("New passwords do not match, please try again");
					newPass = false;
				} else {
					newPass = true;
					statusLabel.setText("Passwords match!");
					newPassword = newPasswordField.getPassword();
				}
			}
		});
		
		JButton changeButton = new JButton("Confirm");
		changeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (newPass && oldPass) {
					String strNewPass = new String(newPassword);
					String newHash = LoginFrame.hashPassword(salt, strNewPass);
					System.out.println(strNewPass);
					try {
						CallableStatement cs = con.prepareCall("{? = call updUser(?, ?)}");
						cs.setString(2, username);
						cs.setString(3, newHash);
						cs.registerOutParameter(1, Types.INTEGER);
						
						cs.execute();

						if (cs.getInt(1) != 0) {
							statusLabel.setText("Something went wrong, please try again");
						} else {
							statusLabel.setText("Success!");
//							statusLabel.setText(strNewPass);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		

		profilePanel.add(profileLabel);
		profilePanel.add(statusLabel);
		profilePanel.add(oldPassLabel);
		profilePanel.add(validateOldPassField);
		profilePanel.add(newPassLabel);
		profilePanel.add(newPasswordField);
		profilePanel.add(validateNewPassLabel);
		profilePanel.add(validateNewPasswordField);
		profilePanel.add(changeButton);

		return profilePanel;
	}
}
