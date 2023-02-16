package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Shows extends Medias {
	public Shows(Connection con) {
		super(con);
	}

	@Override
	protected Object[][] getMediaInfo() {
		String[] a = {"Title", "Rating", "Release Date", "Season Count", "Episode Count", "Latest Episode", "Service"};
		this.columnNames = a;
		
		String showQuery = "SELECT md.Title, md.Rating, md.ReleaseDate, sh.NumSeasons, sh.NumEpisodes, sh.NumEpisodes, sh.LastEpDate, s.SName\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Show sh\n"
				+ "	ON sh.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON sh.MediaID = md.ID";
		ArrayList<Object[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				Object[] showData = new String[this.columnNames.length];
				showData[0] = rs.getString("Title");
				String rating = rs.getString("Rating");
				showData[1] = rating.substring(0, 3);
				showData[2] = rs.getString("ReleaseDate");
				showData[3] = rs.getString("NumSeasons");
				showData[4] = rs.getString("NumEpisodes");
				showData[5] = rs.getString("LastEpDate");
				showData[6] = rs.getString("SName");
				showTitles.add(showData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(showTitles);
	}

	@Override
	protected Object[][] getMediaHostedInfo() {
		String[] a = {"Service", "Title", "Rating", "Release Date", "Season Count", "Episode Count", "Latest Episode"};
		this.columnNames = a;
		
		String showQuery = "SELECT s.SName, md.Title, md.Rating, md.ReleaseDate, sh.NumSeasons, sh.NumEpisodes, sh.NumEpisodes, sh.LastEpDate\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Show sh\n"
				+ "	ON sh.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON sh.MediaID = md.ID";
		ArrayList<Object[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				Object[] showData = new String[this.columnNames.length];
				showData[0] = rs.getString("SName");
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
