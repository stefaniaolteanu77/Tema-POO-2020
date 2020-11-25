package command;

import fileio.*;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;

public class Command {
    public WriterHelper writerHelper;
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> serials;
    public Command(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.users = input.getUsers();
        this.movies = input.getMovies();
        this.serials = input.getSerials();
    }

    public void applyCommand(final ActionInputData action, final Input input)
            throws IOException {
        if (action.getActionType().equals("command")) {
            switch (action.getType()) {
                case "favorite":
                    Favorite favorite = new Favorite(writerHelper, input);
                    favorite.addToFavourite(action);
                    break;

                case "view":
                    View viewed = new View(writerHelper, input);
                    viewed.addToViewed(action);
                    break;

                case "rating":
                    Rating rating = new Rating(writerHelper, input);
                    rating.addRating(action);
                    break;
                default:
                    break;

            }
        }
    }
}
