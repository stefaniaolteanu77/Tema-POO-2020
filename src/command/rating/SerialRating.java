package command.rating;

import entertainment.Season;
import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerialRating {
    SerialInputData serial;
    public SerialRating(SerialInputData serial) {
        this.serial = serial;
    }

    public void addSerialRating() {
        List<Season> seasons = serial.getSeasons();
        double sum = 0;
        for (Season season : seasons) {
            if(season.getRating() != null) {
                sum += season.getRating();
            }
        }
        double totalRating = sum / serial.getNumberSeason();
        serial.getSerialRating().add(totalRating);
    }
    public Double getTotalSerialRating() {
        addSerialRating();
        List<Double> ratings = serial.getSerialRating();
        if (ratings.isEmpty())
            return Double.valueOf(0);
        double sum = 0;
        for (Double rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }
}
