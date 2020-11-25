package utils;

import fileio.*;

import java.util.List;

public class Searcher {
    List<MovieInputData> movies;
    List<SerialInputData> serials;

    public Searcher(List<MovieInputData> movies, List<SerialInputData> serials) {
        this.movies = movies;
        this.serials = serials;
    }

    public MovieInputData lookForMovie(String title) {
        for(MovieInputData movie : movies) {
            if(movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    public SerialInputData lookForSerial(String title) {
        for(SerialInputData serial : serials) {
            if(serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }
}
