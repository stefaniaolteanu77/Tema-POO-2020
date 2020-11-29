package query.video;

import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActionInputData;
import fileio.Input;
import filters.MovieFilters;
import filters.SerialFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class VideoFavorite {

    private Integer getMovieFavorite(final List<UserInputData> users, final MovieInputData movie) {
        Integer movieFavorite = 0;
        for (UserInputData user : users) {
            for (String movieTitle : user.getFavoriteMovies()) {
                if (movieTitle.equals(movie.getTitle())) {
                    movieFavorite++;
                }
            }
        }
        return movieFavorite;
    }

    private Integer getSerialFavorite(final List<UserInputData> users,
                                      final SerialInputData serial) {
        Integer movieFavorite = 0;
        for (UserInputData user : users) {
            for (String movieTitle : user.getFavoriteMovies()) {
                if (movieTitle.equals(serial.getTitle())) {
                    movieFavorite++;
                }
            }
        }
        return movieFavorite;
    }

    /**
     * Puts movies and the number of times they were in the users' favorite lists
     * in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void queryMovieFavorite(final Input input, final ActionInputData action,
                                   final WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer> favorited = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer favorite = getMovieFavorite(input.getUsers(), movie);
                if (favorite != 0) {
                    favorited.put(movie.getTitle(), favorite);
                }
            }
            List<String> favoriteMovies = Sort.sortByInteger(favorited, action);
            String result = favoriteMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    /**
     * Puts serials and the number of times they were in the users' favorite lists
     * in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void querySerialFavorite(final Input input, final ActionInputData action,
                                    final WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> favorited = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Integer favorite = getSerialFavorite(input.getUsers(), serial);
                if (favorite != 0) {
                    favorited.put(serial.getTitle(), favorite);
                }
            }
            List<String> favoriteMovies = Sort.sortByInteger(favorited, action);
            String result = favoriteMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
}
