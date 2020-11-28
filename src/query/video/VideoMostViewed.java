package query.video;

import command.User;
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
                if(!user.getHistory().containsKey(movieTitle)) {
                    movieView = 0;
                    return movieView;
                }
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
            for(String serialTitle : user.getHistory().keySet()) {
                if(!user.getHistory().containsKey(serialTitle)) {
                    return 0;
                }
                if(serialTitle.equals(serial.getTitle())) {
                    movieView += user.getHistory().get(serialTitle);
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
                if(views != 0) {
                    viewed.put(movie.getTitle(), views);
                }
            }
            List<String> mostViewedMovies = Sort.sortByInteger(viewed,action);
            String result = mostViewedMovies.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    public void querySerialMostViewed(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> viewed = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(input, action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                Integer views = getSerialViews(input, serial);
                if(views != 0) {
                    viewed.put(serial.getTitle(), views);
                }
            }
            List<String> mostViewedSerials = Sort.sortByInteger(viewed,action);
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
