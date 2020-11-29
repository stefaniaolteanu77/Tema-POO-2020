package query.actor;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import filters.ActorFilters;
import utils.WriterHelper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class QueryDescription {
    private final Input input;
    private final ActionInputData action;
    private final WriterHelper writerHelper;

    public QueryDescription(final Input input, final ActionInputData action,
                       final WriterHelper writerHelper) {
        this.input = input;
        this.action = action;
        this.writerHelper = writerHelper;
    }

    /**
     *  Filters the actors by description and then puts them
     *  in a map and sorts them by name
     * @throws IOException in case the result cannot be written to output
     */
    public void applyQueryDescription() throws IOException {
        ActorFilters actorFilters = new ActorFilters(input.getActors());
        List<ActorInputData> filteredActors =
                actorFilters.filterByDescription(action.getFilters().get(2));
        List<ActorInputData> sortedList;
        if (action.getSortType().equals("asc")) {
            sortedList = filteredActors.stream().
                    sorted(Comparator.comparing(ActorInputData::getName))
                    .collect(Collectors.toList());
        } else {
            sortedList = filteredActors.stream().
                    sorted(Comparator.comparing(ActorInputData::getName).reversed())
                    .collect(Collectors.toList());
        }
        List<String> field1List = sortedList.stream().
                map(ActorInputData::getName).
                collect(Collectors.toList());
        String result = field1List.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);
    }
}
