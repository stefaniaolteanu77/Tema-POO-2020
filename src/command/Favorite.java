package command;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.WriterHelper;

import java.io.IOException;


public final class Favorite {

    private WriterHelper writerHelper;
    private Input input;

    public Favorite(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
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


    public void addToFavourite(final UserInputData user, final ActionInputData action) throws IOException {
        String title = action.getTitle();
        if (user.getHistory().containsKey(title)) {
            if (user.getFavoriteMovies().contains(title)) {
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
