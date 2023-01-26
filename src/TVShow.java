import java.util.ArrayList;

public class TVShow {
	public long ID;
	public String title = "";
	public String company = "";
	public float rating = 0;
	public String releaseDate = "";
	public int numSeasons = 0;
	public int numEpisodes = 0;
	public String lastEpDate = "";
	public ArrayList<Integer> providers;
	public ArrayList<Integer> actors;
	
	public TVShow(long ID) {
		this.ID = ID;
	}
}
