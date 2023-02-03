import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ui.LoginFrame;

public class main {

	public static void Main(String[] args) {
		createFiles();
		//addToDB();
		LoginFrame login = new LoginFrame();
		login.createFrame();
	}
	
	private static void createFiles() {
		ObjectCreator oc = new ObjectCreator();
//		oc.createMovies();
//		oc.createTVShows();
//		oc.createActors();
		oc.createStreamingServices();
	}
	
	private static void addToDB() {
		DatabaseConnectionService dbcs = null;
		try(InputStream input = new FileInputStream("../DataCreation/src/mediadb.properties")) {
			Properties properties = new Properties();
			properties.load(input);
			dbcs = new DatabaseConnectionService(properties.getProperty("serverName"), properties.getProperty("databaseName"));
			dbcs.connect(properties.getProperty("serverUsername"), properties.getProperty("serverPassword"));
			
			MovieService mov = new MovieService(dbcs);
			mov.addMovies();
		} catch (IOException ex) {
            ex.printStackTrace();
        }
		if(dbcs != null) {
			dbcs.closeConnection();
		}
	}
}
