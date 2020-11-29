package query;

import fileio.ActionInputData;
import fileio.Input;
import query.actor.QueryAverage;
import query.actor.QueryAwards;
import query.actor.QueryDescription;
import utils.WriterHelper;

import java.io.IOException;

public final class ActorsQuery {
    private final WriterHelper writerHelper;
    private final Input input;

    public ActorsQuery(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Applies query on actors based on a criteria
     * @param action the action to be done
     * @throws IOException in case the result cannot pe written to output
     */
    public void applyActorsQuery(final ActionInputData action) throws IOException {
        switch (action.getCriteria()) {
            case "average":
                QueryAverage queryAverage = new QueryAverage(input, action, writerHelper);
                queryAverage.applyQueryAverage();
                break;
            case "filter_description":
                QueryDescription queryDescription = new QueryDescription(input,
                                                        action, writerHelper);
                queryDescription.applyQueryDescription();
                break;
            case "awards":
                QueryAwards queryAwards = new QueryAwards(input, action, writerHelper);
                queryAwards.applyQueryAward();
                break;
            default:
                break;
        }
    }
}
