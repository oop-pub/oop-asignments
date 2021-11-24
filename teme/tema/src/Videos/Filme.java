package Videos;

import java.util.ArrayList;

public class Filme extends Video{
    private int durata;
    private double rating;

    public Filme(String titlu, int anLansare, ArrayList<String> gen, int durata) {
        super(titlu, anLansare, gen);
        this.durata = durata;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
