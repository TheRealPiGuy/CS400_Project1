
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class MovieData implements MovieInterface, MovieDataReaderInterface {

    private String title;
    private Integer year;
    private List<String> genre;
    private String director;
    private String description;
    private Float avgVote;

    public MovieData(String title, int year, List<String> genre, String director, String description, Float avgVote) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.description = description;
        this.avgVote = avgVote;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public List<String> getGenres() {
        return genre;
    }

    @Override
    public String getDirector() {
        return director;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Float getAvgVote() {
        return avgVote;
    }

    @Override
    public int compareTo(MovieInterface otherMovie) {
        return 0;
    }

    @Override
    public List<MovieInterface> readDataSet(Reader inputFileReader) throws FileNotFoundException, IOException, DataFormatException {

        ArrayList<MovieInterface> arrayList = new ArrayList<>();
        String title;
        Integer year;
        String rawGenre;
        List<String> genre = new ArrayList<>();
        String director;
        String description;
        Float avg_vote;
        Reader reader = inputFileReader;
        String dataString = "";
        int data = 0;

        while (data != -1) {
            dataString.concat((char) data + "");
            data = reader.read();
        }
        String[] lines = dataString.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace("\r", "");
        }
        //lines are now separated in array
        for (String line : lines) {
            ArrayList<String> row = new ArrayList<>();
            String element = "";
            char[] lineArray = line.toCharArray();
            boolean group=false;
            for (int i = 0; i < lineArray.length; i++) {
                if (lineArray[i] == ',' && !group) {
                    row.add(element);
                    element = "";
                    continue;
                }
                if (lineArray[i] == '"') {
                    group = !group;
                    continue;
                }
                element.concat(lineArray[i] + "");
            }
            title = row.get(0);
            year = Integer.valueOf(row.get(2));
            rawGenre = row.get(3);
            director =row.get(7);
            description = row.get(11);
            avg_vote = Float.valueOf(row.get(12));
            String[] tmp = rawGenre.split(",");
            for(String string:tmp){
                genre.add(string);
            }
            MovieData movieData = new MovieData(title, year, genre, director, description, avg_vote);
            arrayList.add(movieData);
        }
        return arrayList;
    }
}
