package utils;

import fileio.ActionInputData;

import java.util.*;
import java.util.stream.Collectors;

public class Sort {

    public static List<String> sortByDouble(Map<String, Double> ratings, ActionInputData action) {
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
        }
        else
            return new ArrayList<>(sortedMap.keySet());
    }

    public static List<String> sortByInteger(Map<String, Integer> ratings, ActionInputData action) {
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            ratings.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }
        if (action.getSortType().equals("desc")) {
            ratings.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()).
                            thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        }
        if (action.getNumber() != 0) {
            return sortedMap.keySet().stream().limit(action.getNumber()).collect(Collectors.toList());
        }
        else
            return new ArrayList<>(sortedMap.keySet());
    }

}
