package main;

import Videos.Filme;
import Videos.Message;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import Videos.Video;
import org.json.simple.JSONObject;
import users.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



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
        JSONObject object = new JSONObject();
        List<MovieInputData> moovies =  input.getMovies();
        ArrayList<Filme> filme = new ArrayList<>();
        for(MovieInputData element:moovies){
            filme.add(new Filme(element.getTitle(),element.getYear(),element.getGenres(), element.getDuration()));
        }
        List<UserInputData> users = input.getUsers();
        ArrayList<User> utilizatori = new ArrayList<>();
        for(UserInputData i:users){
            utilizatori.add(new User(i.getUsername(),i.getSubscriptionType(),i.getFavoriteMovies(),i.getHistory()));
        }

        List<ActionInputData> actions = input.getCommands();
        for(ActionInputData i : actions){
            int index;
            for(index = 0 ; index < utilizatori.size();index++){
                if(utilizatori.get(index).getUsername().equals(i.getUsername())){
                    break;
                }
            }
            if(i.getType()!= null && i.getType().compareTo("favorite") == 0){
                utilizatori.get(index).Favorite(i.getTitle());
            }
            if(i.getType()!= null && i.getType().compareTo("view") == 0){
                utilizatori.get(index).addVizualizate(i.getTitle());
            }
            object = fileWriter.writeFile(i.getActionId(),"", Message.mesaj.toString());
            arrayResult.add(object);
        }



        fileWriter.closeJSON(arrayResult);
    }
}
