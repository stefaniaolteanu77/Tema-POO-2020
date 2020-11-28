package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import query.video.VideoDuration;
import query.video.VideoFavorite;
import query.video.VideoMostViewed;
import query.video.VideoRating;
import utils.WriterHelper;

import java.io.IOException;

public class SerialsQuery {
    private WriterHelper writerHelper;
    private Input input;

    public SerialsQuery(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    public void applySerialsQuery(final ActionInputData action) throws IOException {
        switch (action.getCriteria()) {
            case "ratings":
                VideoRating videoRating = new VideoRating();
                videoRating.QuerySerialRating(input,action,writerHelper);
                break;
            case "longest":
                VideoDuration videoDuration = new VideoDuration();
                videoDuration.querySerialDuration(input,action,writerHelper);
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
