package video;

import com.sun.source.tree.Tree;
import container.Container;
import users.Users;

import java.util.*;

public final class Videos implements Container {

    private static Videos instance;
    private final List<Video> videos;
    private final Map<String, Video> video;
    private final Map<String, Integer> popular;

    private Videos() {
        videos = new LinkedList<Video>();
        video = new HashMap<String, Video>();
        popular = new TreeMap<String, Integer>(Collections.reverseOrder());
    }

    public static Videos getInstance() {

        if(instance == null) {
            synchronized (Users.class) {
                if(instance == null) {
                    instance = new Videos();
                }
            }
        }
        return instance;
    }

    public List<Video> getAll() {
        return videos;
    }

    public void add(Object video) {

        videos.add((Video)video);
        updateGenre(video);
        this.video.put(((Video)video).title, (Video)video);

    }

    public void updateGenre(Object video) {
        for(String genre : ((Video) video).genres) {
            Integer num_view = popular.remove(genre);
            if(num_view == null) {
                num_view = 0;
            }
            popular.put(genre, num_view + ((Video) video).getViews());
        }
    }

    public Map<String, Integer> getPopular(){
        return popular;
    }

    public Video get(String title) {
        return video.get(title);
    }

    public void purge() {
        video.clear();
        videos.clear();
    }
}
