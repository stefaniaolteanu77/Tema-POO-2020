package recommendation;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class FavoriteVideo {
    private final UserInputData user;

    public FavoriteVideo(final UserInputData user) {
        this.user = user;
    }

    /**
     * Builds a map of videos and how popular they are in favorite lists
     * @param users all the users from database
     * @param title title of the movie to be compared to the movies in favorite lists
     * @param favoriteVideos map of video names and how popular they are in the
     *                       favorite lists
     */
    private void getMapFavorite(final List<UserInputData> users,
                                final String title, final Map<String, Integer> favoriteVideos) {
        for (UserInputData userInput : users) {
            if (!userInput.getUsername().equals(user.getUsername())) {
                for (String favorite : userInput.getFavoriteMovies()) {
                    if (!user.getHistory().containsKey(favorite)
                            && title.equals(favorite)) {
                        favoriteVideos.put(favorite, favoriteVideos.
                                getOrDefault(favorite, 0) + 1);
                    }
                }
            }
        }
    }
    /**
     *  Uses a map of videos and the number of times they are in the users'
     *  favorite list to get the most popular video not seen by the user
     *  for which the recommendation is applied
     * @param input  the database
     * @param action the action to be done
     * @param writerHelper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void applyFavoriteVideo(final Input input, final ActionInputData action,
                                   final WriterHelper writerHelper) throws IOException {
        if (!user.getSubscriptionType().equals("PREMIUM")) {
            String result = "SearchRecommendation cannot be applied!";
            writerHelper.addToArrayResult(action, result);
            return;
        }
        Map<String, Integer> favoriteVideos = new LinkedHashMap<>();
        for (MovieInputData movie : input.getMovies()) {
            getMapFavorite(input.getUsers(), movie.getTitle(), favoriteVideos);
        }

        for (SerialInputData serial : input.getSerials()) {
            getMapFavorite(input.getUsers(), serial.getTitle(), favoriteVideos);
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
