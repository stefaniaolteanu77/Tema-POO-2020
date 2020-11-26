package command.rating;

import entertainment.Season;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeasonRating {
    SerialInputData serial;
    Integer season;

    public SeasonRating(SerialInputData serial, Integer season) {
        this.serial = serial;
        this.season = season;
    }

    public boolean isSeasonRated(UserInputData user, String title){
        Map<String, List<Integer>> ratings = user.getSeasonsRated();
        if (ratings.containsKey(title)) {
            List<Integer> seasonNumber = ratings.get(title);
            return seasonNumber.contains(season);
        }
        return false;
    }
    public void addSeasonToRated(UserInputData user, String title) {
        Map<String,List<Integer>> ratings = user.getSeasonsRated();
        List<Integer> seasonNumber = ratings.get(title);
        if (seasonNumber == null) {
            seasonNumber = new ArrayList<>();
        }
        seasonNumber.add(season);
        ratings.put(title, seasonNumber);
    }

    public void addSeasonRating(Double grade) {
        ArrayList<Season> seasons = serial.getSeasons();
        for (Season season : seasons) {
            if (season.getCurrentSeason() == this.season) {
                season.setRating(grade);
            }
        }
    }

}
