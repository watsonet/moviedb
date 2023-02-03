import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataLoaderMain {

	public static void main(String[] args) {
		int files = createFiles();
		//int add = addToDB();
	}
	
	private static int createFiles() {
		ObjectCreator oc = new ObjectCreator();
		oc.createMovies();
		oc.createTVShows();
		oc.createActors();
		oc.createStreamingServices();
		return 0;
	}
	
	private static int addToDB() {
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
			return 0;
		}
		return 1;
	}
}
