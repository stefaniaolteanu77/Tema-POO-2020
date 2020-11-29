package query.user;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public final class NumberRatings {
    private final WriterHelper writerHelper;
    private final Input input;

    public NumberRatings(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Finds the number of videos rated by a user
     * @param user the user for which we want to find the
     *             number of videos rated
     * @return number of videos rated
     */
    public Integer getUserRating(final UserInputData user) {
        int serialRatings = 0;
        for (List<Integer> seasons : user.getSeasonsRated().values()) {
            serialRatings += seasons.size();
        }
        return user.getMoviesRated().size() + serialRatings;
    }

    /**
     * Forms a list of the users sorted by the number of ratings they did
     * @param action the action to be done
     * @throws IOException in case the result cannot be written to output
     */
    public void applyQueryNumberRatings(final ActionInputData action) throws IOException {
        Map<String, Integer> numberRatings = new LinkedHashMap<>();
        for (UserInputData user : input.getUsers()) {
            Integer number = getUserRating(user);
            if (number != 0) {
                numberRatings.put(user.getUsername(), number);
            }
        }
        List<String> avgActors = Sort.sortByInteger(numberRatings, action);
        String result = avgActors.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);

    }
}
