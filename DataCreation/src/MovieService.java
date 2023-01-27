import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MovieService {
	private String movieFilepath = "../DataCreation/outputMovies.txt";
	private DatabaseConnectionService dbService = null;
	
	public MovieService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addMovies() {
		FileID fileReader = new FileID(movieFilepath);
		ArrayList<Movie> movies = fileReader.getMovies();
		for(Movie mov : movies) {
			try{
				CallableStatement cs = dbService.getConnection().prepareCall("{? = call addMedia(?, ?, ?, ?, ?)}");
				cs.setLong(2,mov.ID);
				cs.setString(3, mov.title);
				cs.setFloat(4, (float) mov.rating);
				cs.setString(5,  mov.releaseDate);
				cs.setInt(6, 0);
				
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID" + mov.ID + "already exists in Movie table");
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
					JOptionPane.showMessageDialog(null, "Rating must be between 0 and 5");
				}
				
				
				cs = dbService.getConnection().prepareCall("{? = call addMovie(?, ?)}");
				cs.setLong(2, mov.ID);
				cs.setInt(3,  (int) mov.runtime);
				
				cs.execute();
				returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID can not be null");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID" + mov.ID + "does not exist in Media table");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ID" + mov.ID + "already exists in Movie table");
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
