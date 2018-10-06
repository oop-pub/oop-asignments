package cachingSystem.interfaces;

import dataStructures.classes.Pair;

/**
 * The Cache interface defines the functionality for caching key-value pairs.
 */
public interface Cache<K, V> {

    /**
     * Get the value associated with a key, or null if the key does not exist in the cache.
     *
     * @param key the key too lookup
     * @return the associated value, or null
     */
    V get(K key);

    /**
     * Insert a key value pair in the cache.
     *
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * The cache size is defined as the number of stored key-value pairs.
     *
     * @return the cache size
     */
    int size();

    /**
     * Tells whether or not the cache is empty.
     *
     * @return @true if the cache has no keys stored, @false otherwise.
     */
    boolean isEmpty();

    /**
     * Remove a key if it exists in the cache.
     *
     * @param key the key to be removed
     * @return the value associated with the key, or null if the key was not in the cache
     */
    V remove(K key);

    /**
     * Clear all the elements from the cache.
     */
    void clearAll();

    /**
     * Return the eldest entry from the cache. The definition for the eldest entry varies from one
     * type of cache to another.
     *
     * @return the eldest entry
     */
    Pair<K, V> getEldestEntry();
}
