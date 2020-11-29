package query;

import fileio.ActionInputData;
import fileio.Input;
import query.video.VideoDuration;
import query.video.VideoFavorite;
import query.video.VideoMostViewed;
import query.video.VideoRating;
import utils.WriterHelper;

import java.io.IOException;

public final class MoviesQuery {
    private final WriterHelper writerHelper;
    private final Input input;

    public MoviesQuery(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Applies query on movies based on a criteria
     * @param action the action to be done
     * @throws IOException in case the result cannot pe written to output
     */
    public void applyMoviesQuery(final ActionInputData action) throws IOException {
        switch (action.getCriteria()) {
            case "ratings":
                VideoRating videoRating = new VideoRating();
                videoRating.queryMovieRating(input, action, writerHelper);
                break;
            case "longest":
                VideoDuration videoDuration = new VideoDuration();
                videoDuration.queryMovieDuration(input, action, writerHelper);
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

