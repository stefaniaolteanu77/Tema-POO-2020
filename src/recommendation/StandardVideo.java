package recommendation;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import utils.WriterHelper;

import java.io.IOException;

public final class StandardVideo {

    private final UserInputData user;

    public StandardVideo(final UserInputData user) {
        this.user = user;
    }

    /**
     * Looks for first unseen video from database
     * @param input  the database
     * @param action the action to be done
     * @param writerHelper needed to write to output
     * @throws IOException in case the result cannot be written to output
     */
    public void applyStandardVideo(final Input input, final ActionInputData action,
                                   final WriterHelper writerHelper) throws IOException {
        String video;
        String result;
        for (MovieInputData movie : input.getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                video = movie.getTitle();
                result = "StandardRecommendation result: " + video;
                writerHelper.addToArrayResult(action, result);
                return;
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                video = serial.getTitle();
                result = "StandardRecommendation result: " + video;
                writerHelper.addToArrayResult(action, result);
                return;
            }
        }
        result = "StandardRecommendation cannot be applied!";
        writerHelper.addToArrayResult(action, result);
    }
}
