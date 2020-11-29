package query.actor;

import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import query.video.VideoRating;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class QueryAverage {
    private final Input input;
    private final ActionInputData action;
    private final WriterHelper writerHelper;

    public QueryAverage(final Input input, final ActionInputData action,
                        final WriterHelper writerHelper) {
        this.input = input;
        this.action = action;
        this.writerHelper = writerHelper;
    }

    /**
     *
     * @param actor the actor for which we need the total rating
     * @return total rating of actor
     */
    private Double getActorRating(final ActorInputData actor) {
        List<String> filmography = actor.getFilmography();
        VideoRating helperRating = new VideoRating();
        int numberOfVideos = 0;
        double sum = 0;
        double rating;
        for (String video : filmography) {
            for (MovieInputData movie : input.getMovies()) {
                if (movie.getTitle().equals(video)) {
                    rating = helperRating.getTotalMovieRating(movie);
                    if (rating != 0) {
                        sum += rating;
                        numberOfVideos++;
                    }
                    break;
                }
            }
            for (SerialInputData serial : input.getSerials()) {
                if (serial.getTitle().equals(video)) {
                    rating = helperRating.getTotalSerialRating(serial);
                    if (rating != 0) {
                        sum += rating;
                        numberOfVideos++;
                    }
                    break;
                }
            }
        }
        if (numberOfVideos == 0) {
            return (double) 0;
        }

        return sum / numberOfVideos;
    }

    /**
     * Puts all actors and their total ratings in a map and sorts it
     * @throws IOException in case the result cannot be written to output
     */
    public void applyQueryAverage() throws IOException {
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
