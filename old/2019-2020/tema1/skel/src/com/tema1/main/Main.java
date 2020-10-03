package com.tema1.main;

import com.tema1.helpers.GameEngine;


public final class Main {

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();

        // TODO: implement homework logic
    }
}
