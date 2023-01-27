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
	
	public int addTV(TVShow tv) {
		return 1;
	}
	
//	@SuppressWarnings("unchecked")
//	public int addMedia(TVShow tv) {
//		JSONObject tvJSON = new JSONObject();
//		tvJSON.put("id", tv.ID);
//		tvJSON.put("title",  tv.title);
//		tvJSON.put("rating", (double)tv.rating);
//		tvJSON.put("releaseDate", tv.releaseDate);
//		
//		try {
//			file = new BufferedWriter(new FileWriter("../DataCreation/outputTV.txt",true));
//            file.write(tvJSON.toJSONString());
//            file.newLine();
//            file.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return 1;
//        }
//		return 0;
//	}
	public int addActor(Actor actor) {
		return 1;
	}
	
	public int addService() {
		return 1;
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
