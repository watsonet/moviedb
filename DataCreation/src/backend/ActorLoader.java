package backend;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import backend.ClassServices.ActorService;

public class ActorLoader {

	public static void main(String[] args) {
		ObjectCreator oc = new ObjectCreator();
		oc.createActors();
		addToDB();
	}
	private static int addToDB() {
		DatabaseConnectionService dbcs = null;
		try(InputStream input = new FileInputStream("../DataCreation/src/mediadb.properties")) {
			Properties properties = new Properties();
			properties.load(input);
			dbcs = new DatabaseConnectionService(properties.getProperty("serverName"), properties.getProperty("databaseName"));
			dbcs.connect(properties.getProperty("serverUsername"), properties.getProperty("serverPassword"));
			

			ActorService act = new ActorService(dbcs);

			act.addActors();

			
		} catch (IOException ex) {
            ex.printStackTrace();
        }
		if(dbcs != null) {
			dbcs.closeConnection();
			return 0;
		}
		return 1;
	}
}
