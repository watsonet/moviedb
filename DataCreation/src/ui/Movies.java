package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

public class Movies extends Medias {
	public Movies(Connection con) {
		super(con);
		String[] a = {"Title", "Rating", "Release Date", "Runtime", "Service",  "Watched?"};
		this.columnNames = a;
	}

	@Override
	public Object[][] getMediaInfo() {
		String movieQuery = "SELECT md.Title, md.Rating, md.ReleaseDate, m.Runtime, s.SName, m.MediaID\n"
		
//		String movieQuery = "SELECT md.Title, md.Rating, md.ReleaseDate, m.Runtime, a.Name, s.SName\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Movie m\n"
				+ "	ON m.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON m.MediaID = md.ID";
//				+ "	JOIN ActedIn ai\n"
//				+ "	ON m.MediaID = ai.ActorID"
//				+ "	JOIN Actor a\n"
//				+ "	ON ai.ActorID = a.ID";
		ArrayList<Object[]> movieTitles = new ArrayList<>();
		Object[] movieData;
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(movieQuery); 
			while (rs.next()) {
				
				movieData = new Object[this.columnNames.length];
				movieData[0] = rs.getString("Title");
				String rating = rs.getString("Rating");
				movieData[1] = rating.substring(0, 3);
				movieData[2] = rs.getString("ReleaseDate");
				movieData[3] = rs.getString("Runtime");
				movieData[4] = rs.getString("SName");
//				movieData[4] = rs.getInt("MediaID");
				movieData[5] = false;
				
				String exists = "select * from watched w where '" 
						+ Main.currentUser 
						+ "'=w.Username and w.MediaID=" 
						+ rs.getInt("MediaID"); //\n return -1";
//				ArrayList<Object[]> watchedExists = new ArrayList<>();
				try {
					Statement s2 = con.createStatement();
//					ResultSet rs2 = s2.executeQuery(exists);
//					System.out.println("working");
//					while(rs2.next()) {
//						movieData[5] = true;
//					}
//					if (rs2.getFetchSize()==0) {
//						movieData[5] = false;
//					}
//					else {
//						movieData[5] = true;
//					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
//				movieData[5] = rs.getInt("MediaID");
//				((JComponent) movieData[5]).putClientProperty("ID",rs.getInt("ID"));
				movieTitles.add(movieData);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return convertArrayListToArray(movieTitles);
	}

	//use this to replace getMediaInfo() in case you dont like it
	@Override
	protected Object[][] getMediaHostedInfo() {
		String movieQuery = "SELECT md.Title, md.Rating, md.ReleaseDate, m.Runtime, s.sName\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Movie m\n"
				+ "	ON m.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON m.MediaID = md.ID";
		ArrayList<Object[]> movieTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(movieQuery);
			while (rs.next()) {
				String[] movieData = new String[this.columnNames.length];
				movieData[0] = rs.getString("Title");
				String rating = rs.getString("Rating");
				movieData[1] = rating.substring(0, 3);
				movieData[2] = rs.getString("ReleaseDate");
				movieData[3] = rs.getString("Runtime");
				movieData[4] = rs.getString("SName");
				movieTitles.add(movieData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(movieTitles);
	}

	@Override
	protected Object[][] getMediaActedInfo() {
		String[] a = {"Actor", "Title", "Rating", "Release Date", "Runtime", "Watched?"};
		this.columnNames = a;
		
		String movieQuery = "SELECT a.Name , md.Title, md.Rating, md.ReleaseDate, m.Runtime\n"
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
				Object[] movieData = new String[this.columnNames.length];
				movieData[0] = rs.getString("Name");
				movieData[1] = rs.getString("Title");
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
