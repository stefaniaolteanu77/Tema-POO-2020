package filters;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import query.video.VideoRating;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterUnseenVideos {
    private final UserInputData user;

    public FilterUnseenVideos(UserInputData user) {
        this.user = user;
    }

    public Map<String, Double> getUnseenVideos(List<MovieInputData> movies, List<SerialInputData> serials) {
        Map<String, Double> rating = new LinkedHashMap<>();
        VideoRating helperRating = new VideoRating();
        for (MovieInputData movie : movies) {
            if(!user.getHistory().containsKey(movie.getTitle())) {
                Double ratingValue = helperRating.getTotalMovieRating(movie);
                rating.put(movie.getTitle(), ratingValue);
            }
        }
        for (SerialInputData serial : serials) {
            if(!user.getHistory().containsKey(serial.getTitle())) {
                Double ratingValue = helperRating.getTotalSerialRating(serial);
                rating.put(serial.getTitle(), ratingValue);
            }
        }
        return rating;
    }
}
