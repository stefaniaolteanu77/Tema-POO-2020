package query.actor;

import command.rating.SerialRating;
import fileio.*;
import command.rating.MovieRating;
import utils.Sort;
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

    private Double getActorRating(ActorInputData actor) {
        List<String> filmography = actor.getFilmography();
        int numberOfVideos = 0;
        double sum = 0;
        double rating;
        for (String video : filmography) {
            Searcher searcher = new Searcher(movie, serial);
            MovieInputData movie = searcher.lookForMovie(video);
            if (movie != null) {
                MovieRating helper = new MovieRating(movie);
                rating = helper.getTotalMovieRating();
                if (rating != 0) {
                    sum += rating;
                    numberOfVideos++;
                }
            }
            SerialInputData serial = searcher.lookForSerial(video);
            if (serial != null) {
                SerialRating helper = new SerialRating(serial);
                rating = helper.getTotalSerialRating();
                if (rating != 0) {
                    sum += rating;
                    numberOfVideos++;
                }
            }
        }
        if (numberOfVideos == 0) {
            return (double) 0;
        }
        return sum / numberOfVideos;
    }

    public void queryAverage(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        Map<String, Double> ratings = new LinkedHashMap<>();
        for (ActorInputData actor : input.getActors()) {
            Double rating = getActorRating(actor);
            if (rating != 0) {
                ratings.put(actor.getName(), rating);
            }
        }
        List<String> avgActors = Sort.sortByDouble(ratings, action);
        String result = avgActors.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);


    }
}