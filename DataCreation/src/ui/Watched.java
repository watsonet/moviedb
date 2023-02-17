package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Watched extends Medias {
	
	public Watched(Connection con) {
		super(con);
		String[] a = {"ID", "Title", "Rating", "Release Date", "Watched?"};
		this.columnNames = a;
	}

	@Override
	public Object[][] getMediaHostedInfo() {
		String watchedQuery = "SELECT * FROM Media m LEFT JOIN Watched w ON m.ID=w.MediaID and '" + Main.currentUser + "'=w.Username ORDER BY m.Title ASC";
		
		ArrayList<Object[]> watchedTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(watchedQuery);
			while (rs.next()) {
				Object[] watchedData = new Object[this.columnNames.length];
				watchedData[0] = rs.getInt("ID");
				watchedData[1] = rs.getString("Title");
				String rating = rs.getString("Rating");
				watchedData[2] = rating.substring(0, 3);
				watchedData[3] = rs.getString("ReleaseDate");
				watchedData[4] = rs.getString("MediaID") == null? false : true;

				watchedTitles.add(watchedData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(watchedTitles);
	}

	@Override
	protected Object[][] getMediaActedInfo() {
		String[] a = {"Actor", "Title", "Rating", "Release Date", "Season Count", "Episode Count", "Latest Episode"};
		this.columnNames = a;
		
		String showQuery = "SELECT a.Name , md.Title, md.Rating, md.ReleaseDate, sh.NumSeasons, sh.NumEpisodes, sh.NumEpisodes, sh.LastEpDate\n"
				+ "	FROM Actor a\n"
				+ "	JOIN ActedIn ai \n"
				+ "	ON a.ID = ai.ActorID \n"
				+ "	JOIN Show sh\n"
				+ "	ON sh.MediaID = ai.MediaID\n"
				+ "	JOIN Media md \n"
				+ "	ON sh.MediaID = md.ID \n"
				+ "	ORDER BY a.Name ";
		ArrayList<Object[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				Object[] showData = new String[this.columnNames.length];
				showData[0] = rs.getString("Name");
				showData[1] = rs.getString("Title");
				String rating = rs.getString("Rating");
				showData[2] = rating.substring(0, 3);
				showData[3] = rs.getString("ReleaseDate");
				showData[4] = rs.getString("NumSeasons");
				showData[5] = rs.getString("NumEpisodes");
				showData[6] = rs.getString("LastEpDate");
				showTitles.add(showData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(showTitles);
	}

}
