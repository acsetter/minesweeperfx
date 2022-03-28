package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

/**
 * Enum that represents the states of difficulty in the game.
 * <p>
 *     Each enum constant is instantiated with values used to build the gameBoard and respective logic.
 * </p>
 */
public enum Difficulty {
    EASY(8, 10, 10) {
        @Override
        public String toString() {
            return "Easy";
        }
    },
    DEMO(8, 10, 10) {
        @Override
        public String toString() {
            return "Demo";
        }
    },
    MEDIUM(14, 18, 40) {
        @Override
        public String toString() {
            return "Medium";
        }
    },
    HARD(20, 24, 99) {
        @Override
        public String toString() {
            return "Hard";
        }
    };

    private final int rows;
    private final int columns;
    private final int mines;

    Difficulty(int rows, int columns,int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    /**
     * The number of {@link GameBoard} rows for the respective Difficulty value.
     * @return int of the number of rows in the {@link GameBoard}
     */
    public int getRows() {
        return rows;
    }

    /**
     * The number of {@link GameBoard} columns for the respective difficulty value.
     * @return int of the number of rows in the {@link GameBoard}
     */
    public int getColumns() {
        return columns;
    }

    /**
     * The number of mines in the {@link GameBoard} for the respective difficulty value.
     * @return int of the number of mines in the {@link GameBoard}
     */
    public int getMines() {
        return mines;
    }
}
