package query.actor;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import filters.ActorFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public final class QueryAwards {
    private final Input input;
    private final ActionInputData action;
    private final WriterHelper writerHelper;

    public QueryAwards(final Input input, final ActionInputData action,
                        final WriterHelper writerHelper) {
        this.input = input;
        this.action = action;
        this.writerHelper = writerHelper;
    }

    /**
     *  Filters the actors by awards and then puts them and their number of awards
     *  in a map and sorts it
     * @throws IOException in case the result cannot be written to output
     */
    public void applyQueryAward() throws IOException {
        ActorFilters actorFilters = new ActorFilters(input.getActors());
        final int awardsPosition = 3;
        List<ActorInputData> filteredActors =
                actorFilters.filterByAwards(action.getFilters().get(awardsPosition));
        Map<String, Integer> totalAwards = new LinkedHashMap<>();
        for (ActorInputData actor : filteredActors) {
            Integer numberAwards = actorFilters.setNumberOfAwards(actor);
            if (numberAwards != 0) {
                totalAwards.put(actor.getName(), numberAwards);
            }
        }
        List<String> awardedActors = Sort.sortByInteger(totalAwards, action);
        String result = awardedActors.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);
    }
}
