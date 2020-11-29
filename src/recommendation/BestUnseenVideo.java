package recommendation;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import filters.FilterUnseenVideos;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BestUnseenVideo {
    private final UserInputData user;

    public BestUnseenVideo(final UserInputData user) {
        this.user = user;
    }

  /**
   * Applies filter to get all unseen videos with their rating in a map * and then sorts them and
   * gets the first one printing it to output
   * @param input the database
   * @param action the action to be done
   * @param writerHelper needed to write to output
   * @throws IOException in case the result cannot be written to output
   */
  public void applyBestUnseen(
      final Input input, final ActionInputData action, final WriterHelper writerHelper)
      throws IOException {
        FilterUnseenVideos filter = new FilterUnseenVideos(user);
        Map<String, Double> rating = filter.getUnseenVideos(input.getMovies(), input.getSerials());
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        rating.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        String recommendedVideo;
        if (sortedMap.size() >= 1) {
            recommendedVideo = String.valueOf(sortedMap.keySet().toArray()[0]);
        } else {
            recommendedVideo = null;
        }

        String result;
        if (recommendedVideo != null) {
            result = "BestRatedUnseenRecommendation result: " + recommendedVideo;
        } else {
            result = "BestRatedUnseenRecommendation cannot be applied!";
        }
        writerHelper.addToArrayResult(action, result);

    }
}
