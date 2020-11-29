package command.rating;

import entertainment.Season;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SerialRating {
    private final SerialInputData serial;
    private final Integer season;
    private final UserInputData user;

    public SerialRating(final SerialInputData serial,
                        final Integer season, final UserInputData user) {
        this.serial = serial;
        this.season = season;
        this.user = user;
    }
    /**
     *
     * @return whether the season is rated or not
     */
    public boolean isSeasonRated() {
        Map<String, List<Integer>> ratings = user.getSeasonsRated();
        if (ratings.containsKey(serial.getTitle())) {
            List<Integer> seasonNumber = ratings.get(serial.getTitle());
            return seasonNumber.contains(season);
        }
        return false;
    }
    /**
     *   Adds season to a list of the seasons from a certain serial
     *   rated by the user
     */
    public void addSeasonToRated() {
        Map<String, List<Integer>> ratings = user.getSeasonsRated();
        List<Integer> seasonNumber = ratings.get(serial.getTitle());
        if (seasonNumber == null) {
            seasonNumber = new ArrayList<>();
        }
        seasonNumber.add(season);
        ratings.put(serial.getTitle(), seasonNumber);
    }

    /**
     * Add the grade to the season
     * @param grade the grade given to the season
     */
    public void addSeasonRating(final Double grade) {
        ArrayList<Season> seasons = serial.getSeasons();
        for (Season seasonInput : seasons) {
            if (seasonInput.getCurrentSeason() == season) {
                seasonInput.setRating(grade);
            }
        }
    }

    /**
     * Calculates the rating of the serial
     * and adds it to the serial's rating list
     */
    public void addSerialRating() {
        List<Season> seasons = serial.getSeasons();
        double sum = 0;
        for (Season seasonInput : seasons) {
            if (seasonInput.getRating() != 0) {
                sum += seasonInput.getRating();
            }
        }
        double totalRating = sum / serial.getNumberSeason();
        Map<String, Double> serialRating = serial.getSerialRating();
        serialRating.put(user.getUsername(), totalRating);
        serial.setSerialRating(serialRating);
    }


}
