package command;

import fileio.*;
import query.MovieRating;
import query.SerialRating;
import utils.Searcher;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class QueryAverage {
    List<MovieInputData> movie;
    List<SerialInputData> serial;

    public QueryAverage(List<MovieInputData> movie, List<SerialInputData> serial) {
        this.movie = movie;
        this.serial = serial;
    }

    public Double setActorRating(Input input, ActorInputData actor) {
        List<String> filmography = actor.getFilmography();
        int numberOfVideos = 0;
        double sum = 0;
        double rating;
        for (String video : filmography) {
            Searcher searcher = new Searcher(movie, serial);
            MovieInputData movie = searcher.lookForMovie(video);
            if (movie != null) {
                MovieRating helper = new MovieRating(movie, null);
                rating = helper.getTotalMovieRating();
                if (rating != 0) {
                    sum += rating;
                    numberOfVideos++;
                }
            }
            SerialInputData serial = searcher.lookForSerial(video);
            if (serial != null) {
                SerialRating helper = new SerialRating(null, serial);
                rating = helper.getTotalSerialRating();
                if (rating != 0) {
                    sum += rating;
                    numberOfVideos++;
                }
            }
        }
        if (numberOfVideos == 0) {
            return Double.valueOf(0);
        }
        return sum / numberOfVideos;
    }

    public void queryAverage(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        Map<String, Double> ratings = new LinkedHashMap<>();
        int number = action.getNumber();
        for (ActorInputData actor : input.getActors()) {
            Double rating = setActorRating(input, actor);
            if (rating != 0) {
                ratings.put(actor.getName(), rating);
            }
        }
        Map<String, Double> result2 = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            ratings.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        }
        if (action.getSortType().equals("desc")) {
            ratings.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()).
                            thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        }
        List<String> avgActors = result2.keySet().stream().limit(number).collect(Collectors.toList());
        String result = avgActors.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);


    }
}