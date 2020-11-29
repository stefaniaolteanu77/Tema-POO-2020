package query.video;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import filters.MovieFilters;
import filters.SerialFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class VideoRating {
    /**
     *
     * @param movie the movie we want the total rating from
     * @return total rating of a movie
     */
    public Double getTotalMovieRating(final MovieInputData movie) {
        List<Double> ratings = movie.getRatings();
        if (ratings.isEmpty()) {
            return (double) 0;
        }
        double sum = 0;
        for (Double rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }
    /**
     *
     * @param serial the serial we want the total rating from
     * @return total rating of a serial
     */
    public Double getTotalSerialRating(final SerialInputData serial) {
        Map<String, Double> serialRating = serial.getSerialRating();
        if (serialRating.isEmpty()) {
            return (double) 0;
        }
        double sum = 0;
        for (Double rating : serialRating.values()) {
            sum += rating;
        }
        return sum / serialRating.size();
    }
    /**
     * Puts movies and their total rating in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void queryMovieRating(final Input input, final ActionInputData action,
                                 final WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Double> ratings = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Double rating = getTotalMovieRating(movie);
                if (rating != 0) {
                    ratings.put(movie.getTitle(), rating);
                }

            }
            List<String> bestMovies = Sort.sortByDouble(ratings, action);
            String result = bestMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
    /**
     * Puts serials and their total rating in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void querySerialRating(final Input input, final ActionInputData action,
                                  final WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Double> ratings = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Double rating = getTotalSerialRating(serial);
                if (rating != 0) {
                    ratings.put(serial.getTitle(), rating);
                }
            }
            List<String> bestSerials = Sort.sortByDouble(ratings, action);
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
