package ui;

import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class MainFrame {
	private DatabaseConnectionService dbcs;
	static JFrame frame;
	public MainFrame(DatabaseConnectionService dbcs) {
		this.dbcs = dbcs;
		frame = new JFrame();
	}

//	public static void update() {
//		frame.repaint();
//	}
	public void createFrame() {
		Connection con = this.dbcs.getConnection();

//		JFrame frame = new JFrame();
		frame.setTitle("User: " + Main.currentUser);
		JTabbedPane tabbedPane = new JTabbedPane();

		// Movies
		Movies actedMovies = new Movies(con);
		Movies hostedMovies = new Movies(con);
		
		JTabbedPane moviePanel = new JTabbedPane();
		JPanel movieActedPanel = actedMovies.createPane(Type.ACTED);
		JPanel movieHostedPanel = hostedMovies.createPane(Type.HOSTED);
		
		moviePanel.add("Actors/Actresses", movieActedPanel);
		moviePanel.add("Services", movieHostedPanel);

		// TV Shows
		Shows actedShows = new Shows(con);
		Shows hostedShows = new Shows(con);
		JTabbedPane showPanel = new JTabbedPane();
		JPanel showActedPanel = actedShows.createPane(Type.ACTED);
		JPanel showHostedPanel = hostedShows.createPane(Type.HOSTED);
		
		showPanel.add("Actors/Actresses", showActedPanel);
		showPanel.add("Services", showHostedPanel);

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
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
