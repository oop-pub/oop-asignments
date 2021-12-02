package queries;

import fileio.ActionInputData;
import fileio.MovieInputData;

import java.util.List;
import java.util.stream.Collectors;

public class FilterMovie {
    /**
     *
     * @param action
     * @param movieList
     * @return
     */
    public static List<MovieInputData> verFilter(final ActionInputData action,
                                                 final List<MovieInputData> movieList) {
        List<MovieInputData> lista1;
        if (action.getFilters().get(0).get(0) != null) {
            lista1 = !action.getFilters().get(0).get(0).equals("null") ? movieList.stream()
                    .filter(s -> s.getYear() == Integer
                            .parseInt(action.getFilters().get(0).get(0)))
                    .collect(Collectors.toList()) : movieList;
            if (action.getFilters().get(1).get(0) != null) {
                if (action.getFilters().get(1).get(0).equals("null")) {
                    return lista1;
                } else {
                    return lista1.stream()
                            .filter(s -> s.getGenres().contains(action.
                                    getFilters().get(1).get(0)))
                            .collect(Collectors.toList());
                }
            } else {
                return lista1;
            }
        } else {
            lista1 = movieList;
            if (action.getFilters().get(1).get(0) == null) {
                return lista1;
            } else if (action.getFilters().get(1).get(0).equals("null")) {
                return lista1;
            } else {
                return lista1.stream()
                        .filter(s -> s.getGenres().contains(action.
                                getFilters().get(1).get(0)))
                        .collect(Collectors.toList());
            }
        }

    }
}
