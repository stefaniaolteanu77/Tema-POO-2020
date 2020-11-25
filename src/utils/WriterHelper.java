package utils;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class WriterHelper {
    private final Writer fileWriter;
    private final JSONArray arrayResult;

    public WriterHelper(Writer fileWriter, JSONArray arrayResult) {
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
    }

    public void addToArrayResult(final ActionInputData action, final String message)
            throws IOException {
        JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
        arrayResult.add(object);
    }
}
