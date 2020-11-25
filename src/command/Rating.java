package command;
import fileio.*;
import query.MovieRating;
import query.SerialRating;
import query.User;
import utils.Searcher;
import utils.WriterHelper;

import java.io.IOException;


public class Rating extends Command {
    public Rating(WriterHelper writerHelper, Input input) {
        super(writerHelper, input);
    }

    private String alreadyRatedError(final String title) {
        return "error -> " + title + " has been already rated";
    }

    private String videoRated(final String title, final Double grade, final String name) {
        return "success -> " + title + " was rated with " + grade + " by " + name;
    }

    public void addRating(final ActionInputData action) throws IOException {
        String title = action.getTitle();
        Double grade = action.getGrade();
        UserInputData user = User.lookForUserInDataBase(users,action);
        if (user != null && User.lookInHistory(user, title)) {
            Searcher searcher = new Searcher(movies,serials);
           MovieInputData movie = searcher.lookForMovie(title);
            MovieRating ratingHelp = new MovieRating(movie,user);
           if (movie != null) {
               if (ratingHelp.isMovieRated(user,title)) {
                   writerHelper.addToArrayResult(action, alreadyRatedError(title));
               } else {
                   ratingHelp.addMovieToRated(user,title);
                   ratingHelp.addToRatingList(movie, grade);
                   writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
               }
               return;
           }

            SerialInputData serial = searcher.lookForSerial(title);
            SerialRating ratingHelper = new SerialRating(user,serial);
           if (serial != null) {
               Integer season = action.getSeasonNumber();
               if (ratingHelper.isSeasonRated(title, season)) {
                   writerHelper.addToArrayResult(action, alreadyRatedError(title));
               } else {
                   ratingHelper.addSeasonToRated(title, season);
                   ratingHelper.addSeasonRating(season, grade);
                   writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
               }
           }
        }
    }
}
