package recommendation;

import command.rating.MovieRating;
import command.rating.SerialRating;
import fileio.*;
import filters.FilterUnseenVideos;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BestUnseenVideo {
    private final UserInputData user;

    public BestUnseenVideo(UserInputData user) {
        this.user = user;
    }

    public void applyBestUnseen(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        FilterUnseenVideos filter = new FilterUnseenVideos(user);
        Map<String, Double> rating = filter.getUnseenVideos(input.getMovies(),input.getSerials());
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        rating.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        String recommendedVideo;
        if(sortedMap.size() >= 1) {
            recommendedVideo =  String.valueOf(sortedMap.keySet().toArray()[0]);
        } else {
            recommendedVideo = null;
        }

        String result;
        if(recommendedVideo != null) {
            result = "BestRatedUnseenRecommendation result: " + recommendedVideo;
        } else {
            result = "BestRatedUnseenRecommendation cannot be applied!";
        }
        writerHelper.addToArrayResult(action, result);

    }
}
