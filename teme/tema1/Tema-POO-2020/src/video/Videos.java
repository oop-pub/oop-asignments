package video;

import container.Container;
import users.Users;

import java.util.*;

public final class Videos implements Container {

    private static Videos instance;
    private final List<Video> videos;
    private final Map<String, Video> video;

    private Videos() {
        videos = new LinkedList<Video>();
        video = new HashMap<String, Video>();
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
        this.video.put(((Video)video).title, (Video)video);

    }

    public Video get(String title) {
        return video.get(title);
    }

    public void purge() {
        video.clear();
        videos.clear();
    }
}
