package query.actor;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import filters.ActorFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class QueryAwards {

    public void queryAward(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        ActorFilters actorFilters = new ActorFilters(input.getActors());
        List<ActorInputData> filteredActors = actorFilters.filterByAwards(action.getFilters().get(3));
        Map<String, Integer> totalAwards = new LinkedHashMap<>();
        if (filteredActors != null) {
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
        } else {
            String result = "Query result: []";
            writerHelper.addToArrayResult(action, result);
        }
    }
}
