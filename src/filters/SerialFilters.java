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

    public List<SerialInputData> filterByYear(String year) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : inputSerials) {
            if (Integer.toString(serial.getYear()).equals(year)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    public List<SerialInputData> filterByGenre(List<String> genre) {
        List<SerialInputData> serials = new ArrayList<>();
        for (SerialInputData serial : inputSerials) {
            if(serial.getGenres().containsAll(genre)) {
                serials.add(serial);
            }
        }
        return serials;
    }

    public List<SerialInputData> applyFilters(Input input, ActionInputData action) {
        List <SerialInputData> filteredserials = new ArrayList<>();
        List<List<String>> filters = action.getFilters();
        if(filters.isEmpty()) {
            filteredserials = input.getSerials();
        } else {
            if (filters.get(0) != null) {
                String year = filters.get(0).get(0);
                filteredserials = filterByYear(year);
                if (filters.get(1) != null) {
                    List<String> genre = filters.get(1);
                    inputSerials = filteredserials;
                    filteredserials = filterByGenre(genre);
                }
            }
            else if (filters.get(1) != null) {
                List<String> genre = filters.get(1);
                filteredserials = filterByGenre(genre);
            }
        }
        return filteredserials;
    }
}
