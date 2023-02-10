import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class fileWriter {
	private BufferedWriter file;
	
	public fileWriter(){}
	
	@SuppressWarnings("unchecked")
	public int addMovie(Movie movie) {
		JSONObject movieJSON = new JSONObject();
		movieJSON.put("actors", (ArrayList<Long>)movie.actors);
		movieJSON.put("providers", (ArrayList<Long>)movie.providers);
		movieJSON.put("runtime", movie.runtime);
		movieJSON.put("releaseDate", movie.releaseDate);
		movieJSON.put("rating", (double)movie.rating);
		movieJSON.put("title", movie.title);
		movieJSON.put("id", movie.ID);
		
		try {
			file = new BufferedWriter(new FileWriter("../DataCreation/outputMovies.txt",true));
            file.write(movieJSON.toJSONString());
            file.newLine();
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public int addTV(TVShow tv) {
		JSONObject tvJSON = new JSONObject();
		tvJSON.put("actors", (ArrayList<Long>)tv.actors);
		tvJSON.put("providers", (ArrayList<Long>)tv.providers);
		tvJSON.put("numSeasons", tv.numSeasons);
		tvJSON.put("numEpisodes", tv.numEpisodes);
		tvJSON.put("releaseDate", tv.releaseDate);
		tvJSON.put("lastEpDate", tv.lastEpDate);
		tvJSON.put("rating", (double)tv.rating);
		tvJSON.put("title", tv.title);
		tvJSON.put("id", tv.ID);

		try {
			file = new BufferedWriter(new FileWriter("../DataCreation/outputTVShows.txt",true));
            file.write(tvJSON.toJSONString());
            file.newLine();
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
		return 0;	
	}
	
	@SuppressWarnings("unchecked")
	public int addActor(Actor actor) {
		JSONObject actorJSON = new JSONObject();
		actorJSON.put("name", actor.name);
		actorJSON.put("gender", actor.gender);
		actorJSON.put("id", actor.ID);

		try {
			file = new BufferedWriter(new FileWriter("../DataCreation/outputActors.txt",true));
            file.write(actorJSON.toJSONString());
            file.newLine();
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
		return 0;		
	}
	
	@SuppressWarnings("unchecked")
	public int addService(StreamingService service) {
		JSONObject serviceJSON = new JSONObject();
		serviceJSON.put("name", service.name);
		serviceJSON.put("id", service.ID);
		try {
			file = new BufferedWriter(new FileWriter("../DataCreation/outputServices.txt",true));
            file.write(serviceJSON.toJSONString());
            file.newLine();
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
		return 0;
	}
	
	public int closeWriter() {
		if(this.file == null) {
			return 1;
		}else {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
				return 2;
			}
		}
		return 0;
	}
}
