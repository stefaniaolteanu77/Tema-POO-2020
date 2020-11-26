package command;
import command.rating.MovieRating;
import command.rating.SeasonRating;
import fileio.*;
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
    private String notViewed(final String title) {return "error -> " + title + " is not seen";}
    private String videoRated(final String title, final Double grade, final String name) {
        return "success -> " + title + " was rated with " + grade + " by " + name;
    }

    public void addRating(final ActionInputData action) throws IOException {
        String title = action.getTitle();
        Double grade = action.getGrade();
        UserInputData user = User.lookForUserInDataBase(users,action);
        if (!User.lookInHistory(user, title)){
            writerHelper.addToArrayResult(action, notViewed(title));
            return;
        }
        if (user != null && User.lookInHistory(user, title)) {
            Searcher searcher = new Searcher(movies,serials);
           MovieInputData movie = searcher.lookForMovie(title);
            MovieRating ratingHelp = new MovieRating(movie);
           if (movie != null) {
               if (ratingHelp.isMovieRated(user,title)) {
                   writerHelper.addToArrayResult(action, alreadyRatedError(title));
               } else {
                   ratingHelp.addMovieToRated(user,title);
                   ratingHelp.addToRatingList(grade);
                   writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
               }
               return;
           }

            SerialInputData serial = searcher.lookForSerial(title);
           if (serial != null) {
               Integer season = action.getSeasonNumber();
               SeasonRating ratingHelper = new SeasonRating(serial,season);
               if (ratingHelper.isSeasonRated(user,title)) {
                   writerHelper.addToArrayResult(action, alreadyRatedError(title));
               } else {
                   ratingHelper.addSeasonToRated(user,title);
                   ratingHelper.addSeasonRating(grade);
                   writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
               }
           }
        }
    }
}
