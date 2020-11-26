package query.video;

import command.rating.SerialRating;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import filters.MovieFilters;
import filters.SerialFilters;
import command.rating.MovieRating;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class VideoRating {

    public void queryMovieRating(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Double> ratings = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(input, action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Double rating = new MovieRating(movie).getTotalMovieRating();
                if (rating != 0) {
                    ratings.put(movie.getTitle(), rating);
                }

            }
            List<String> bestMovies = Sort.sortByDouble(ratings,action);
            String result = bestMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
           String result = "Query result: []";
           helper.addToArrayResult(action, result);
        }
    }

    public void QuerySerialRating(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Double> ratings = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(input, action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Double rating = new SerialRating(serial).getTotalSerialRating();
                if (rating != 0) {
                    ratings.put(serial.getTitle(), rating);
                }
            }
            List<String> bestSerials = Sort.sortByDouble(ratings,action);
            String result = bestSerials.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }


}
