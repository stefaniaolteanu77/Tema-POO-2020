package recommendation;


import fileio.*;
import filters.FilterUnseenVideos;

import utils.WriterHelper;

import java.io.IOException;
import java.util.*;


public class SearchVideo {
    private final UserInputData user;

    public SearchVideo(UserInputData user) {
        this.user = user;
    }

    public void applySearchVideo(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        if(!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "SearchRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        List<MovieInputData> filteredMovies = new ArrayList<>();
        List<SerialInputData> filteredSerials = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            if(movie.getGenres().contains(action.getGenre())) {
                filteredMovies.add(movie);
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if(serial.getGenres().contains(action.getGenre())) {
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
                .sorted(Map.Entry.<String, Double>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));


            List<String> sortedList = new ArrayList<>(sortedMap.keySet());
            String result = "SearchRecommendation result: " + sortedList;
            writerHelper.addToArrayResult(action, result);
        }
    }
}
