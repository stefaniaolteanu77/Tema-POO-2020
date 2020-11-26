package command.rating;

import fileio.MovieInputData;
import fileio.UserInputData;

import java.util.List;

public class MovieRating {
    MovieInputData movie;

    public MovieRating(MovieInputData movie) {
        this.movie = movie;
    }
    public void addMovieToRated(UserInputData user, String title) {
        List<String> ratings = user.getMoviesRated();
        ratings.add(title);
    }

    public boolean isMovieRated(UserInputData user,String title) {
        List<String> ratings = user.getMoviesRated();
        return ratings.contains(title);
    }
    public void addToRatingList(Double grade) {
        List<Double> ratings = movie.getRatings();
        ratings.add(grade);
    }

    public Double getTotalMovieRating() {
        List<Double> ratings = movie.getRatings();
        if (ratings.isEmpty()){
            return Double.valueOf(0);
        }
        double sum = 0;
        for (Double rating : ratings) {
            sum += rating;
        }
        return sum/ratings.size();
    }
}
