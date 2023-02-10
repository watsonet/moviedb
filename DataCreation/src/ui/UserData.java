package ui;

import java.sql.Connection;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserData {
	private Connection con;
	private String username;
	
	public UserData(Connection con, String username) {
		this.con = con;
		this.username = username;
	}
	
	public JPanel createPane() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		
		JLabel currentUser = new JLabel("Your are currently logged in as " + this.username);
		
		
		userPanel.add(currentUser);
		
		return userPanel;
	}
}
