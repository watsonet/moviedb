package backend.Classes;
import java.util.ArrayList;

public class TVShow {
	public long ID;
	public String title = "";
	public double rating = 0;
	public String releaseDate = "";
	public Long numSeasons = (long) 0;
	public Long numEpisodes = (long) 0;
	public String lastEpDate = "";
	public ArrayList<Long> providers = new ArrayList<Long>();
	public ArrayList<Long> actors = new ArrayList<Long>();
	
	public TVShow(long ID) {
		this.ID = ID;
	}
}
