package simulate;

import input.Consumer;
import input.Player;
import input.Players;

import java.util.List;

public final class Visa {
    static <T extends  Player> void getIncome(List<T> players) {
        for(T player : players) {
            player.getIncome();
        }
    }

    static <T extends Player>  void payFees(List<T> players) {
        for(T player : players) {
            player.pay();
        }
    }

}
