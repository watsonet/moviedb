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

}
