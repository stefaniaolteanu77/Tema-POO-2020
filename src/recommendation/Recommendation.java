package recommendation;
import fileio.*;
import command.User;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;

public class Recommendation {
    public WriterHelper writerHelper;
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> serials;
    public Recommendation(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.users = input.getUsers();
        this.movies = input.getMovies();
        this.serials = input.getSerials();
    }

    public void applyRecommendation(final ActionInputData action, final Input input)
            throws IOException {
        if (action.getActionType().equals("recommendation")) {
            switch (action.getType()) {
                case "standard":
                    UserInputData user = User.lookForUserInDataBase(input.getUsers(), action);
                    String title = User.standard(user, input);
                    String message = "StandardRecommendation result: " + title;
                    writerHelper.addToArrayResult(action,message);
                    break;
                default:
                    break;

            }
        }
    }
}
