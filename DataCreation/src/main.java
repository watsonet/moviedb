import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class main {

	public static void main(String[] args) {
		//createFiles();
		addToDB();
	}
	
	private static void createFiles() {
		ObjectCreator oc = new ObjectCreator();
		oc.createMovies();
		oc.createTVShows();
		oc.createActors();
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
