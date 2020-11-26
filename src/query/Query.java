package query;

import fileio.*;
import query.user.NumberRatings;
import utils.WriterHelper;

import java.io.IOException;
import java.util.List;

public class Query {
    public WriterHelper writerHelper;
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> serials;
    public Query(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.users = input.getUsers();
        this.movies = input.getMovies();
        this.serials = input.getSerials();
    }
    public void applyQuery(final ActionInputData action, final Input input)
            throws IOException {
        if (action.getActionType().equals("query")) {
            switch (action.getObjectType()) {
                case "actors":
                    ActorsQuery actorsQuery = new ActorsQuery(writerHelper,input);
                    actorsQuery.applyActorsQuery(action, input);
                    break;
                case "movies":
                    MoviesQuery moviesQuery = new MoviesQuery(writerHelper, input);
                    moviesQuery.applyMoviesQuery(action, input);
                    break;
                case "shows":
                    SerialsQuery serialsQuery = new SerialsQuery(writerHelper,input);
                    serialsQuery.applySerialsQuery(action, input);
                    break;
                case "users":
                    NumberRatings numberRatings = new NumberRatings();
                    numberRatings.queryNumberRatings(input,action,writerHelper);
                default:
                    break;

            }
        }
    }
}
