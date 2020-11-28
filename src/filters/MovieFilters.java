package filters;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class MovieFilters {
    List<MovieInputData> inputMovies;

    public MovieFilters(List<MovieInputData> movies) {
        inputMovies = movies;
    }

    public List<MovieInputData> filterByYear(String year, List<MovieInputData> moviesInput) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : moviesInput) {
            if (Integer.toString(movie.getYear()).equals(year)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public List<MovieInputData> filterByGenre(List<String> genre, List<MovieInputData> moviesInput) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : moviesInput) {
            if(movie.getGenres().containsAll(genre)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public List<MovieInputData> applyFilters(Input input, ActionInputData action) {
        List <MovieInputData> filteredMovies = new ArrayList<>();
        List<List<String>> filters = action.getFilters();
        List<String> yearFilter = filters.get(0);
        List<String> genreFilter = filters.get(1);
        if(yearFilter.get(0) == null && genreFilter.get(0) == null) {
            return inputMovies;
        }
        if (yearFilter.get(0) != null) {
                 String year = yearFilter.get(0);
                 filteredMovies = filterByYear(year, inputMovies);
                 if (genreFilter.get(0) != null) {
                     filteredMovies = filterByGenre(genreFilter, filteredMovies);
                 }
            }
        else if (genreFilter.get(0) != null) {
            filteredMovies = filterByGenre(genreFilter, inputMovies);
        }
        return filteredMovies;
    }
}
