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
		String subbedQuery = "SELECT ss.ID, ss.SName FROM StreamingService ss";
		ArrayList<Object[]> serviceTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(subbedQuery);
			while (rs.next()) {
				Object[] serviceData = new Object[this.columnNames.length];
				serviceData[0] = rs.getInt("ID");
				serviceData[1] = rs.getString("SName");
				serviceData[2] = false;
				String exists = "select * from subscribed s where "  
						+ "s.Username ='" + Main.currentUser + "' and s.ServiceID=" 
						+ rs.getInt("ID"); //\n return -1";
//				ArrayList<Object[]> watchedExists = new ArrayList<>();
				try {
					Statement s2 = con.createStatement();
					ResultSet rs2 = s2.executeQuery(exists);
//					System.out.println("working");
					while(rs2.next()) {
						serviceData[2] = true;
					}
//					if (rs2.getFetchSize()==0) {
//						movieData[5] = false;
//					}
//					else {
//						movieData[5] = true;
//					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
