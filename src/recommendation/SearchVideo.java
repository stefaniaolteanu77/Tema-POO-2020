package recommendation;


import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import filters.FilterUnseenVideos;

import utils.WriterHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


public final class SearchVideo {
    private final UserInputData user;

    public SearchVideo(final UserInputData user) {
        this.user = user;
    }

    /**
     * Filters the videos by genre and then gets a list of all unseen videos
     * from that genre sorted by rating and then name
     * @param input  the database
     * @param action the action to be done
     * @param writerHelper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
  public void applySearchVideo(
      final Input input, final ActionInputData action, final WriterHelper writerHelper)
      throws IOException {
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "SearchRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        List<MovieInputData> filteredMovies = new ArrayList<>();
        List<SerialInputData> filteredSerials = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getGenres().contains(action.getGenre())) {
                filteredMovies.add(movie);
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getGenres().contains(action.getGenre())) {
                filteredSerials.add(serial);
            }
        }
        FilterUnseenVideos filter = new FilterUnseenVideos(user);
        Map<String, Double> filteredMap = filter.getUnseenVideos(filteredMovies, filteredSerials);
        if (filteredMap.size() == 0) {
            String result = "SearchRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
        } else {
            Map<String, Double> sortedMap = new LinkedHashMap<>();
            filteredMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().
                            thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));


            List<String> sortedList = new ArrayList<>(sortedMap.keySet());
            String result = "SearchRecommendation result: " + sortedList;
            writerHelper.addToArrayResult(action, result);
        }
    }
}
