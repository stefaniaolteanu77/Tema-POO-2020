package command;

import command.rating.MovieRating;
import command.rating.SerialRating;
import fileio.Input;
import fileio.UserInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.ActionInputData;

import utils.WriterHelper;

import java.io.IOException;


public final class Rating {

    private final WriterHelper writerHelper;
    private final Input input;

    public Rating(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    private String alreadyRatedError(final String title) {
        return "error -> " + title + " has been already rated";
    }

    private String notViewed(final String title) {
        return "error -> " + title + " is not seen";
    }

    private String videoRated(final String title, final Double grade, final String name) {
        return "success -> " + title + " was rated with " + grade + " by " + name;
    }

    /**
     *  Adds rating to a movie or season of a serial to the user's
     * database as well as globally
     * @param user the user who rates the video
     * @param action the type of action done
     * @throws IOException in case the result cannot be printed to output
     */
    public void addRating(final UserInputData user,
                          final ActionInputData action) throws IOException {
        String title = action.getTitle();
        Double grade = action.getGrade();
        if (!user.getHistory().containsKey(title)) {
            writerHelper.addToArrayResult(action, notViewed(title));
            return;
        }
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getTitle().equals(title)) {
                MovieRating ratingHelper = new MovieRating(movie, user);
                if (ratingHelper.isMovieRated(title)) {
                    writerHelper.addToArrayResult(action, alreadyRatedError(title));
                } else {
                    ratingHelper.addMovieToRated(title);
                    ratingHelper.addToRatingList(grade);
                    writerHelper.addToArrayResult(action, videoRated(title,
                                                grade, user.getUsername()));
                }
                return;
            }

        }
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getTitle().equals(title)) {
                Integer season = action.getSeasonNumber();
                SerialRating ratingHelper = new SerialRating(serial, season, user);
                if (ratingHelper.isSeasonRated()) {
                    writerHelper.addToArrayResult(action, alreadyRatedError(title));
                } else {
                    ratingHelper.addSeasonToRated();
                    ratingHelper.addSeasonRating(grade);
                    ratingHelper.addSerialRating();
                    writerHelper.addToArrayResult(action, videoRated(title,
                                                grade, user.getUsername()));
                }
                return;
            }
        }
    }
}
