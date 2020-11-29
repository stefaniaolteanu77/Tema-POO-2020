package query.video;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
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

public final class VideoDuration {
    /**
     * Puts movies and their total duration in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void queryMovieDuration(final Input input, final ActionInputData action,
                                   final WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer> durations = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer duration = movie.getDuration();
                durations.put(movie.getTitle(), duration);
            }
            List<String> longestMovie = Sort.sortByInteger(durations, action);
            String result = longestMovie.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    /**
     * Puts serials and their total duration in a map and sorts it
     * @param input  the database
     * @param action the action to be done
     * @param helper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void querySerialDuration(final Input input, final ActionInputData action,
                                    final WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> durations = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                int totalDuration = 0;
                for (Season season : serial.getSeasons()) {
                    totalDuration += season.getDuration();
                }
                if (totalDuration != 0) {
                    durations.put(serial.getTitle(), totalDuration);
                }
            }
            List<String> longestMovie = Sort.sortByInteger(durations, action);
            String result = longestMovie.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
}


