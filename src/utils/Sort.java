package utils;

import fileio.ActionInputData;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Sort {
    private Sort() {

    }

    /**
     * Sorts a map of names and ratings by value and then by key
     * @param ratings the map which needs to be sorted
     * @param action the action to be done
     * @return a list of the names sorted accordingly
     */
    public static List<String> sortByDouble(final Map<String, Double> ratings,
                                            final ActionInputData action) {
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            ratings.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }
        if (action.getSortType().equals("desc")) {
            ratings.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()).
                            thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }

        if (action.getNumber() != 0) {
            return sortedMap.keySet().stream()
                    .limit(action.getNumber()).collect(Collectors.toList());
        } else {
            return new ArrayList<>(sortedMap.keySet());
        }
    }

    /**
     * Sorts a map of names and Integers by value and the by key
     * @param values the map which needs to be sorted
     * @param action the action to be done
     * @return a list of the names sorted accordingly
     */
    public static List<String> sortByInteger(final Map<String, Integer> values,
                                             final ActionInputData action) {
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            values.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }
        if (action.getSortType().equals("desc")) {
            values.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()).
                            thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }
        if (action.getNumber() != 0) {
            return sortedMap.keySet().stream()
                    .limit(action.getNumber()).
                    collect(Collectors.toList());
        } else {
            return new ArrayList<>(sortedMap.keySet());
        }
    }

}
