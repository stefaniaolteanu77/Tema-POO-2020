package filters;

import fileio.ActionInputData;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public final class MovieFilters {
    private final List<MovieInputData> inputMovies;

    public MovieFilters(final List<MovieInputData> movies) {
        inputMovies = movies;
    }

    /**
     *
     * @param year the year which the movie need to have to be added to the list
     * @param moviesInput the movies which we filter from
     * @return list of filtered movies
     */
    public List<MovieInputData> filterByYear(final String year,
                                             final List<MovieInputData> moviesInput) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : moviesInput) {
            if (Integer.toString(movie.getYear()).equals(year)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    /**
     *
     * @param genre the list of genres which the movie need to have to be added to the list
     * @param moviesInput the movies which we filter from
     * @return list of filtered movies
     */
    public List<MovieInputData> filterByGenre(final List<String> genre,
                                              final List<MovieInputData> moviesInput) {
        List<MovieInputData> movies = new ArrayList<>();
        for (MovieInputData movie : moviesInput) {
            if (movie.getGenres().containsAll(genre)) {
                movies.add(movie);
            }
        }
        return movies;
    }
    /**
     * Filter movies based on year, genre or both
     * @param action the action to be done
     * @return a list of filtered serials
     */
    public List<MovieInputData> applyFilters(final ActionInputData action) {
        List<MovieInputData> filteredMovies = new ArrayList<>();
        List<List<String>> filters = action.getFilters();
        List<String> yearFilter = filters.get(0);
        List<String> genreFilter = filters.get(1);
        if (yearFilter.get(0) == null && genreFilter.get(0) == null) {
            return inputMovies;
        }
        if (yearFilter.get(0) != null) {
            String year = yearFilter.get(0);
            filteredMovies = filterByYear(year, inputMovies);
            if (genreFilter.get(0) != null) {
                filteredMovies = filterByGenre(genreFilter, filteredMovies);
            }
        } else if (genreFilter.get(0) != null) {
            filteredMovies = filterByGenre(genreFilter, inputMovies);
        }
        return filteredMovies;
    }
}
