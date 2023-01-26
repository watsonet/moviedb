import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class ObjectCreator{
	String url = "https://api.themoviedb.org/3/";
	String key = "?api_key=a4fda0a2adb7aecab7457592037b3b19";
	URLConnection connection;
	
	String peopleFilepath = "../DataCreation/person_ids_01_11_2023.json";
	String movieFilepath = "../DataCreation/movie_ids_01_11_2023.json";
	String tvShowFilepath = "../DataCreation/tv_series_ids_01_11_2023.json";
	
	ArrayList<Actor> actors = new ArrayList<Actor>();
	ArrayList<Movie> movies = new ArrayList<Movie>();
	ArrayList<TVShow> tvShows = new ArrayList<TVShow>();
	
	public ObjectCreator() {}
	public ArrayList<Actor> getActors() {
		return actors;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<TVShow> getTvShows() {
		return tvShows;
	}
	
	public void createActors() {
		String actorURL = url + "person/";
		FileID actorReader = new FileID(peopleFilepath);
		ArrayList<Long> ActorIDs = actorReader.getIDs();
		for(Long id : ActorIDs) {
			Actor toAdd = new Actor(id);
			try {
				connection = new URL(actorURL + id + key).openConnection();
				connection.setRequestProperty("Accept-Charset", "UTF-8");
			} catch (MalformedURLException e) {
				System.err.println("bad url");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}
			
			try {
				InputStream response = connection.getInputStream();
				System.out.println("responded!");
				JSONParser jsonParser = new JSONParser();
				JSONObject extendedActorObject = (JSONObject)jsonParser.parse(new InputStreamReader(response, "UTF-8"));
				toAdd.name = (String) extendedActorObject.get("name");
				long tempGender = (Long) extendedActorObject.get("gender");
				toAdd.gender = tempGender == 2 ? "male" : tempGender == 1 ? "female": "other";
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			} catch (ParseException e) {
				System.err.println("ParseException");
				e.printStackTrace();
			}
			actors.add(toAdd);
		}
	}
	public void createMovies() {
		String movieURL = url + "movie/";
		FileID movieReader = new FileID(movieFilepath);
		ArrayList<Long> MovieIDs = movieReader.getIDs();
		for(long id : MovieIDs) {
			Movie toAdd = new Movie(id);
			try {
				connection = new URL(movieURL + id + key + "&append_to_response = providers, credits").openConnection();
				connection.setRequestProperty("Accept-Charset", "UTF-8");
			} catch (MalformedURLException e) {
				System.err.println("bad url");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}
			
			try {
				InputStream response = connection.getInputStream();
				JSONParser jsonParser = new JSONParser();
				JSONObject extendedMovieObject = (JSONObject)jsonParser.parse(new InputStreamReader(response, "UTF-8"));
				toAdd.title = (String) extendedMovieObject.get("title");
				toAdd.rating = (float) extendedMovieObject.get("vote_average");
				toAdd.releaseDate = (String) extendedMovieObject.get("release_date");
				toAdd.runtime = (int) extendedMovieObject.get("runtime");
				
				JSONArray castIDS = (JSONArray) extendedMovieObject.get("cast");
				for(Object c : castIDS) {
					toAdd.actors.add((Integer) c);
				}
				JSONArray providerIDs = (JSONArray) extendedMovieObject.get("cast");
				for(Object p : providerIDs) {
					toAdd.providers.add((Integer) p);
				}
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			} catch (ParseException e) {
				System.err.println("ParseException");
				e.printStackTrace();
			}
			movies.add(toAdd);
		}
	}
	public void createTVShows() {
		String tvURL = url + "tv/";
		FileID tvReader = new FileID(tvShowFilepath);
		ArrayList<Long> TVShowIDs = tvReader.getIDs();
		for(long id : TVShowIDs) {
			TVShow toAdd = new TVShow(id);
			try {
				connection = new URL(tvURL + id + key + "&append_to_response = providers, credits").openConnection();
				connection.setRequestProperty("Accept-Charset", "UTF-8");
			} catch (MalformedURLException e) {
				System.err.println("bad url");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}
			
			try {
				InputStream response = connection.getInputStream();
				JSONParser jsonParser = new JSONParser();
				JSONObject extendedMovieObject = (JSONObject)jsonParser.parse(new InputStreamReader(response, "UTF-8"));
				toAdd.title = (String) extendedMovieObject.get("title");
				toAdd.rating = (float) extendedMovieObject.get("vote_average");
				toAdd.releaseDate = (String) extendedMovieObject.get("first_air_date");
				toAdd.numSeasons = (int) extendedMovieObject.get("number_of_seasons");
				toAdd.numEpisodes = (int) extendedMovieObject.get("number_of_episodes");
				
				JSONArray castIDS = (JSONArray) extendedMovieObject.get("cast");
				for(Object c : castIDS) {
					toAdd.actors.add((Integer) c);
				}
				JSONArray providerIDs = (JSONArray) extendedMovieObject.get("cast");
				for(Object p : providerIDs) {
					toAdd.providers.add((Integer) p);
				}
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			} catch (ParseException e) {
				System.err.println("ParseException");
				e.printStackTrace();
			}
			tvShows.add(toAdd);
		}
	}
}
