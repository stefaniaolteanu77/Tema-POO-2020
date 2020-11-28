package recommendation;

import fileio.*;
import filters.FilterUnseenVideos;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PopularVideo {
    private final UserInputData user;

    public PopularVideo(UserInputData user) {
        this.user = user;
    }

    private List<String> getPopularGenres(Input input) {
        Map<String,Integer> popularGenres = new LinkedHashMap<>();
        for (MovieInputData movie : input.getMovies()) {
            for(String genre : movie.getGenres()){
                popularGenres.put(genre, popularGenres.getOrDefault(genre, 0) + 1);
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            for(String genre : serial.getGenres()){
                popularGenres.put(genre, popularGenres.getOrDefault(genre, 0) + 1);
            }
        }
        return popularGenres.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    public void applyPopularVideo(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        if(!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "PopularRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        List<String> genres = getPopularGenres(input);
        String popularVideo = null;
        boolean foundVideo = false;
        for (String genre : genres) {
            for (MovieInputData movie : input.getMovies()) {
                if (movie.getGenres().contains(genre)) {
                    if(!user.getHistory().containsKey(movie.getTitle())) {
                        popularVideo = movie.getTitle();
                        foundVideo = true;
                        break;
                    }
                }
            }
            if(foundVideo) {
                break;
            }
            for (SerialInputData serial : input.getSerials()) {
                if(serial.getGenres().contains(genre)) {
                    if(!user.getHistory().containsKey(serial.getTitle())) {
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
