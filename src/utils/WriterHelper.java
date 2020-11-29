package utils;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public final class WriterHelper {
    private final Writer fileWriter;
    private final JSONArray arrayResult;

    public WriterHelper(final Writer fileWriter, final JSONArray arrayResult) {
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
    }

    /**
     * Helps write all messages to output
     * @param action the action to be done
     * @param message the message that needs to pe printed to output
     * @throws IOException in case the message can't be written to output
     */
    public void addToArrayResult(final ActionInputData action, final String message)
            throws IOException {
        JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
        arrayResult.add(object);
    }
}
