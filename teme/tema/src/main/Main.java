package main;

import checker.Checkstyle;
import checker.Checker;
import commands.Favorite;
import commands.Rating;
import commands.View;
import common.Constants;
import fileio.*;
import fileio.ActionInputData;
import org.json.simple.JSONArray;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.json.simple.JSONObject;
import queries.ActorQuery;
import queries.MovieQ;

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
     *
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
        List<SerialInputData> serialList = input.getSerials();
        List<MovieInputData> movieList = input.getMovies();
        List<ActorInputData> actorList = input.getActors();
        List<UserInputData> userList = input.getUsers();
        List<ActionInputData> comm = input.getCommands();
        ActorQuery query = new ActorQuery();


        JSONObject object = new JSONObject();
        for (ActionInputData action : comm) {
            if (action.getActionType().equals("command")) {
                if (action.getType() != null) {
                    if (action.getType().equals(Constants.RATING)) {
                        Rating rating = new Rating();
                        for (UserInputData user : input.getUsers()) {
                            if (user.getUsername().equals(action.getUsername())) {
                                String res = rating.addRating(input, user, action);
                                object = fileWriter.writeFile(action.getActionId(), "", res);
                            }
                        }
                    }
                    if (action.getType().equals(Constants.FAVORITE)) {
                        Favorite favorite = new Favorite();
                        List<UserInputData> users = input.getUsers();
                        for (int i = 0, usersSize = users.size(); i < usersSize; i++) {
                            UserInputData user = users.get(i);
                            if (user.getUsername().equals(action.getUsername())) {
                                object = fileWriter.writeFile(
                                        action.getActionId(), "", favorite.favAdd(user, action.getTitle()));
                                break;
                            }
                        }
                    }
                    if (action.getType().equals(Constants.VIEW)) {
                        View viewResult = new View();
                        String res = viewResult.views(input, action);
                        object = fileWriter.writeFile(action.getActionId(), "", res);
                    }

                }


            }
            if (action.getActionType().equals(Constants.QUERY)) {
                if (action.getObjectType().equals(Constants.ACTORS)) {
                    if (action.getCriteria().equals(Constants.AVERAGE)) {
                        query.average(action, actorList, movieList, serialList);
                        object = fileWriter.writeFile(
                                action.getActionId(), "", query.getMessage());

                    }

                    if (action.getCriteria().equals(Constants.AWARDS)) {
                        query.prize(action, actorList);
                        object = fileWriter.writeFile(
                                action.getActionId(), "", query.getMessage());

                    }

                }
                if (action.getObjectType().equals(Constants.MOVIES)) {
                    MovieQ query2;
                    query2 = new MovieQ();
                    if (action.getCriteria().equals(Constants.RATINGS)) {
                        query2.rateMovies(action, movieList);
                        object = fileWriter.writeFile(
                                action.getActionId(), "", query2.getMessage());


                    }

                    if (action.getCriteria().equals(Constants.FAVORITE)) {
                        query2.favMoviesQ(action, movieList, userList);
                        object = fileWriter.writeFile(
                                action.getActionId(), "", query2.getMessage());

                    }

                    if (action.getCriteria().equals(Constants.LONGEST)) {
                        query2.longestMovie(action, movieList);
                        object = fileWriter.writeFile(
                                action.getActionId(), "", query2.getMessage());

                    }

                }

                 }
            arrayResult.add(object);
            }


        fileWriter.closeJSON(arrayResult);
        }


    }
