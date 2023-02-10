package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Shows extends Medias {
	public Shows(Connection con) {
		super(con);
		String[] a = {"Title", "Rating", "Release Date", "Season Count", "Episode Count", "Latest Episode"};
		this.columnNames = a;
	}

	@Override
	protected String[][] getMediaInfo() {
		String showQuery = "SELECT * FROM Media m JOIN Show s ON m.ID=s.MediaID ORDER BY m.Title ASC";
		ArrayList<String[]> showTitles = new ArrayList<>();

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(showQuery);
			while (rs.next()) {
				String[] showData = new String[this.columnNames.length];
				showData[0] = rs.getString("Title");
				String rating = rs.getString("Rating");
				showData[1] = rating.substring(0, 3);
				showData[2] = rs.getString("ReleaseDate");
				showData[3] = rs.getString("NumSeasons");
				showData[4] = rs.getString("NumEpisodes");
				showData[5] = rs.getString("LastEpDate");
				showTitles.add(showData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertArrayListToArray(showTitles);
	}

}
