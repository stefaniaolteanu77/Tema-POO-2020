package query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import query.actor.QueryAverage;
import query.actor.QueryAwards;
import query.actor.QueryDescription;
import utils.WriterHelper;

import java.io.IOException;

public class ActorsQuery {
    private WriterHelper writerHelper;
    private Input input;

    public ActorsQuery(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    public void applyActorsQuery(final ActionInputData action) throws IOException {
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
