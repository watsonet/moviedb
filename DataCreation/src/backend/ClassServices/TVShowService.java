package backend.ClassServices;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import backend.DatabaseConnectionService;
import backend.Classes.TVShow;
import backend.FileTools.FileID;

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
				int media = addMedia(show);
				if(media == 1) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " already exists in Media table");
				}
				if(media == 2) {
					//JOptionPane.showMessageDialog(null, "ID cannot be null");
				}
				if(media == 3) {
					JOptionPane.showMessageDialog(null, "ID cannot be negative");
				}
				if(media == 4) {
					JOptionPane.showMessageDialog(null, "Title cannot be null");
				}
				if(media == 5) {
					JOptionPane.showMessageDialog(null, "Rating cannot be null");
				}
				if(media == 6) {
					JOptionPane.showMessageDialog(null, "Release Date cannot be null");
				}
				if(media == 7) {
					JOptionPane.showMessageDialog(null, "Invalid value for isAdult");
				}
				if(media == 8) {
					JOptionPane.showMessageDialog(null, "Rating must be between 0 and 10");
				}
				
				int tv = addTV(show);
				if(tv == 1) {
					JOptionPane.showMessageDialog(null, "ID can not be null");
				}
				if(tv == 2) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " does not exist in Media table");
				}
				if(tv == 3) {
					JOptionPane.showMessageDialog(null, "ID " + show.ID + " already exists in Show table");
				}
				if(tv == 4) {
					JOptionPane.showMessageDialog(null, "Runtime can not be null");
				}
				
				addActedIn(show);
				addHosts(show);
		}
	}
	private int addTV(TVShow show) {
		try {
			System.out.println("hi!!");
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addShow(?, ?, ?, ?)}");
			cs.setLong(2, show.ID);
			cs.setLong(3,  (long) show.numSeasons);
			cs.setLong(4,  (long) show.numEpisodes);
			cs.setString(5, show.lastEpDate);	//unsure if correct data type


			cs.registerOutParameter(1, Types.INTEGER);
			
			cs.execute();
			int returnValue = cs.getInt(1);
			cs.close();
			return returnValue;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	private int addMedia(TVShow show) {
		try{
			System.out.println("not my fault");
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addMedia(?, ?, ?, ?, ?)}");
			cs.setLong(2, show.ID);
			cs.setString(3, show.title);
			cs.setFloat(4, (float) show.rating);
			cs.setString(5,  show.releaseDate);
			cs.setInt(6, 0);
			cs.registerOutParameter(1, Types.INTEGER);
			
			cs.execute();
			int returnValue = cs.getInt(1);
			cs.close();
			return returnValue;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	private void addActedIn(TVShow show) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addActedIn(?, ?)}");
			cs.setLong(2,  show.ID);
			cs.registerOutParameter(1, Types.INTEGER);
			for(Long actID : show.actors) {
				cs.setLong(3, actID);
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ActorID can not be null");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "Show ID can not be null");
				}
				if(returnValue == 3) {
					//JOptionPane.showMessageDialog(null, "ID does not exist in Actor table");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "ID does not exist in Media table");
				}
			}
			cs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private void addHosts(TVShow show) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addHosts(?, ?)}");
			cs.setLong(2,  show.ID);
			cs.registerOutParameter(1, Types.INTEGER);
			for(Long actID : show.providers) {
				cs.setLong(3, actID);
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "Service ID can not be null");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "Show ID can not be null");
				}
				if(returnValue == 3) {
					//JOptionPane.showMessageDialog(null, "ID does not exist in Service table");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "ID does not exist in Media table");
				}
			}
			cs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}


