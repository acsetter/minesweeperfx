package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

/**
 * Class wrapper for the {@link GameBoard} to communicate with the {@link AppManager}.
 * <p>This class wrapper helps enforce 1 game is instanced at a time per App Lifecycle.</p>
 */
public class Game {
    private final GameBoard gameBoard;

    public Game(Difficulty difficulty) {
        this.gameBoard = new GameBoard(difficulty);
        AppManager.setFlag(String.valueOf(gameBoard.getFlagCount()));

        gameBoard.flagCountProperty().addListener(
                (observableValue, oldInt, newInt) -> AppManager.setFlag(String.valueOf(newInt))
        );
        gameBoard.wonProperty().addListener(
                (observableValue, oldBool, newBool) ->  handleWin()
        );
        gameBoard.lostProperty().addListener(
                (observableValue, oldBool, newBool) -> handleLoss()
        );
    }

    /**
     * The game board for the active game.
     * @return {@link GameBoard} for the active game.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Invoked by the {@link GameBoard} via Property bindings.
     */
    private void handleWin() {
        AppManager.getTimer().pause();
        // Tell AppManager to show the help dialog:
        AppManager.showWinDialog();
    }

    /**
     * Invoked by the {@link GameBoard} via Property bindings.
     */
    private void handleLoss() {
        AppManager.getTimer().pause();
        // Tell AppManager to show loss dialog:
        AppManager.showLossDialog();
    }
}
