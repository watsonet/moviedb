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
import javax.swing.JTextField;

public class UserData {
	private Connection con;
	private String username;
//	private JMenu menu;
	JTextField newMediaName = null;
	JTextField newSubName = null;
//	JTextField newSodaManf = null;
	

	public UserData(Connection con, String username) {
		this.con = con;
		this.username = Main.currentUser;
	}

	public JPanel createPane() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());

//		JLabel currentUser = new JLabel("You are currently logged in as " + this.username);
//		userPanel.add(currentUser);

		newMediaName = new JTextField();
		newMediaName.setColumns(10);
//		newMediaName.setPreferredSize(new Dimension(100, 30));
		userPanel.add(new JLabel("Media Name:"));
		userPanel.add(newMediaName, BorderLayout.EAST);
		JButton addWatchedButton = new JButton("Add Media Watched");
		userPanel.add(addWatchedButton);
		
		newSubName = new JTextField();
		newSubName.setColumns(10);

//		newSubName.setPreferredSize(new Dimension(100, 30));
		userPanel.add(new JLabel("Subscription Name:"));
		userPanel.add(newSubName , BorderLayout.WEST);
		JButton addSubButton = new JButton("Add Service Subscribed to");
		userPanel.add(addSubButton);
//		setLayout(new BorderLayout());
//		JPanel watchedPanel = watchedPanel();
//		add(createWatchedPanel, "Center");
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
