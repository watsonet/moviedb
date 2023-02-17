package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class Movies extends Medias {
	public Movies(Connection con) {
		super(con);
	}

	@Override
	public Object[][] getMediaHostedInfo() {
		String[] cols = {"Title", "Service", "Rating", "Release Date", "Runtime"};
		this.columnNames = cols;
		
		CategoryToggle[] ct = new CategoryToggle[cols.length];
		for (int i = 0; i < cols.length; i++) {
			ct[i] = CategoryToggle.NONE;
		}
		
		String movieQuery = "SELECT m.MediaID, md.Title, s.SName, md.Rating, md.ReleaseDate, m.Runtime\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Movie m\n"
				+ "	ON m.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON m.MediaID = md.ID";
		
		ArrayList<Object[]> movieTitles = new ArrayList<>();
		Object[] movieData;
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(movieQuery); 
			while (rs.next()) {
				
				movieData = new Object[this.columnNames.length];
				movieData[0] = rs.getString("Title");
				movieData[1] = rs.getString("SName");
				String rating = rs.getString("Rating");
				movieData[2] = rating.substring(0, 3);
				movieData[3] = rs.getString("ReleaseDate");
				movieData[4] = rs.getString("Runtime");
				movieTitles.add(movieData);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return convertArrayListToArray(movieTitles);
	}

	@Override
	protected Object[][] getMediaActedInfo() {
		String[] cols = {"Title", "Actor", "Rating", "Release Date", "Runtime"};
		this.columnNames = cols;
		
		CategoryToggle[] ct = new CategoryToggle[cols.length];
		for (int i = 0; i < cols.length; i++) {
			ct[i] = CategoryToggle.NONE;
		}
		
		String movieQuery = "SELECT a.Name , md.Title, md.Rating, md.ReleaseDate, m.Runtime, m.MediaID\n"
				+ "	FROM Actor a\n"
				+ "	JOIN ActedIn ai \n"
				+ "	ON a.ID = ai.ActorID \n"
				+ "	JOIN movie m\n"
				+ "	ON m.MediaID = ai.MediaID\n"
				+ "	JOIN Media md \n"
				+ "	ON m.MediaID = md.ID \n"
				+ "	ORDER BY a.Name ";
		ArrayList<Object[]> movieTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(movieQuery);
			while (rs.next()) {
				Object[] movieData = new Object[this.columnNames.length];
				movieData[0] = rs.getString("Title");
				movieData[1] = rs.getString("Name");
				String rating = rs.getString("Rating");
				movieData[2] = rating.substring(0, 3);
				movieData[3] = rs.getString("ReleaseDate");
				movieData[4] = rs.getString("Runtime");
				movieTitles.add(movieData);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(movieTitles);
	}

}
