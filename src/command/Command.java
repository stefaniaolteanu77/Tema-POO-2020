package command;

import fileio.*;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;

public class Command {
    private WriterHelper writerHelper;
    private Input input;

    public Command(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }
    public UserInputData getUserFromInput (String username) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public void applyCommand(final ActionInputData action)
            throws IOException {
        if (action.getActionType().equals("command")) {
            UserInputData user = getUserFromInput(action.getUsername());
            switch (action.getType()) {
                case "favorite":
                    Favorite favorite = new Favorite(writerHelper, input);
                    favorite.addToFavourite(user,action);
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
