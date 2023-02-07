package backend;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import backend.ClassServices.ActorService;
import backend.ClassServices.MovieService;
import backend.ClassServices.StreamingServiceService;
import backend.ClassServices.TVShowService;

public class DataLoaderMain {

	public static void main(String[] args) {
		//int files = createFiles();
		int add = addToDB();
		System.out.println(add);
	}
	
	private static int createFiles() {
		ObjectCreator oc = new ObjectCreator();
		//oc.createMovies();
		//oc.createTVShows();
		//oc.createStreamingServices();
		oc.createActors();
		return 0;
	}
	
	private static int addToDB() {
		DatabaseConnectionService dbcs = null;
		try(InputStream input = new FileInputStream("../DataCreation/src/mediadb.properties")) {
			Properties properties = new Properties();
			properties.load(input);
			dbcs = new DatabaseConnectionService(properties.getProperty("serverName"), properties.getProperty("databaseName"));
			dbcs.connect(properties.getProperty("serverUsername"), properties.getProperty("serverPassword"));
			
			StreamingServiceService serve = new StreamingServiceService(dbcs);
			ActorService act = new ActorService(dbcs);
			TVShowService tv = new TVShowService(dbcs);
			MovieService mov = new MovieService(dbcs);
			serve.addService();
			act.addActors();
			tv.addTVShows();
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
