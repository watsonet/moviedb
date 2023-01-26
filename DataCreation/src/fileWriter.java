import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class fileWriter {
	
	@SuppressWarnings("unchecked")
	public int addMovie(Movie movie) {
		JSONObject movieJSON = new JSONObject();
		movieJSON.put("id", movie.ID);
		movieJSON.put("title",  movie.title);
		movieJSON.put("rating", (double)movie.rating);
		movieJSON.put("releaseDate", movie.releaseDate);
		movieJSON.put("runtime", movie.runtime);
		movieJSON.put("providers", (ArrayList<Long>)movie.providers);
		movieJSON.put("actors", (ArrayList<Long>)movie.actors);
		
		try (BufferedWriter file = new BufferedWriter(new FileWriter("../DataCreation/outputMovies.txt"))) {
            file.write(movieJSON.toJSONString());
            file.newLine();
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
		return 0;
	}
	
	public int addTV(TVShow tv) {
		return 1;
	}
	
	public int addMedia() {
		return 1;
	}
	
	public int addActor(Actor actor) {
		return 1;
	}
	
	public int addService() {
		return 1;
	}
	
}
