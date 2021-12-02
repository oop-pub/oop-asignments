package queries;
import actor.ActorsAwards;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActionInputData;
import fileio.ActorInputData;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class ActorQuery {
    private String msg;

    public final String getMessage() {
        return msg;
    }

    /**
     * The method provides the actors searched by awards
     * @param action the action that needs to be done
     * @param actorList the list of actors from database
     */
    public void prize(final ActionInputData action, final List<ActorInputData> actorList) {
        List<String> prize;
        prize = unmodifiableList(action.getFilters()
                .get(action.getFilters().size() - 1));
        HashMap<String, Double> searchActor = new HashMap<>();

        for (int j = 0, actorListSize = actorList.size(); j < actorListSize; j++) {
            ActorInputData actorInputData = actorList.get(j);
            int bun = 0;
            for (int i = 0; i < prize.size(); i++) {
                String award = prize.get(i);
                if (!actorInputData.getAwards().containsKey(ActorsAwards.valueOf(award))) {
                    bun = 0;
                    break;
                } else {
                    bun = 1;
                }
            }
            if (bun == 1) {
                if (actorInputData.totalAwards() != 0) {
                    var put = searchActor.put(actorInputData.getName(),
                            actorInputData.totalAwards().doubleValue());
                }
            }
        }

        msg = HelperSort.sorting(action, searchActor);
    }
    /**
     * Getting the searched actors sorted by ratings
     * @param action the action that needs to be done
     * @param actorList the list of actors from database
     * @param movieList the list of movies from database
     * @param serialList the list of serials from database
     */
    public void average(final ActionInputData action, final List<ActorInputData> actorList,
                        final List<MovieInputData> movieList,
                        final List<SerialInputData> serialList) {
        Map<String, Double> actors;
        actors = new HashMap<>();
        for (int i = 0, actorListSize = actorList.size(); i < actorListSize; i++) {
            ActorInputData actorsData = actorList.get(i);
            if (actorsData.getFilmography().size() != 0) {
                ArrayList<String> filmography = actorsData.getFilmography();
                for (int j = 0, filmographySize = filmography.size(); j < filmographySize; j++) {
                    String film = filmography.get(j);
                    movieList.stream().filter(movie -> film.equals(movie.getTitle()))
                            .filter(movie
                                    -> movie.average() != 0).forEachOrdered(movie
                                    -> actorsData.getActorsAverage().put(movie.getTitle(),
                                    movie.average()));
                }
                for (int j = 0, filmographySize = filmography.size(); j < filmographySize; j++) {
                    String show = filmography.get(j);
                    serialList.stream().filter(series -> show.equals(series.getTitle())
                            && series.average() != 0)
                            .forEachOrdered(series
                            -> actorsData.getActorsAverage().put(series.getTitle(),
                                    series.average()));
                }
            }
        }


        for (int i = 0, actorListSize = actorList.size(); i < actorListSize; i++) {
            ActorInputData actor;
            actor = actorList.get(i);
            if (actor.getActorsAverage().size() != 0) if (actor.average() != 0) {
                var put = actors.put(actor.getName(), actor.average());
            }
        }
        msg = HelperSort.sorting(action, actors);
    }


}
