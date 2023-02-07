package backend.ClassServices;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import backend.DatabaseConnectionService;
import backend.Classes.Actor;
import backend.FileTools.FileID;

public class ActorService {
	//check the filepath
	private String ActorFilepath = "../DataCreation/outputActors.txt";
	private DatabaseConnectionService dbService = null;
	
	public ActorService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addActors() {
		FileID fileReader = new FileID(ActorFilepath);
		ArrayList<Actor> actors = fileReader.getActor();
		for(Actor act : actors) {
			try{
				CallableStatement cs = dbService.getConnection().prepareCall("{? = call addActor(?, ?, ?)}");
				cs.setLong(2, act.ID);
				cs.setString(3, act.name); //how is fname and lname split????
				cs.setString(4, act.gender);

				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					//JOptionPane.showMessageDialog(null, "ID already exists");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID cannot be null");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "Invalid ID value");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "Name cannot be null");
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
