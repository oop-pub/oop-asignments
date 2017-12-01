package cachingSystem.classes;

import cachingSystem.interfaces.Cache;
import cachingSystem.interfaces.CacheStalePolicy;
import observerPattern.interfaces.CacheListener;

/**
 * Abstract class that adds support for listeners and stale element policies to the Cache
 * interface.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class ObservableCache<K, V> implements Cache<K, V> {

    /**
     * Set a policy for removing stale elements from the cache.
     *
     * @param stalePolicy
     */
    public void setStalePolicy(CacheStalePolicy<K, V> stalePolicy) {
        /* TODO: implement setStalePolicy */
    }

    /**
     * Set a listener for the cache.
     *
     * @param cacheListener
     */
    public void setCacheListener(CacheListener<K, V> cacheListener) {
        /* TODO: implement setCacheListener */
    }

    /**
     * Clear the stale elements from the cache. This method must make use of the stale policy.
     *
     */
    public void clearStaleEntries() {
        /* TODO: implement clearStaleEntries */
    }
}
