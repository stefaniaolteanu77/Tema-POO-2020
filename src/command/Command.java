package command;

import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.Input;
import utils.WriterHelper;

import java.io.IOException;

public final class Command {
    private final WriterHelper writerHelper;
    private final Input input;

    public Command(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /***
     * Search for user by username
     * @param username the username of the user we are looking for
     * @return the user we are looking for
     */
    public UserInputData getUserFromInput(final String username) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     *  Based on the actionType apply one of the commands
     * @param action the action to be executed
     * @throws IOException function writes the result in file
     */
    public void applyCommand(final ActionInputData action)
            throws IOException {
        if (action.getActionType().equals("command")) {
            UserInputData user = getUserFromInput(action.getUsername());
            if (user != null) {
                switch (action.getType()) {
                    case "favorite":
                        Favorite favorite = new Favorite(writerHelper);
                        favorite.addToFavourite(user, action);
                        break;

                    case "view":
                        View viewed = new View(writerHelper);
                        viewed.addToViewed(user, action);
                        break;

                    case "rating":
                        Rating rating = new Rating(writerHelper, input);
                        rating.addRating(user, action);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
