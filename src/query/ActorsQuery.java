package query;

import fileio.ActionInputData;
import fileio.Input;
import query.actor.QueryAverage;
import query.actor.QueryAwards;
import query.actor.QueryDescription;
import utils.WriterHelper;

import java.io.IOException;

public class ActorsQuery extends Query{
    public ActorsQuery(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }

    public void applyActorsQuery(final ActionInputData action, final Input input) throws IOException {
        switch (action.getCriteria()) {
            case "average":
                QueryAverage queryAverage = new QueryAverage(input.getMovies(),input.getSerials());
                queryAverage.queryAverage(input, action, writerHelper);
                break;
            case "filter_description":
                QueryDescription qD = new QueryDescription();
                qD.queryDescription(input,action,writerHelper);
                break;
            case "awards":
                QueryAwards queryAwards = new QueryAwards();
                queryAwards.queryAward(input, action, writerHelper);
                break;
            default:
                break;
        }
    }
}
