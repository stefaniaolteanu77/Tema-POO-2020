package command.rating;

import fileio.MovieInputData;
import fileio.UserInputData;

import java.util.List;

public final class MovieRating {
    private final MovieInputData movie;
    private final UserInputData user;

    public MovieRating(final MovieInputData movie, final UserInputData user) {
        this.movie = movie;
        this.user = user;
    }

    /**
     *   Adds movie to a list of the movies rated by the
     * user
     * @param title the title of the movie to be rated
     */
    public void addMovieToRated(final String title) {
        List<String> ratings = user.getMoviesRated();
        ratings.add(title);
    }

    /**
     *
     * @param title the title of the movie that needs
     *      to be checked if it's rated
     * @return whether the movie is rated or not
     */
    public boolean isMovieRated(final String title) {
        List<String> ratings = user.getMoviesRated();
        return ratings.contains(title);
    }

    /**
     * Add the grade to the movie's rating list
     * @param grade the grade given to the movie
     */
    public void addToRatingList(final Double grade) {
        List<Double> ratings = movie.getRatings();
        ratings.add(grade);
        movie.setRatings(ratings);
    }

}
