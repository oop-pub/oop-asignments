import cachingSystem.FileCache;
import cachingSystem.classes.*;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.classes.StatsListener;
import observerPattern.classes.KeyStatsListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static void testCacheType(FileCache.CacheType type, List<String> paths) {
        FileCache cache = FileCache.createCache(type);
        StatsListener<String, String> listener = new StatsListener<>();

        cache.addListener(listener);

        for (String path : paths) {
            String contents = cache.getFileContents(path);
        }

        System.out.println(type + ": " + listener.getHits());
        System.out.println(type + ": " + listener.getMisses());
        System.out.println(type + ": " + listener.getUpdates());
    }

    private static List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file path: " + filePath);
        }
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing path to input file.");
        }

        List<String> paths = readLines(args[0]);

        testCacheType(FileCache.CacheType.FIFO, paths);
        testCacheType(FileCache.CacheType.LIFO, paths);
        testCacheType(FileCache.CacheType.LRU, paths);
    }
}
