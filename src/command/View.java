package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Map;

public class View {

    private final WriterHelper writerHelper;

    public View(WriterHelper writerHelper) {
        this.writerHelper = writerHelper;
    }

    private String validMessage(final String title, Integer numViews) {
        return "success -> " + title + " was viewed with total views of " + numViews;
    }

    public void addToViewed(final UserInputData user, final ActionInputData action) throws IOException {
        String title = action.getTitle();
        Map<String, Integer> history = user.getHistory();
        history.put(title, history.getOrDefault(title,0) + 1);
        writerHelper.addToArrayResult(action, validMessage(title, history.get(title)));

    }

}
