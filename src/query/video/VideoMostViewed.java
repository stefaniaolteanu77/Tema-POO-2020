package query.video;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import filters.MovieFilters;
import filters.SerialFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class VideoMostViewed {
    private Integer getMovieViews(final List<UserInputData> users, final MovieInputData movie) {
        Integer movieView = 0;
        for (UserInputData user : users) {
            for (String movieTitle : user.getHistory().keySet()) {
                if (!user.getHistory().containsKey(movieTitle)) {
                    movieView = 0;
                    return movieView;
                }
                if (movieTitle.equals(movie.getTitle())) {
                    movieView += user.getHistory().get(movieTitle);
                }
            }
        }
        return movieView;
    }

    private Integer getSerialViews(final List<UserInputData> users, final SerialInputData serial) {
        Integer movieView = 0;
        for (UserInputData user : users) {
            for (String serialTitle : user.getHistory().keySet()) {
                if (!user.getHistory().containsKey(serialTitle)) {
                    return 0;
                }
                if (serialTitle.equals(serial.getTitle())) {
                    movieView += user.getHistory().get(serialTitle);
                }
            }
        }
        return movieView;
    }
    /**
     * Puts movies and their total number of views in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void queryMovieMostViewed(final Input input, final ActionInputData action,
                                     final WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer> viewed = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer views = getMovieViews(input.getUsers(), movie);
                if (views != 0) {
                    viewed.put(movie.getTitle(), views);
                }
            }
            List<String> mostViewedMovies = Sort.sortByInteger(viewed, action);
            String result = mostViewedMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
    /**
     * Puts serials and their total number of views in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void querySerialMostViewed(final Input input, final ActionInputData action,
                                      final WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> viewed = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Integer views = getSerialViews(input.getUsers(), serial);
                if (views != 0) {
                    viewed.put(serial.getTitle(), views);
                }
            }
            List<String> mostViewedSerials = Sort.sortByInteger(viewed, action);
            String result = mostViewedSerials.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
}
