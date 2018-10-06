package observerPattern.classes;

import java.util.List;

import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(K key) {
        /* TODO: implement getKeyHits */
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(K key) {
        /* TODO: implement getKeyMisses */
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(K key) {
        /* TODO: implement getKeyUpdates */
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(int top) {
        /* TODO: implement getTopHitKeys */
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(int top) {
        /* TODO: implement getTopMissedKeys */
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(int top) {
        /* TODO: implement getTopUpdatedKeys */
    }

    /* TODO: implement the CacheListener interface */
}
