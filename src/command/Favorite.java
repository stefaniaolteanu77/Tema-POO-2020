package command;
import user.User;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;


public class Favorite extends Command {

    private String duplicateErrormessage(final String title) {
        return "error -> " + title + " is already in favourite list";
    }

    private String invalidErrormessage(final String title) {
        return "error -> " + title + " is not seen";
    }

    private String validMessage(final String title) {
        return "success -> " + title + " was added as favourite";
    }

    private void addToArrayResult(final ActionInputData action, final Writer writer,
                                 final JSONArray arrayResult, final String message)
            throws IOException {
        JSONObject object = writer.writeFile(action.getActionId(), null, message);
        arrayResult.add(object);
    }

    public void addToFavourite(final Input input, final ActionInputData action,
                   final Writer writer, final JSONArray arrayResult) throws IOException {
        String title = action.getTitle();
        UserInputData user = User.lookForUserInDataBase(input,action);
        if (User.lookInHistory(user, title)) {
            if (User.lookInFavorite(user, title)) {
                addToArrayResult(action, writer, arrayResult, duplicateErrormessage(title));
            } else {
                user.getFavoriteMovies().add(title);
                addToArrayResult(action, writer, arrayResult, validMessage(title));
            }
        } else {
            addToArrayResult(action, writer, arrayResult, invalidErrormessage(title));
        }
    }
}
