import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.Database;
import entities.Error;
import entities.*;
import invoker.Invoker;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        ProgramOutput output = new ProgramOutput();
        Database database = objectMapper.readValue(new File(args[0]), Database.class);
        List<Actions> actionsList = database.getActions();
        OnPageAction onPageAction = new OnPageAction();
        Error error = new Error();
        Invoker invoker = new Invoker();
        CurrentPage currentPage = new CurrentPage("homepage", database,invoker);
        for (Actions actions : actionsList) {
            switch (actions.getType()) {
                case "change page" ->
                        invoker.execute( new ChangePageAction(actions.getPage(), currentPage, writer, actions, output, error));
                case "on page" -> onPageAction.execute(actions.getPage(), currentPage, writer, actions, output, error);
                case "database" -> DatabaseAction.execute(actions, currentPage, output);
                case "back" -> invoker.undo(currentPage, writer, actions, output, error);
                case "subscribe" ->
                        currentPage.getUser().getUser().addSubscribedGenre(actions.getSubscribedGenre(), currentPage, output, error);
            }
        }
        if(currentPage.getUser().getUser() !=null && currentPage.getUser().getUser().getCredentials().getAccountType().equals("premium")){
           var movie= Recommendations.getRecommandation(currentPage.getUser().getUser(),currentPage.getDatabase());
           if(movie != null)
            currentPage.getUser().getUser().getNotifications().add(new Notifications(movie.getName(),"Recommendation"));
           else
               currentPage.getUser().getUser().getNotifications().add(new Notifications("No recommendation","Recommendation"));
           output.getOutput().add(new CommandOutput(currentPage.getUser().getUser()));
        }
        currentPage.getUser().setCurrentUser(null);
        writer.writeValue(new File(args[1]), output.getOutput());
        database.getUsers().forEach(Users::resetUser);
        database.getMovies().forEach(Movies::resetMovies);
    }
}
