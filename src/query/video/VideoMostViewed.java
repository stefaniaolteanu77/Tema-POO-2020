package query.video;

import command.rating.MovieRating;
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

public class VideoMostViewed {
    private Integer getMovieViews(Input input, MovieInputData movie) {
        Integer movieView = 0;
        for (UserInputData user : input.getUsers()) {
            for(String movieTitle : user.getHistory().keySet()) {
                if(movieTitle.equals(movie.getTitle())) {
                    movieView += user.getHistory().get(movieTitle);
                }
            }
        }
        return movieView;
    }

    private Integer getSerialViews(Input input, SerialInputData serial) {
        Integer movieView = 0;
        for (UserInputData user : input.getUsers()) {
            for(String movieTitle : user.getHistory().keySet()) {
                if(movieTitle.equals(serial.getTitle())) {
                    movieView += user.getHistory().get(movieTitle);
                }
            }
        }
        return movieView;
    }
    public void queryMovieMostViewed(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer> viewed = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(input, action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer views = getMovieViews(input, movie);
                viewed.put(movie.getTitle(), views);
            }
            List<String> mostViewdMovies = Sort.sortByInteger(viewed,action);
            String result = mostViewdMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    public void querySerialMostViewd(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> viewed = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(input, action);
        if (serials != null) {
            for (SerialInputData serial : input.getSerials()) {
                Integer views = getSerialViews(input, serial);
                viewed.put(serial.getTitle(), views);
            }
            List<String> mostViewdSerials = Sort.sortByInteger(viewed,action);
            String result = mostViewdSerials.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
}
