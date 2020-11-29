package query;

import fileio.Input;
import fileio.ActionInputData;
import query.user.NumberRatings;
import utils.WriterHelper;

import java.io.IOException;

public final class Query {
    private final WriterHelper writerHelper;
    private final Input input;

    public Query(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Applies query based on object type
     * @param action the action to be done
     * @throws IOException in case the result cannot pe written to output
     */
    public void applyQuery(final ActionInputData action)
            throws IOException {
        if (action.getActionType().equals("query")) {
            switch (action.getObjectType()) {
                case "actors":
                    ActorsQuery actorsQuery = new ActorsQuery(writerHelper, input);
                    actorsQuery.applyActorsQuery(action);
                    break;
                case "movies":
                    MoviesQuery moviesQuery = new MoviesQuery(writerHelper, input);
                    moviesQuery.applyMoviesQuery(action);
                    break;
                case "shows":
                    SerialsQuery serialsQuery = new SerialsQuery(writerHelper, input);
                    serialsQuery.applySerialsQuery(action);
                    break;
                case "users":
                    NumberRatings numberRatings = new NumberRatings(writerHelper, input);
                    numberRatings.applyQueryNumberRatings(action);
                default:
                    break;

            }
        }
    }
}
