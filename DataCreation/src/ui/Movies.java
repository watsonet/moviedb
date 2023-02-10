package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Movies extends Medias {
	public Movies(Connection con) {
		super(con);
		String[] a = {"Title", "Rating", "Release Date", "Runtime"};
		this.columnNames = a;
	}

	@Override
	public String[][] getMediaInfo() {
		String movieQuery = "SELECT * FROM Media m JOIN Movie mo ON m.ID=mo.MediaID ORDER BY m.Title ASC";
		ArrayList<String[]> movieTitles = new ArrayList<>();

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
				movieTitles.add(movieData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(movieTitles);
	}

	@Override
	protected String[][] getMediaHostedInfo() {
		String[] a = {"Service", "Title", "Rating", "Release Date", "Runtime"};
		this.columnNames = a;
		
		String showQuery = "SELECT s.SName, md.Title, md.Rating, md.ReleaseDate, m.Runtime\n"
				+ "	FROM StreamingService s\n"
				+ "	JOIN Hosts h\n"
				+ "	ON s.ID = h.ServiceID\n"
				+ "	JOIN Movie m\n"
				+ "	ON m.MediaID = h.MediaID\n"
				+ "	JOIN Media md\n"
				+ "	ON m.MediaID = md.ID";
		ArrayList<String[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				String[] showData = new String[this.columnNames.length];
				showData[0] = rs.getString("SName");
				showData[1] = rs.getString("Title");
				String rating = rs.getString("Rating");
				showData[2] = rating.substring(0, 3);
				showData[3] = rs.getString("ReleaseDate");
				showData[3] = rs.getString("Runtime");
				showTitles.add(showData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(showTitles);
	}

	@Override
	protected String[][] getMediaActedInfo() {
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
		ArrayList<String[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				String[] showData = new String[this.columnNames.length];
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
