package ui;

import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class MainFrame {
	private DatabaseConnectionService dbcs;
	
	public MainFrame(DatabaseConnectionService dbcs) {
		this.dbcs = dbcs;
	}

	public void createFrame() {
		Connection con = this.dbcs.getConnection();

		JFrame frame = new JFrame();
		frame.setTitle("User: " + Main.currentUser);
		JTabbedPane tabbedPane = new JTabbedPane();

		// Movies
		Movies movies = new Movies(con);
		JTabbedPane moviePanel = movies.createPane();

		// TV Shows
		Shows shows = new Shows(con);
		JTabbedPane showPanel = shows.createPane();

		// User things
		UserData userData = new UserData(con, dbcs.getUsername());
		JPanel userPanel = userData.createTabbedPane();
//		JPanel profileSearch = userData.createTabbedPane();

		// Admin page (only shown if you have admin privileges to the DB)

		tabbedPane.add("Movie", moviePanel);
		tabbedPane.add("TV Show", showPanel);
		tabbedPane.add("Profile", userPanel);

		frame.add(tabbedPane);
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
