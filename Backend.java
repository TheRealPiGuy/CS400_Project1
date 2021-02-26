import java.io.StringReader;
import java.util.List;

public class Backend implements BackendInterface
{
	HashTableMap<String, String> myHashTable = new HashTableMap<String, String>();
	MovieData myDummy;
	
	public Backend(StringReader read)
	{
		myDummy = new MovieData();
	}
	
	public void addGenre(String genre) 
	{
		myHashTable.put(genre, null);
	}

	public void addAvgRating(String rating) 
	{
		myHashTable.put(null,rating);
	}

	public void removeGenre(String genre) 
	{
		myHashTable.remove(genre);
	}

	public void removeAvgRating(String rating) 
	{
		myHashTable.remove(rating);
	}

	public List<String> getGenres() 
	{
		return ((BackendInterface) myDummy).getGenres();
	}

	public List<String> getAvgRatings() 
	{
		return ((BackendInterface) myDummy).getAvgRatings();
	}

	public int getNumberOfMovies() 
	{
		return myHashTable.size();
	}

	public List<String> getAllGenres() 
	{
		return null;
	}	
	
	public List<MovieInterface> getThreeMovies(int startingIndex) 
	{
		return null;
	}

}
