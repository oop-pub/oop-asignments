package queries;

import common.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Sort {

    private Sort() { }

    /**
     *
     * @param map
     * @param sortType
     * @return
     */
     public static Map<String, Double> addsort(final Map<String, Double> map,
                                               final String sortType) {
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();

        ArrayList<Map.Entry<String, Double>> lista;
        lista = new ArrayList<>(map.entrySet());

        var comp =
                (Comparator<Map.Entry<String, Double>>) (o1, o2) -> {
                    Double value1 = o1.getValue();
                    Double value2 = o2.getValue();
                    int var;
                    var = value1.compareTo(value2);

                    switch (var) {
                        case 0 -> {
                            String val1;
                            val1 = o1.getKey();
                            String val2;
                            val2 = o2.getKey();
                            return switch (sortType) {
                                case Constants.DESCENDING -> val2.compareTo(val1);
                                default -> val1.compareTo(val2);
                            };
                        }
                    }

                    return switch (sortType) {
                        case Constants.DESCENDING -> value2.compareTo(value1);
                        default -> value1.compareTo(value2);
                    };
                };
        lista.sort(comp);

        for (int i = 0, listSize = lista.size(); i < listSize; i++) {
            Map.Entry<String, Double> element = lista.get(i);
            sortedMap.put(element.getKey(), element.getValue());
        }

        return sortedMap;
    }
}
