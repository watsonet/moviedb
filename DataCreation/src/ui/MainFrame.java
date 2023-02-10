package ui;

import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class MainFrame {
	private DatabaseConnectionService dbcs;

	public void createFrame(DatabaseConnectionService dbcs) {
		this.dbcs = dbcs;
		
		Connection con = this.dbcs.getConnection();

		JFrame frame = new JFrame();
		JTabbedPane tabbedPane = new JTabbedPane();

		Movies movies = new Movies(con);
		JPanel moviePanel = movies.createPane(tabbedPane);

		// TV Shows

		// User things

		// Admin page (only shown if you have admin privileges to the DB)

		tabbedPane.add("Movie", moviePanel);

		frame.add(tabbedPane);
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
