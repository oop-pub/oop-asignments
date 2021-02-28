package simulate;
import input.Player;

import java.util.List;

public final class Visa {

    private Visa() { }
    static <T extends Player> void getIncome(final List<T> players) {
        for (T player : players) {
            player.getIncome();
        }
    }

    static <T extends Player>  void payFees(final List<T> players) {
        for (T player : players) {
            player.pay();
        }
    }

}
