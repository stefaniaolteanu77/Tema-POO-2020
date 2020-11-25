package command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import utils.WriterHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoRating {

    public void QueryVideoRating(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        Filters filter = new Filters();
        List<MovieInputData> movies = new ArrayList<>();
        movies = filter.applyFilters(input, action);
        String result = movies.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(",", "{", "}"));
        helper.addToArrayResult(action, result);
    }
}
