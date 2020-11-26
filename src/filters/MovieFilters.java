package filters;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class MovieFilters {
    List<MovieInputData> inputMovies;

    public MovieFilters(List<MovieInputData> movies) {
        inputMovies = movies;
    }

    public List<MovieInputData> filterByYear(String year) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : inputMovies) {
            if (Integer.toString(movie.getYear()).equals(year)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public List<MovieInputData> filterByGenre(List<String> genre) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : inputMovies) {
            if(movie.getGenres().containsAll(genre)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public List<MovieInputData> applyFilters(Input input, ActionInputData action) {
        List <MovieInputData> filteredMovies = new ArrayList<>();
        List<List<String>> filters = action.getFilters();
        if(filters.isEmpty()) {
            filteredMovies = input.getMovies();
        } else {
            if (filters.get(0) != null) {
                 String year = filters.get(0).get(0);
                 filteredMovies = filterByYear(year);
                 if (filters.get(1) != null) {
                     List<String> genre = filters.get(1);
                     inputMovies = filteredMovies;
                     filteredMovies = filterByGenre(genre);
                 }
            }
            else if (filters.get(1) != null) {
                List<String> genre = filters.get(1);
                filteredMovies = filterByGenre(genre);
            }
        }
        return filteredMovies;
    }
}
