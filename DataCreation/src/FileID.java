import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class FileID {
	private JSONParser parser;
	private JSONArray fromFile = new JSONArray();
	
	@SuppressWarnings("unchecked")
	public FileID(String filename) {
		 this.parser = new JSONParser();
		 
		 try {
			FileReader reader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = "";
			while((line = bufferedReader.readLine()) != null)
				this.fromFile.add((JSONObject)parser.parse(line));
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.err.print("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("IOException");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.print("ParseException");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Long> getIDs(){
		ArrayList<Long>output = new ArrayList<Long>();
		for(Object o : fromFile) {
			JSONObject original = (JSONObject) o;
			
			Long id = (Long) original.get("id");
			
			output.add(id);
		}
		return output;
	}
	
	public ArrayList<Movie> getMovies(){
		ArrayList<Movie> output = new ArrayList<Movie>();
		for(Object o : fromFile) {
			JSONObject original = (JSONObject) o;
			Long id = (Long) original.get("id");
			String title = (String) original.get("title");
			Double rating = (Double) original.get("rating");
			String releaseDate = (String) original.get("releaseDate");
			Long runtime = (Long) original.get("runtime");
			JSONArray jproviders = (JSONArray) original.get("providers");
			ArrayList<Long> providers = new ArrayList<Long>();
			for(Object oo : jproviders) {
				providers.add((Long) oo);
			}
			JSONArray jactors = (JSONArray) original.get("actors");
			ArrayList<Long> actors = new ArrayList<Long>();
			for(Object oo : jactors) {
				actors.add((Long) oo);
			}
			
			Movie movie = new Movie(id);
			movie.title = title;
			movie.rating = rating;
			movie.releaseDate = releaseDate;
			movie.runtime = runtime;
			movie.providers = providers;
			movie.actors = actors;
			output.add(movie);
		}
		return output;
	}
	
	//EDIT THIS
	public ArrayList<TVShow> getTVShows(){
		ArrayList<TVShow> output = new ArrayList<TVShow>();
		for(Object o : fromFile) {
			JSONObject original = (JSONObject) o;
			Long id = (Long) original.get("id");
			String title = (String) original.get("title");
			Double rating = (Double) original.get("rating");
			String releaseDate = (String) original.get("releaseDate");
			Long runtime = (Long) original.get("runtime");
			JSONArray jproviders = (JSONArray) original.get("providers");
			ArrayList<Long> providers = new ArrayList<Long>();
			for(Object oo : jproviders) {
				providers.add((Long) oo);
			}
			JSONArray jactors = (JSONArray) original.get("actors");
			ArrayList<Long> actors = new ArrayList<Long>();
			for(Object oo : jactors) {
				actors.add((Long) oo);
			}
			
			TVShow show = new TVShow(id);
			show.title = title;
			show.rating = rating;
			show.releaseDate = releaseDate;
//			show.runtime = runtime;
			show.providers = providers;
			show.actors = actors;
			output.add(show);
		}
		return output;
	}
	
	//EDIT THIS
		public ArrayList<Actor> getActor(){
			ArrayList<Actor> output = new ArrayList<Actor>();
			for(Object o : fromFile) {
				JSONObject original = (JSONObject) o;
				Long id = (Long) original.get("id");
				String title = (String) original.get("title");
				Double rating = (Double) original.get("rating");
				String releaseDate = (String) original.get("releaseDate");
				Long runtime = (Long) original.get("runtime");
				JSONArray jproviders = (JSONArray) original.get("providers");
				ArrayList<Long> providers = new ArrayList<Long>();
				for(Object oo : jproviders) {
					providers.add((Long) oo);
				}
				JSONArray jactors = (JSONArray) original.get("actors");
				ArrayList<Long> actors = new ArrayList<Long>();
				for(Object oo : jactors) {
					actors.add((Long) oo);
				}
				
				Actor actor = new Actor(id);
//				Actor.title = title;
//				Actor.rating = rating;
//				Actor.releaseDate = releaseDate;
//				show.runtime = runtime;
//				Actor.providers = providers;
//				Actor.actors = actors;
				output.add(actor);
			}
			return output;
		}
}
