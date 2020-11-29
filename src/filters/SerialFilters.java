package filters;

import fileio.ActionInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public final class SerialFilters {
    private final List<SerialInputData> inputSerials;

    public SerialFilters(final List<SerialInputData> serials) {
        inputSerials = serials;
    }

    /**
     *
     * @param year the year which the serial need to have to be added to the list
     * @param serialsInput the serials which we filter from
     * @return list of filtered serials
     */
    public List<SerialInputData> filterByYear(final String year,
                                              final List<SerialInputData> serialsInput) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : serialsInput) {
            if (Integer.toString(serial.getYear()).equals(year)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    /**
     *
     * @param genre the list of genres which the serial need to have to be added to the list
     * @param serialsInput the serials which we filter from
     * @return list of filtered serials
     */
    public List<SerialInputData> filterByGenre(final List<String> genre,
                                               final List<SerialInputData> serialsInput) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : serialsInput) {
            if (serial.getGenres().containsAll(genre)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    /**
     * Filter serials based on year, genre or both
     * @param action the action to be done
     * @return a list of filtered serials
     */
    public List<SerialInputData> applyFilters(final ActionInputData action) {
        List<List<String>> filters = action.getFilters();
        List<String> yearFilter = filters.get(0);
        List<String> genreFilter = filters.get(1);
        if (yearFilter.get(0) == null && genreFilter.get(0) == null) {
            return inputSerials;
        }
        List<SerialInputData> filteredSerials = null;
        if (yearFilter.get(0) != null) {
            String year = filters.get(0).get(0);
            filteredSerials = filterByYear(year, inputSerials);
            if (genreFilter.get(0) != null) {
                filteredSerials = filterByGenre(genreFilter, filteredSerials);
            }
        } else if (genreFilter.get(0) != null) {
            filteredSerials = filterByGenre(genreFilter, inputSerials);
        }

        return filteredSerials;
    }
}
