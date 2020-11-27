package actor;

import fileio.ActorInputData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Actor {

    private final String name;
    private final String careerDescription;

    private final List<String> filmography;
    private final Map<ActorsAwards, Integer> awards;
    private final Map<String, Integer> usedWords;
    private double mediumRatingValue;
    private int mediumRatingNum;

    private int awardsNumber;

    public Actor(final String name, final String careerDescription, final List<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
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

    public Actor(final ActorInputData actor) {
        this(actor.getName(), actor.getCareerDescription(),
                actor.getFilmography(), actor.getAwards());
    }


    /**
     * Check if the description contains the given words.
     * @param words
     * @return
     */
    public boolean descriptionContains(final List<String> words) {

        for (String word : words) {
            if (!usedWords.containsKey(word)) {
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

    /**
     * Checks if the actor has the given awards
     * @param awardList
     * @return
     */
    public boolean hasAwards(final List<String> awardList) {
        for (String award : awardList) {
            if (!awards.containsKey(ActorsAwards.valueOf(award))) {
                return false;
            }
        }
        return true;
    }

    private void getAwardsNum() {
        for (ActorsAwards award : ActorsAwards.values()) {
            awardsNumber += awards.getOrDefault(award, 0);
        }
    }

    private void getUsedWords() {
        for (String word : careerDescription.split(" ")) {
            usedWords.put(word, 1);
        }
    }

    public int getAwardsNumber() {
        return awardsNumber;
    }

    /**
     * Modifies the rating of the actor
     * @param rating
     * @param oldRating
     */
    public void modifyRating(final double rating, final double oldRating) {
        mediumRatingValue = mediumRatingValue - oldRating + rating;
        if (oldRating == 0) {
            mediumRatingNum += 1;
        }
    }

    /**
     * Returns the rating of the actor
     * @return
     */
    public double getRating() {

        if (mediumRatingNum == 0) {
            return 0;
        }

        return mediumRatingValue / mediumRatingNum;
    }

    @Override
    public String toString() {
        return name;
    }
}
