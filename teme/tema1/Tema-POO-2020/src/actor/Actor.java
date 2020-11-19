package actor;

import fileio.ActorInputData;
import video.Movie;
import video.Show;
import video.Video;
import video.Videos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actor {

    private String name;
    private String careerDescription;

    private List<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    private Map<String, Integer> usedWords;
    private double mediumRatingValue;
    private int mediumRatingNum;

    private int awardsNumber;

    public Actor(String name, String careerDescription, List<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription.replaceAll("[^\\w]", " ").toLowerCase();
        this.filmography = filmography;
        this.awardsNumber = 0;
        this.awards = awards;
        this.usedWords = new HashMap<String, Integer>();
        mediumRatingValue = 0;
        mediumRatingNum = 0;
        getAwardsNum();
        getUsedWords();
    }

    public Actor(ActorInputData actor) {
        this(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards());
    }

    public boolean descriptionContains(List<String> words) {

        for(String word : words) {
            if(!usedWords.containsKey(word)) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public List<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public boolean hasAwards(List<String> awards) {
        for(String award : awards){
            if(!this.awards.containsKey(ActorsAwards.valueOf(award))) {
                return false;
            }
        }
        return true;
    }

    private void getAwardsNum() {
        for(ActorsAwards award : ActorsAwards.values()) {
            awardsNumber += awards.getOrDefault(award, 0);
        }
    }

    private void getUsedWords() {
        for(String word : careerDescription.split(" ")) {
            usedWords.put(word, 1);
        }
    }

    public int getAwardsNumber() {
        return awardsNumber;
    }

    public void modifyRating(double rating, double oldRating) {
        mediumRatingValue = mediumRatingValue - oldRating + rating;
        if(oldRating == 0) {
            mediumRatingNum += 1;
        }
    }

    public double getRating() {
        if(mediumRatingNum == 0) {
            return 0;
        }
        return mediumRatingValue / mediumRatingNum;
    }

    @Override
    public String toString() {
        return name;
    }
}
