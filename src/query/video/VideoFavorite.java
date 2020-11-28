package query.video;

import fileio.*;
import filters.MovieFilters;
import filters.SerialFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VideoFavorite {

    private Integer getMovieFavorite(Input input, MovieInputData movie) {
        Integer movieFavorite = 0;
        for (UserInputData user : input.getUsers()) {
            for(String movieTitle : user.getFavoriteMovies()) {
                if(movieTitle.equals(movie.getTitle()))
                    movieFavorite++;
            }
        }
        return movieFavorite;
    }

    private Integer getSerialFavorite(Input input, SerialInputData serial) {
        Integer movieFavorite = 0;
        for (UserInputData user : input.getUsers()) {
            for(String movieTitle : user.getFavoriteMovies()) {
                if(movieTitle.equals(serial.getTitle()))
                    movieFavorite++;
            }
        }
        return movieFavorite;
    }

    public void queryMovieFavorite(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer>  favorited = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(input, action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer favorite = getMovieFavorite(input, movie);
                if(favorite != 0) {
                    favorited.put(movie.getTitle(), favorite);
                }
            }
            List<String> favoriteMovies = Sort.sortByInteger(favorited,action);
            String result = favoriteMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    public void querySerialFavorite(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer>  favorited = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(input, action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Integer favorite = getSerialFavorite(input, serial);
                if (favorite != 0) {
                    favorited.put(serial.getTitle(), favorite);
                }
            }
            List<String> favoriteMovies = Sort.sortByInteger(favorited,action);
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
