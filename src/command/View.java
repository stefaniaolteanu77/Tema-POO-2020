package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.IOException;
import java.util.Map;

public class View extends Command {

    private String validMessage(final String title, Integer numViews) {
        return "success -> " + title + " was viewed with total views of " + numViews;
    }


    private void addToArrayResult(final ActionInputData action, final Writer writer,
                                  final JSONArray arrayResult, final String message)
            throws IOException {
        JSONObject object = writer.writeFile(action.getActionId(), null, message);
        arrayResult.add(object);
    }

    public void addToViewed(final Input input, final ActionInputData action,
                               final Writer writer, final JSONArray arrayResult) throws IOException {
        String title = action.getTitle();
        UserInputData user = User.lookForUserInDataBase(input,action);
        Map<String, Integer> history = user.getHistory();
        if (User.lookInHistory(user, title)) {
            history.put(title,history.get(title) + 1);
        }
        else {
            history.put(title, 1);
        }
        addToArrayResult(action, writer, arrayResult, validMessage(title, history.get(title)));
    }

}
