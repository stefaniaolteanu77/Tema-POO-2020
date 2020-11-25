package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import query.User;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Map;

public class View extends Command {

    public View(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }

    private String validMessage(final String title, Integer numViews) {
        return "success -> " + title + " was viewed with total views of " + numViews;
    }


    public void addToViewed(final ActionInputData action) throws IOException {
        String title = action.getTitle();
        UserInputData user = User.lookForUserInDataBase(users,action);
        if(user != null) {
            Map<String, Integer> history = user.getHistory();
            if (User.lookInHistory(user, title)) {
                history.put(title, history.get(title) + 1);
            } else {
                history.put(title, 1);
            }
            writerHelper.addToArrayResult(action, validMessage(title, history.get(title)));
        }
    }

}
