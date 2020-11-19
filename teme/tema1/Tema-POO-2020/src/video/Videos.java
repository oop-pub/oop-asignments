package video;

import container.Container;
import users.Users;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public final class Videos implements Container {

    private static Videos instance;
    private final List<Video> videos;
    private final Map<String, Video> video;
    private final Map<String, Integer> popular;

    private Videos() {
        videos = new LinkedList<Video>();
        video = new HashMap<String, Video>();
        popular = new LinkedHashMap<>();
    }

    /**
     *
     * @return
     */
    public static Videos getInstance() {

        if (instance == null) {
            synchronized (Users.class) {
                if (instance == null) {
                    instance = new Videos();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public List<Video> getAll() {
        return videos;
    }

    /**
     *
     * @param newVideo
     */
    public void add(final Object newVideo) {

        videos.add((Video) newVideo);
        updateGenre(newVideo);
        video.put(((Video) newVideo).title, (Video) newVideo);

    }

    /**
     *
     * @param newVideo
     */
    public void updateGenre(final Object newVideo) {
        for (String genre : ((Video) newVideo).genres) {
            Integer numView = popular.remove(genre);
            if (numView == null) {
                numView = 0;
            }
            popular.put(genre, numView + ((Video) newVideo).getViews());
        }
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> getPopular() {
        return popular;
    }

    /**
     *
     * @param title
     * @return
     */
    public Video get(final String title) {
        return video.get(title);
    }

    /**
     *
     */
    public void purge() {
        video.clear();
        videos.clear();
    }
}
