import cachingSystem.FileCache;
import observerPattern.classes.KeyStatsListener;
import observerPattern.classes.StatsListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public final class Main {
    private static final String FIFO_CACHE = "FIFO";
    private static final String LRU_CACHE = "LRU";
    private static final String TIME_AWARE_CACHE = "TIME";
    private static final String DELAY_COMMAND = "delay";
    private static final String GET_COMMAND = "get";
    private static final String PUT_COMMAND = "put";
    private static final String TOP_HITS = "top_hits";
    private static final String TOP_MISSES = "top_misses";
    private static final String TOP_UPDATES = "top_updates";
    private static final String KEY_HITS = "key_hits";
    private static final String KEY_MISSES = "key_misses";
    private static final String KEY_UPDATES = "key_updates";
    private static final String TOTAL_HITS = "total_hits";
    private static final String TOTAL_MISSES = "total_misses";
    private static final String TOTAL_UPDATES = "total_updates";

    private Main() {

    }

    private static List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file path: " + filePath);
        }
    }

    private static FileCache createFileCache(String command) {
        String[] tokens = command.split(" ");
        long parameter = Long.parseLong(tokens[1]);

        switch (tokens[0]) {
            case FIFO_CACHE:
                return FileCache.createCacheWithCapacity(FileCache.Strategy.FIFO, (int) parameter);
            case LRU_CACHE:
                return FileCache.createCacheWithCapacity(FileCache.Strategy.LRU, (int) parameter);
            case TIME_AWARE_CACHE:
                return FileCache.createCacheWithExpiration(parameter);
            default:
                throw new IllegalArgumentException("Unsupported cache type: " + tokens[0]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing path to test file.");
        }

        List<String> commands = readLines(args[0]);

        Iterator<String> commandIterator = commands.iterator();

        FileCache cache = createFileCache(commandIterator.next());
        KeyStatsListener<String, String> keyStatsListener = new KeyStatsListener<>();
        StatsListener<String, String> statsListener = new StatsListener<>();

        cache.addListener(keyStatsListener);
        cache.addListener(statsListener);

        while (commandIterator.hasNext()) {
            String command = commandIterator.next().trim();
            String[] tokens = command.split(" ");

            if (command.isEmpty() || command.startsWith("#")) {
                continue;
            }

            switch (tokens[0]) {
                case DELAY_COMMAND:
                    long parameter = Long.parseLong(tokens[1]);

                    Thread.sleep(parameter);

                    break;
                case GET_COMMAND:
                    System.out.println(cache.getFileContents(tokens[1]));

                    break;
                case PUT_COMMAND:
                    cache.putFileContents(tokens[1], tokens[2]);

                    break;
                case TOP_HITS:
                    int topHitsLength = Integer.parseInt(tokens[1]);

                    /* Avoid sort differences by printing values instead of keys */
                    for (String key : keyStatsListener.getTopHitKeys(topHitsLength)) {
                        System.out.print(keyStatsListener.getKeyHits(key) + " ");
                    }

                    System.out.println();

                    break;
                case TOP_MISSES:
                    int topMissesLength = Integer.parseInt(tokens[1]);

                    /* Avoid sort differences by printing values instead of keys */
                    for (String key : keyStatsListener.getTopMissedKeys(topMissesLength)) {
                        System.out.print(keyStatsListener.getKeyMisses(key) + " ");
                    }

                    System.out.println();

                    break;
                case TOP_UPDATES:
                    int topUpdatesLength = Integer.parseInt(tokens[1]);

                    /* Avoid sort differences by printing values instead of keys */
                    for (String key : keyStatsListener.getTopUpdatedKeys(topUpdatesLength)) {
                        System.out.print(keyStatsListener.getKeyUpdates(key) + " ");
                    }

                    System.out.println();

                    break;
                case KEY_HITS:
                    System.out.println(keyStatsListener.getKeyHits(tokens[1]));
                    break;
                case KEY_MISSES:
                    System.out.println(keyStatsListener.getKeyMisses(tokens[1]));
                    break;
                case KEY_UPDATES:
                    System.out.println(keyStatsListener.getKeyUpdates(tokens[1]));
                    break;
                case TOTAL_HITS:
                    System.out.println(statsListener.getHits());
                    break;
                case TOTAL_MISSES:
                    System.out.println(statsListener.getMisses());
                    break;
                case TOTAL_UPDATES:
                    System.out.println(statsListener.getUpdates());
                    break;
                default:
                    continue;
            }
        }
    }
}
