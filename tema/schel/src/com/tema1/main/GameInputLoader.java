package com.tema1.main;

import java.util.ArrayList;
import java.util.List;

import fileio.FileSystem;

public final class GameInputLoader {
    private final String mInputPath;
    private final String mOutputPath;

    GameInputLoader(final String inputPath, final String outputPath) {
        mInputPath = inputPath;
        mOutputPath = outputPath;
    }

    public GameInput load() {
        List<Integer> assetsIds = new ArrayList<>();
        List<String> playerOrder = new ArrayList<>();
        int rounds = 0;
        int noPlayers = 0;
        int noGoods = 0;

        try {
            FileSystem fs = new FileSystem(mInputPath, mOutputPath);

            rounds = fs.nextInt();
            noPlayers = fs.nextInt();

            for (int i = 0; i < noPlayers; ++i) {
                playerOrder.add(fs.nextWord());
            }

            noGoods = fs.nextInt();

            for (int i = 0; i < noGoods; ++i) {
                assetsIds.add(fs.nextInt());
            }

            fs.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return new GameInput(rounds, assetsIds, playerOrder);
    }
}
