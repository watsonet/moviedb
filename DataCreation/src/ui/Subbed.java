package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Subbed extends Medias {
	
	public Subbed(Connection con) {
		super(con);
		String[] a = {"Service ID", "Streaming Service Name", "Subscribe?"};
		this.columnNames = a;
	}

	@Override
	public Object[][] getMediaInfo() {
		String subbedQuery = "SELECT ss.ID, ss.SName, s.ServiceID FROM StreamingService ss LEFT JOIN subscribed s on s.Username ='" + Main.currentUser + "'";
		ArrayList<Object[]> serviceTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(subbedQuery);
			while (rs.next()) {
				Object[] serviceData = new Object[this.columnNames.length];
				serviceData[0] = rs.getInt("ID");
				serviceData[1] = rs.getString("SName");
				serviceData[2] = rs.getString("ServiceID") == null? false : true;

				serviceTitles.add(serviceData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(serviceTitles);
	}

	@Override
	protected Object[][] getMediaHostedInfo() {
		String subbedQuery = "SELECT ss.SName FROM StreamingService ss JOIN Subscribed s ON ss.ID=s.ServiceID WHERE '" + Main.currentUser + "'=s.Username ORDER BY ss.SName ASC";
		ArrayList<Object[]> serviceTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(subbedQuery);
			while (rs.next()) {
				Object[] serviceData = new String[this.columnNames.length];
				serviceData[0] = rs.getString("SName");

				serviceTitles.add(serviceData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(serviceTitles);
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
