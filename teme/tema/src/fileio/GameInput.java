package fileio;

import java.util.ArrayList;

public final class GameInput {
        private StartGameInput startGame;
        private ArrayList<ActionsInput> actions;

        public GameInput() {
        }

        public StartGameInput getStartGame() {
                return startGame;
        }

        public void setStartGame(final StartGameInput startGame) {
                this.startGame = startGame;
        }

        public ArrayList<ActionsInput> getActions() {
                return actions;
        }

        public void setActions(final ArrayList<ActionsInput> actions) {
                this.actions = actions;
        }

        @Override
        public String toString() {
                return "GameInput{"
                        +  "startGame="
                        + startGame
                        + ", actions="
                        + actions
                        + '}';
        }
}
