package main;

import actor.Actor;
import actor.Actors;
import checker.Checkstyle;
import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.zip.None;
import solvers.ActorQuerySolver;
import solvers.UserCommandSolver;
import solvers.UserQuerySolver;
import solvers.VideoQuerySolver;
import users.User;
import users.Users;
import utils.InputAssumer;
import video.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
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
        InputAssumer.transfer(input);

        String result;

        for(ActionInputData action : input.getCommands()) {

            switch (action.getActionType()) {
                case "command":
                    result = UserCommandSolver.solve(action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(), null, result));
                    break;
                case "query":
                    switch (action.getObjectType()) {
                        case "actors" -> {
                            result = ActorQuerySolver.solve(action);
                            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, result));
                        }
                        case "users" -> {
                            result = UserQuerySolver.solve(action);
                            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, result));
                        }
                        case "movies", "shows" -> {
                            result = VideoQuerySolver.solve(action);
                            arrayResult.add(fileWriter.writeFile(action.getActionId(), null, result));
                        }
                    }
                    break;
                case "recommendation":
                    result = UserQuerySolver.recommend(action);
                    arrayResult.add(fileWriter.writeFile(action.getActionId(), null, result));
                    break;
            }

        }

        InputAssumer.purgeTestData();
        fileWriter.closeJSON(arrayResult);
    }
}
