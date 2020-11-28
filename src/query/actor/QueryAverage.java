package query.actor;

import fileio.*;
import query.video.VideoRating;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class QueryAverage {
    List<MovieInputData> movies;
    List<SerialInputData> serials;

    public QueryAverage(List<MovieInputData> movies, List<SerialInputData> serials) {
        this.movies = movies;
        this.serials = serials;
    }

    private Double getActorRating(ActorInputData actor) {
        List<String> filmography = actor.getFilmography();
        VideoRating helperRating = new VideoRating();
        int numberOfVideos = 0;
        double sum = 0;
        double rating;
        for (String video : filmography) {
            for (MovieInputData movie : movies) {
                if (movie.getTitle().equals(video)) {
                    rating = helperRating.getTotalMovieRating(movie);
                    if (rating != 0) {
                        sum += rating;
                        numberOfVideos++;
                    }
                    break;
                }
            }
            for (SerialInputData serial : serials) {
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