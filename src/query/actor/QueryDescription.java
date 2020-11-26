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

public class QueryDescription {

    public void queryDescription(Input input, ActionInputData action, WriterHelper writerHelper) throws IOException {
        ActorFilters actorFilters = new ActorFilters(input.getActors());
        List<ActorInputData> filteredActors = actorFilters.filterByDescription(action.getFilters().get(2));
        List<ActorInputData> sortedList = filteredActors.stream().
                sorted(Comparator.comparing(ActorInputData::getName))
                .collect(Collectors.toList());
        List<String> field1List = sortedList.stream().map(ActorInputData::getName).collect(Collectors.toList());
        String result = field1List.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "Query result: [", "]"));
        writerHelper.addToArrayResult(action, result);
    }
}
