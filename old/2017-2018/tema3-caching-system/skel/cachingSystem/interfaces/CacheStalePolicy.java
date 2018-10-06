package cachingSystem.interfaces;

import dataStructures.classes.Pair;

/**
 * The CacheStalePolicy interface defines the logic for removing stale entries from caches.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface CacheStalePolicy<K, V> {

    /**
     * Return whether or not the eldest entry should be removed from the cache. This can be used to
     * limit the capacity of a cache, or to remove old entries based on custom logic.
     *
     * @param entry the eldest entry, as returned by @Cache.getEldestEntry
     * @return true if the eldest entry should be removed, false otherwise
     */
    boolean shouldRemoveEldestEntry(Pair<K, V> entry);
}
