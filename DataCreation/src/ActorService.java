import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ActorService {
	//check the filepath
	private String TVFilepath = "../DataCreation/outputTVShows.txt";
	private DatabaseConnectionService dbService = null;
	
	public ActorService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addMovies() {
		FileID fileReader = new FileID(TVFilepath);
		ArrayList<Actor> actors = fileReader.getActor();
		for(Actor act : actors) {
			try{
				CallableStatement cs = dbService.getConnection().prepareCall("{? = call addMedia(?, ?, ?, ?, ?)}");
				cs.setLong(2, act.ID);
//				cs.setString(3, act.title);
//				cs.setFloat(4, (float) act.rating);
//				cs.setString(5,  act.releaseDate);
				cs.setInt(6, 0);
				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID" + act.ID + "already exists in Show table");
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
				
				
				cs = dbService.getConnection().prepareCall("{? = call addMovie(?, ?)}");
				cs.setLong(2, act.ID);
//				cs.setInt(3,  (int) show.runtime);
				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID can not be null");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID" + act.ID + "does not exist in Media table");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ID" + act.ID + "already exists in Movie table");
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
