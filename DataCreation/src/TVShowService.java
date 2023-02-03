import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TVShowService {
	//check the filepath
	private String TVFilepath = "../DataCreation/outputTVShows.txt";
	private DatabaseConnectionService dbService = null;
	
	public TVShowService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addTVShows() {
		FileID fileReader = new FileID(TVFilepath);
		ArrayList<TVShow> TVshows = fileReader.getTVShows();
		for(TVShow show : TVshows) {
			try{
				CallableStatement cs = dbService.getConnection().prepareCall("{? = call addMedia(?, ?, ?, ?, ?)}");
				cs.setLong(2, show.ID);
				cs.setString(3, show.title);
				cs.setFloat(4, (float) show.rating);
				cs.setString(5,  show.releaseDate);
				cs.setInt(6, 0);
				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " already exists in Show table");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID cannot be null");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ID cannot be negative");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "Title cannot be null");
				}
				if(returnValue == 5) {
					JOptionPane.showMessageDialog(null, "Rating cannot be null");
				}
				if(returnValue == 6) {
					JOptionPane.showMessageDialog(null, "Release Date cannot be null");
				}
				if(returnValue == 7) {
					JOptionPane.showMessageDialog(null, "Invalid value for isAdult");
				}
				if(returnValue == 8) {
					JOptionPane.showMessageDialog(null, "Rating must be between 0 and 10");
				}
				
				
				cs = dbService.getConnection().prepareCall("{? = call addShow(?, ?, ?, ?)}");
				cs.setLong(2, show.ID);
				cs.setLong(3,  (long) show.numSeasons);
				cs.setLong(4,  (long) show.numEpisodes);
				cs.setString(5, show.lastEpDate);	//unsure if correct data type


				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID can not be null");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " does not exist in Media table");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " already exists in Show table");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "Runtime can not be null");
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
