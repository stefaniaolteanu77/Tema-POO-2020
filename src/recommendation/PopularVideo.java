package recommendation;

import fileio.Input;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class PopularVideo {
    private final UserInputData user;

    public PopularVideo(final UserInputData user) {
        this.user = user;
    }

    /**
     *  Puts all genres and the number of views from each video in the genres
     *  in map and then sorts them by popularity and puts them in a list
     * @param movies all movies from database
     * @param serials all serials from database
     * @return list of genres sorted by popularity
     */
    private List<String> getPopularGenres(final List<MovieInputData> movies,
                                          final List<SerialInputData> serials) {
        Map<String, Integer> popularGenres = new LinkedHashMap<>();
        for (MovieInputData movie : movies) {
            for (String genre : movie.getGenres()) {
                popularGenres.put(genre, popularGenres.getOrDefault(genre, 0) + 1);
            }
        }
        for (SerialInputData serial : serials) {
            for (String genre : serial.getGenres()) {
                popularGenres.put(genre, popularGenres.getOrDefault(genre, 0) + 1);
            }
        }
        return popularGenres.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    /**
     *  Gets first unseen video of a user from the most popular genre
     *  and prints to output
     * @param input  the database
     * @param action the action to be done
     * @param writerHelper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void applyPopularVideo(final Input input, final ActionInputData action,
                                  final WriterHelper writerHelper) throws IOException {
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "PopularRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        List<String> genres = getPopularGenres(input.getMovies(), input.getSerials());
        String popularVideo = null;
        boolean foundVideo = false;
        for (String genre : genres) {
            for (MovieInputData movie : input.getMovies()) {
                if (movie.getGenres().contains(genre)) {
                    if (!user.getHistory().containsKey(movie.getTitle())) {
                        popularVideo = movie.getTitle();
                        foundVideo = true;
                        break;
                    }
                }
            }
            if (foundVideo) {
                break;
            }
            for (SerialInputData serial : input.getSerials()) {
                if (serial.getGenres().contains(genre)) {
                    if (!user.getHistory().containsKey(serial.getTitle())) {
                        popularVideo = serial.getTitle();
                        foundVideo = true;
                        break;
                    }
                }
            }
            if (foundVideo) {
                break;
            }
        }
        String result;
        if (!foundVideo) {
            result = "PopularRecommendation cannot be applied!";
        } else {
            result = "PopularRecommendation result: " + popularVideo;
        }
        writerHelper.addToArrayResult(action, result);
    }

}
