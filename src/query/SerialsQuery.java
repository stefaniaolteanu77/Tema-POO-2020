package query;

import fileio.ActionInputData;
import fileio.Input;
import query.video.VideoDuration;
import query.video.VideoFavorite;
import query.video.VideoMostViewed;
import query.video.VideoRating;
import utils.WriterHelper;

import java.io.IOException;

public class SerialsQuery extends Query{
    public SerialsQuery(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }
    public void applySerialsQuery(final ActionInputData action, final Input input) throws IOException {
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
                videoMostViewed.querySerialMostViewd(input, action, writerHelper);
            case "favorite":
                VideoFavorite videoFavorite = new VideoFavorite();
                videoFavorite.querySerialFavorite(input, action, writerHelper);
            default:
                break;
        }
    }
}
