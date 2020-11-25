package query;
import entertainment.Season;
import fileio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class User {
    private User() {

    }
    public static boolean lookInHistory(final UserInputData user, final String title) {
        Map<String, Integer> history = user.getHistory();
        return history.containsKey(title);
    }
    public static boolean lookInFavorite(final UserInputData user, final String title) {
        return user.getFavoriteMovies().contains(title);
    }
    public static UserInputData lookForUserInDataBase(List<UserInputData> users, ActionInputData action) {
        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                return user;
            }
        }
        return null;
    }



    public static String standard(final UserInputData user, final Input input) {
        List<MovieInputData> databaseMovies = input.getMovies();
        List<SerialInputData> databaseSerial = input.getSerials();
        for (MovieInputData movie : databaseMovies) {
            if(!lookInHistory(user, movie.getTitle()))
                return movie.getTitle();
        }
        for (SerialInputData serial : databaseSerial) {
            if(!lookInHistory(user, serial.getTitle()))
                return serial.getTitle();
        }
        return null;
    }
}
