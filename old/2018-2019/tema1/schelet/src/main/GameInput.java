package main;

import java.util.List;

public class GameInput {
    private final List<Integer> mAssetOrder;
    private final List<String> mPlayersOrder;

    public GameInput() {
        mAssetOrder = null;
        mPlayersOrder = null;
    }

    public GameInput(final List<Integer> assets, final List<String> players) {
        mAssetOrder = assets;
        mPlayersOrder = players;
    }

    public final List<Integer> getAssetIds() {
        return mAssetOrder;
    }

    public final List<String> getPlayerNames() {
        return mPlayersOrder;
    }

    public final boolean isValidInput() {
        boolean membersInstantiated = mAssetOrder != null && mPlayersOrder != null;
        boolean membersNotEmpty = mAssetOrder.size() > 0 && mPlayersOrder.size() > 0;

        return membersInstantiated && membersNotEmpty;
    }
}
