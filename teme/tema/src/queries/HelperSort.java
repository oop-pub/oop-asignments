package queries;


import fileio.ActionInputData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class HelperSort {

    private HelperSort() { }

    /**
     * This method sorts a map first by value
     * and then by key for alphabetical order,
     * using a customized comparator
     * @param map list of map from database
     * @param sortType the type of sorting
     * @return a sorted map
     */


    /**
     * Returning the first N elements for a query
     * @param action the action that needs to be done
     * @param map the sorted map
     * @return the result of a query
     */
    public static String sorting(final ActionInputData action,
                                 final Map<String, Double> map) {
        Map<String, Double> sortedShow = Sort.addsort(map, action.getSortType());

        List<String> searchQuery;
        searchQuery = new ArrayList<>();
        int c = 0;

        if (action.getNumber() <= sortedShow.size()) {
        } else {
            c = action.getNumber() - map.size();
        }
        Iterator<String> it;
        it = sortedShow.keySet().iterator();
        if (c < action.getNumber() && it.hasNext()) {
            do {
                searchQuery.add(it.next());
                ++c;
            } while (c < action.getNumber() && it.hasNext());
        }
        return "Query result: " + searchQuery;
    }
}
