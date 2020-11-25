package query;

import entertainment.Season;
import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerialRating {
    UserInputData user;
    SerialInputData serial;

    public SerialRating(UserInputData user, SerialInputData serial) {
        this.user = user;
        this.serial = serial;
    }
    public boolean isSeasonRated(String title, Integer season){
        Map<String,List <Integer>> ratings = user.getSeasonsRated();
        if (ratings.containsKey(title)) {
            List<Integer> seasonNumber = ratings.get(title);
            return seasonNumber.contains(season);
        }
        return false;
    }
    public void addSeasonToRated(String title, Integer season) {
        Map<String,List<Integer>> ratings = user.getSeasonsRated();
        List<Integer> seasonNumber = ratings.get(title);
        if (seasonNumber == null) {
            seasonNumber = new ArrayList<>();
        }
        seasonNumber.add(season);
        ratings.put(title, seasonNumber);
    }

    public void addSeasonRating(Integer numberOfSeason, Double grade) {
        ArrayList<Season> seasons = serial.getSeasons();
        for (Season season : seasons) {
            if (season.getCurrentSeason() == numberOfSeason) {
                season.setRating(grade);
            }
        }
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
