import java.util.ArrayList;

public class Movie {
	public long ID;
	public String title = "";
	public String company = "";
	public float rating = 0;
	public String releaseDate = "";
	public int runtime;
	public ArrayList<Integer> providers;
	public ArrayList<Integer> actors;
	
	public Movie(long ID) {
		this.ID = ID;
	}
	
}
