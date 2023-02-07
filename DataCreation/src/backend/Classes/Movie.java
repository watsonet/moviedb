package backend.Classes;
import java.util.ArrayList;

public class Movie {
	public long ID;
	public String title = "";
	public double rating = 0;
	public String releaseDate = "";
	public long runtime = 0;
	public ArrayList<Long> providers = new ArrayList<Long>();
	public ArrayList<Long> actors = new ArrayList<Long>();
	
	public Movie(long ID) {
		this.ID = ID;
	}
	
}
