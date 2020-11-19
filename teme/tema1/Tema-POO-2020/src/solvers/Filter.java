package solvers;

import actor.ActorsAwards;

import java.util.List;
import java.util.stream.Collectors;

public final class Filter {

    Integer year;
    String genre;
    List<String> awards;
    List<String> words;

    public Filter(String year, String genre) { // Constructor for videos
        if(year == null) {
            this.year = null;
        } else {
            this.year = Integer.parseInt(year);
        }
        this.genre = genre;
    }

    public Filter(List<String> words, List<String> awards) { // Constructor for actors
        this.awards = awards;
        this.words = words;
    }



}
