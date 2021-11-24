package Videos;

import entertainment.Season;

import java.util.ArrayList;

public class Seriale extends Video{
    private ArrayList<Season> sezoane;
    private double rating;

    public Seriale(Season sezon) {
        sezoane = new ArrayList<>();
        sezoane.add(sezon);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addSezon(Season sezon){
        sezoane.add(sezon);
    }

    public ArrayList<Season> getSezoane() {
        return sezoane;
    }
}
