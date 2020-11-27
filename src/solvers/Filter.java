package solvers;

import java.util.List;

public final class Filter {

    private Integer year;
    private String genre;
    private List<String> awards;
    private List<String> words;

    public Filter(final String year, final String genre) { // Constructor for videos
        if (year == null) {
            this.year = null;
        } else {
            this.year = Integer.parseInt(year);
        }
        this.genre = genre;
    }

    public Filter(final List<String> words, final List<String> awards) { // Constructor for actors
        this.awards = awards;
        this.words = words;
    }

    public Integer getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getAwards() {
        return awards;
    }

    public List<String> getWords() {
        return words;
    }
}
