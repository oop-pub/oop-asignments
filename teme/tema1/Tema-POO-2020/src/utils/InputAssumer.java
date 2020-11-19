package utils;

import actor.Actor;
import actor.Actors;
import fileio.*;
import users.User;
import users.Users;
import video.*;

public abstract class InputAssumer {

    public static void transfer(Input input) {

        for(MovieInputData movie : input.getMovies()) {
            Videos.getInstance().add(new Movie(movie));
        }

        for(SerialInputData show : input.getSerials()) {
            Videos.getInstance().add(new Show(show));
        }

        for(UserInputData userInputData : input.getUsers()) {
            Users.getInstance().add(new User(userInputData));
        }

        for(ActorInputData actor : input.getActors()) {
            Actors.getInstance().add(new Actor(actor));
        }

    }

    public static void purgeTestData() {
        Users.getInstance().purge();
        Actors.getInstance().purge();
        Videos.getInstance().purge();
    }
}
