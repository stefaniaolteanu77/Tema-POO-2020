package main;

import checker.Checkstyle;
import checker.Checker;
import command.Command;
import command.QueryAverage;
import command.QueryAwards;
import command.VideoRating;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import query.User;
import utils.WriterHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();
        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        WriterHelper writerHelper = new WriterHelper(fileWriter,arrayResult);
        Command command = new Command(writerHelper, input);

        List<ActionInputData> actions = input.getCommands();
        for (ActionInputData action : actions) {
            command.applyCommand(action, input);
            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    UserInputData user = User.lookForUserInDataBase(input.getUsers(), action);
                    String title = User.standard(user, input);
                    String message = "StandardRecommendation result: " + title;
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
            }
            if(action.getActionType().equals("query")) {
                if(action.getCriteria().equals("average")) {
                    QueryAverage queryAverage = new QueryAverage(input.getMovies(),input.getSerials());
                    queryAverage.queryAverage(input, action, writerHelper);
                }
                if(action.getCriteria().equals("ratings")) {
                    VideoRating videoRating = new VideoRating();
                    videoRating.QueryVideoRating(input,action, writerHelper);
                }

            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
