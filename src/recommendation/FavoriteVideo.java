package recommendation;

import fileio.*;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FavoriteVideo {
    private final UserInputData user;

    public FavoriteVideo(UserInputData user) {
        this.user = user;
    }

    private void getMapFavorite(Input input, String title, Map<String, Integer> favoriteVideos) {
        for (UserInputData userInput : input.getUsers()) {
            if (!userInput.getUsername().equals(user.getUsername())) {
                for (String favorite : userInput.getFavoriteMovies()) {
                    if (!user.getHistory().containsKey(favorite) &&
                            title.equals(favorite)) {
                        favoriteVideos.put(favorite, favoriteVideos.
                                getOrDefault(favorite, 0) + 1);
                    }
                }
            }
        }
    }
    public void applyFavoriteVideo(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "SearchRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        Map<String, Integer> favoriteVideos = new LinkedHashMap<>();
        for (MovieInputData movie : input.getMovies()) {
            getMapFavorite(input, movie.getTitle(), favoriteVideos);
        }

        for (SerialInputData serial : input.getSerials()) {
            getMapFavorite(input, serial.getTitle(), favoriteVideos);
        }

        if (favoriteVideos.size() == 0) {
            String result = "FavoriteRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
        } else {
            List<String> sortedList = favoriteVideos.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            String video = sortedList.get(0);
            String result = "FavoriteRecommendation result: " + video;

            writerHelper.addToArrayResult(action, result);
        }
    }
}
