package recommendation;

import fileio.*;
import utils.WriterHelper;

import java.io.IOException;

public class StandardVideo {

    private final UserInputData user;

    public StandardVideo(UserInputData user) {
        this.user = user;
    }


    public void applyStandardVideo(final Input input, final ActionInputData action,
                              final WriterHelper writerHelper) throws IOException {
        String video;
        String result;
        for (MovieInputData movie : input.getMovies()) {
            if(!user.getHistory().containsKey(movie.getTitle())) {
                video = movie.getTitle();
                result = "StandardRecommendation result: " + video;
                writerHelper.addToArrayResult(action, result);
                return;
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if(!user.getHistory().containsKey(serial.getTitle())) {
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
