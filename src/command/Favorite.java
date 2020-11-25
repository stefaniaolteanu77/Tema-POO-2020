package command;
import query.User;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.Searcher;
import utils.WriterHelper;

import java.io.IOException;
import java.util.stream.Collectors;


public final class Favorite extends Command {

    public Favorite(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }

    private String duplicateErrormessage(final String title) {
        return "error -> " + title + " is already in favourite list";
    }

    private String invalidErrormessage(final String title) {
        return "error -> " + title + " is not seen";
    }

    private String validMessage(final String title) {
        return "success -> " + title + " was added as favourite";
    }


    public void addToFavourite(final ActionInputData action) throws IOException {
        String title = action.getTitle();
        UserInputData user = User.lookForUserInDataBase(users,action);
        if (user != null && User.lookInHistory(user, title)) {
            if (User.lookInFavorite(user, title)) {
                writerHelper.addToArrayResult(action, duplicateErrormessage(title));
            } else {
                user.getFavoriteMovies().add(title);
                writerHelper.addToArrayResult(action, validMessage(title));
            }
        } else {
            writerHelper.addToArrayResult(action, invalidErrormessage(title));
        }
    }
}
