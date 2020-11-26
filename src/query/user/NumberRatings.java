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


public class NumberRatings {


    public Integer getUserRating(UserInputData user) {
        int SerialRatings = 0;
        for(List<Integer> seasons : user.getSeasonsRated().values()) {
            SerialRatings += seasons.size();
        }
        return user.getMoviesRated().size() + SerialRatings;
    }

    public void queryNumberRatings(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        Map<String, Integer> numberRatings = new LinkedHashMap<>();
        for (UserInputData user : input.getUsers()) {
            Integer number = getUserRating(user);
            if (number != 0) {
                numberRatings.put(user.getUsername(), number);
            }
        }
        List<String> avgActors = Sort.sortByInteger(numberRatings,action);
        String result = avgActors.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);

    }
}
