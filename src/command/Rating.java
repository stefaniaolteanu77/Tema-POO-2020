package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Rating extends Command {

    private void addToArrayResult(final ActionInputData action, final Writer writer,
                                  final JSONArray arrayResult, final String message)
            throws IOException {
        JSONObject object = writer.writeFile(action.getActionId(), null, message);
        arrayResult.add(object);
    }

    public void addRating(final Input input, final ActionInputData action,
                            final Writer writer, final JSONArray arrayResult) throws IOException {
        String title = action.getTitle();
        UserInputData user = User.lookForUserInDataBase(input,action);
        Map<String, Integer> history = user.getHistory();
        if (User.lookInHistory(user, title)) {
        }
    }
}
