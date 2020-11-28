package filters;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class SerialFilters {
    List<SerialInputData> inputSerials;

    public SerialFilters(List<SerialInputData> serials) {
        inputSerials = serials;
    }

    public List<SerialInputData> filterByYear(String year, List<SerialInputData> serialsInput) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : serialsInput) {
            if (Integer.toString(serial.getYear()).equals(year)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    public List<SerialInputData> filterByGenre(List<String> genre, List<SerialInputData> serialsInput) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : serialsInput) {
            if(serial.getGenres().containsAll(genre)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    public List<SerialInputData> applyFilters(Input input, ActionInputData action) {
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
            }
        else if (genreFilter.get(0) != null) {
            filteredSerials = filterByGenre(genreFilter, inputSerials);
        }

        return filteredSerials;
    }
}
