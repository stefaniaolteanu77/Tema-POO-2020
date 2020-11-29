package command;

import fileio.ActionInputData;
import fileio.UserInputData;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Map;

public final class View {

    private final WriterHelper writerHelper;

    public View(final WriterHelper writerHelper) {
        this.writerHelper = writerHelper;
    }

    private String validMessage(final String title, final Integer numViews) {
        return "success -> " + title + " was viewed with total views of " + numViews;
    }

    /**
     * Adds a video to user's history if it wasn't viewed before
     * or increases the number of views if it was seen
     * @param user the user who views the video
     * @param action the action done on the video
     * @throws IOException in case the result cannot be printed to output
     */
    public void addToViewed(final UserInputData user,
                            final ActionInputData action) throws IOException {
        String title = action.getTitle();
        Map<String, Integer> history = user.getHistory();
        history.put(title, history.getOrDefault(title, 0) + 1);
        writerHelper.addToArrayResult(action, validMessage(title, history.get(title)));

    }

}
