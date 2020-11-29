package filters;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import query.video.VideoRating;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FilterUnseenVideos {
    private final UserInputData user;

    public FilterUnseenVideos(final UserInputData user) {
        this.user = user;
    }

    /**
     * Gets all unseen videos by a certain user puts them in a map and sorts
     * the by rating
     * @param movies the movies from database
     * @param serials the serials from database
     * @return list of unseen videos sorted by rating
     */
    public Map<String, Double> getUnseenVideos(final List<MovieInputData> movies,
                                               final List<SerialInputData> serials) {
        Map<String, Double> rating = new LinkedHashMap<>();
        VideoRating helperRating = new VideoRating();
        for (MovieInputData movie : movies) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                Double ratingValue = helperRating.getTotalMovieRating(movie);
                rating.put(movie.getTitle(), ratingValue);
            }
        }
        for (SerialInputData serial : serials) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                Double ratingValue = helperRating.getTotalSerialRating(serial);
                rating.put(serial.getTitle(), ratingValue);
            }
        }
        return rating;
    }
}
