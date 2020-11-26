package query;

import fileio.ActionInputData;
import fileio.Input;
import query.video.VideoDuration;
import query.video.VideoFavorite;
import query.video.VideoMostViewed;
import query.video.VideoRating;
import utils.WriterHelper;

import java.io.IOException;

public class MoviesQuery extends Query{

    public MoviesQuery(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }
    public void applyMoviesQuery(final ActionInputData action, final Input input) throws IOException {
        switch (action.getCriteria()) {
            case "ratings":
                VideoRating videoRating = new VideoRating();
                videoRating.queryMovieRating(input,action,writerHelper);
                break;
            case "longest":
                VideoDuration videoDuration = new VideoDuration();
                videoDuration.queryMovieDuration(input,action,writerHelper);
                break;
            case "most_viewed":
                VideoMostViewed videoMostViewed = new VideoMostViewed();
                videoMostViewed.queryMovieMostViewed(input, action, writerHelper);
                break;
            case "favorite":
                VideoFavorite videoFavorite = new VideoFavorite();
                videoFavorite.queryMovieFavorite(input, action, writerHelper);
                break;
            default:
                break;
        }
    }
}

