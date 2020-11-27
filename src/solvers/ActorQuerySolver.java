package solvers;

import actor.Actor;
import actor.Actors;
import fileio.ActionInputData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ActorQuerySolver {

    /**
     * Solves the queries for actors
     * @param action
     * @return
     */
    public static String solve(final ActionInputData action) {
        List<Actor> actors = Actors.getInstance().getAll();
        List<Actor> filteredActors = null;
        Filter filter = new Filter(
                action.getFilters().get(2),
                action.getFilters().get(action.getFilters().size() - 1)
        );
        Stream<Actor> unorderedActors = actors.stream();

        switch (action.getCriteria()) {
            case "average":
                if (action.getSortType().equals("asc")) {
                    filteredActors = unorderedActors
                            .filter(actor -> actor.getRating() != 0)
                            .sorted(Comparator.comparing(Actor::getRating)
                            .thenComparing(Actor::getName))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredActors = unorderedActors
                            .filter(actor -> actor.getRating() != 0)
                            .sorted(Comparator.comparing(Actor::getRating)
                            .thenComparing(Actor::getName)
                            .reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }
                break;
            case "awards":

                unorderedActors = unorderedActors.filter(actor -> filter.getAwards() == null
                        || actor.hasAwards(filter.getAwards()));

                if (action.getSortType().equals("asc")) {
                    filteredActors = unorderedActors
                            .sorted(Comparator.comparing(Actor::getAwardsNumber)
                                    .thenComparing(Actor::getName))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredActors = unorderedActors
                            .sorted(Comparator.comparing(Actor::getAwardsNumber)
                                    .thenComparing(Actor::getName).reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }
                break;
            case "filter_description":
                unorderedActors = unorderedActors.filter(actor -> filter.getWords() == null
                        || actor.descriptionContains(filter.getWords()));
                if (action.getSortType().equals("asc")) {
                    filteredActors = unorderedActors
                            .sorted(Comparator.comparing(Actor::getName))
                            .collect(Collectors.toList());
                } else {
                    filteredActors = unorderedActors
                            .sorted(Comparator.comparing(Actor::getName).reversed())
                            .collect(Collectors.toList());
                }
                break;

            default:
                break;
        }

        return String.format("Query result: %s", filteredActors);
    }
}
