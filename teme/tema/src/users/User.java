package users;

import Videos.Message;
import Videos.Video;

import java.util.ArrayList;
import java.util.Map;

import static Videos.Message.mesaj;

public class User {
    private String username;
    private String categorie;
    private ArrayList<String> favorite;
    private Map<String,Integer> vizualizate;

    public User() {
    }

    public User(String username,String categorie, ArrayList<String> favorite, Map<String,Integer> vizualizate) {
        this.username=username;
        this.categorie = categorie;
        this.favorite = favorite;
        this.vizualizate = vizualizate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategorie() {
        return categorie;
    }

    public ArrayList<String> getFavorite() {
        return favorite;
    }

    public Map<String, Integer> getVizualizate() {
        return vizualizate;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void addVizualizate(String video){

        if(vizualizate.containsKey(video)){
            vizualizate.put(video,vizualizate.get(video) + 1);

        }
        else {
            vizualizate.put(video,1);
        }
        mesaj.setLength(0);
        mesaj.append("success -> ");
        mesaj.append(video);
        mesaj.append(" was viewed with total views of ");
        mesaj.append(vizualizate.get(video));
    }

    public void Favorite(String video){
        if(vizualizate.containsKey(video) && !favorite.contains(video)){
            favorite.add(video);
            mesaj.setLength(0);
            mesaj.append("success -> ");
            mesaj.append(video);
            mesaj.append(" was added as favourite");
        }
        else if(favorite.contains(video)){
            mesaj.setLength(0);
            mesaj.append("error -> ");
            mesaj.append(video);
            mesaj.append(" is already in favourite list");
        }
        else if(!vizualizate.containsKey(video)){
            mesaj.setLength(0);
            mesaj.append("error -> ");
            mesaj.append(video);
            mesaj.append(" is not seen");
        }



    }


}
