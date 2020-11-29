package query;

import fileio.ActionInputData;
import fileio.Input;
import query.video.VideoDuration;
import query.video.VideoFavorite;
import query.video.VideoMostViewed;
import query.video.VideoRating;
import utils.WriterHelper;

import java.io.IOException;

public final class SerialsQuery {
    private final WriterHelper writerHelper;
    private final Input input;

    public SerialsQuery(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Applies query on serials based on a criteria
     * @param action the action to be done
     * @throws IOException in case the result cannot pe written to output
     */
    public void applySerialsQuery(final ActionInputData action) throws IOException {
        switch (action.getCriteria()) {
            case "ratings":
                VideoRating videoRating = new VideoRating();
                videoRating.querySerialRating(input, action, writerHelper);
                break;
            case "longest":
                VideoDuration videoDuration = new VideoDuration();
                videoDuration.querySerialDuration(input, action, writerHelper);
                break;
            case "most_viewed":
                VideoMostViewed videoMostViewed = new VideoMostViewed();
                videoMostViewed.querySerialMostViewed(input, action, writerHelper);
                break;
            case "favorite":
                VideoFavorite videoFavorite = new VideoFavorite();
                videoFavorite.querySerialFavorite(input, action, writerHelper);
            default:
                break;
        }
    }
}
