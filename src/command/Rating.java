package command;
import command.rating.MovieRating;
import command.rating.SeasonRating;
import fileio.*;

import utils.WriterHelper;

import java.io.IOException;


public class Rating {

    private final WriterHelper writerHelper;
    private final Input input;

    public Rating(WriterHelper writerHelper, Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    private String alreadyRatedError(final String title) {
        return "error -> " + title + " has been already rated";
    }
    private String notViewed(final String title) {return "error -> " + title + " is not seen";}
    private String videoRated(final String title, final Double grade, final String name) {
        return "success -> " + title + " was rated with " + grade + " by " + name;
    }

    public void addRating(final UserInputData user, ActionInputData action) throws IOException {
        String title = action.getTitle();
        Double grade = action.getGrade();
        if (!user.getHistory().containsKey(title)) {
            writerHelper.addToArrayResult(action, notViewed(title));
            return;
        }
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getTitle().equals(title)) {
                MovieRating ratingHelp = new MovieRating(movie);
                if (ratingHelp.isMovieRated(user, title)) {
                    writerHelper.addToArrayResult(action, alreadyRatedError(title));
                } else {
                    ratingHelp.addMovieToRated(user, title);
                    ratingHelp.addToRatingList(grade);
                    writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
                }
                return;
            }

        }
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getTitle().equals(title)) {
                Integer season = action.getSeasonNumber();
                SeasonRating ratingHelper = new SeasonRating(serial, season);
                if (ratingHelper.isSeasonRated(user, title)) {
                    writerHelper.addToArrayResult(action, alreadyRatedError(title));
                } else {
                    ratingHelper.addSeasonToRated(user, title);
                    ratingHelper.addSeasonRating(grade);
                    ratingHelper.addSerialRating(user);
                    writerHelper.addToArrayResult(action, videoRated(title, grade, user.getUsername()));
                }
                return;
            }
        }
    }
}
