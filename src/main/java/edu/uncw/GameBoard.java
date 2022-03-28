package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

import edu.uncw.btns.GameBoardBtn;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;

/**
 * Extends the JavaFX GridPane to create the game board. All game logic is implemented here.
 */
public class GameBoard extends GridPane {
    private final GameBoardBtn[][] gameBoard;
    private final Difficulty difficulty;
    private final SimpleIntegerProperty flagCount;
    private final SimpleBooleanProperty won = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty lost = new SimpleBooleanProperty(false);

    public GameBoard(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.gameBoard = new GameBoardBtn[difficulty.getRows()][difficulty.getColumns()];
        this.flagCount = new SimpleIntegerProperty();
        flagCountProperty().setValue(difficulty.getMines());
        buildGameBoard();
        setAlignment(Pos.TOP_CENTER);
        setGridLinesVisible(true);
    }

    /**
     * Returns a count of the flags left to be played.
     * @return int flags left to be played.
     */
    public int getFlagCount() {
        return flagCount.get();
    }

    /**
     * Property wrapper for the flag count.
     * @return {@link SimpleIntegerProperty} for the flag count.
     */
    public SimpleIntegerProperty flagCountProperty() {
        return flagCount;
    }

    /**
     * Boolean indicating if the game is won.
     * @return boolean indicating a game win.
     */
    public boolean isWon() {
        return won.get();
    }

    /**
     * Property wrapper for the win boolean
     * @return {@link SimpleBooleanProperty} for the win boolean.
     */
    public SimpleBooleanProperty wonProperty() {
        return won;
    }

    /**
     * Boolean indicating if the game is lost.
     * @return boolean indicating a game loss.
     */
    public boolean isLost() {
        return lost.get();
    }

    /**
     * Property wrapper for the lost boolean.
     * @return {@link SimpleBooleanProperty} for the lost boolean.
     */
    public SimpleBooleanProperty lostProperty() {
        return lost;
    }

    /**
     * Builds the game board using random mine placement.
     */
    private void buildGameBoard() {
        int rows = difficulty.getRows();
        int cols = difficulty.getColumns();
        int n = rows * cols;
        int numMines = difficulty.getMines();
        // Randomly determine mine locations via 1d array:
        Random random = new Random();
        ArrayList<Integer> mineIndexes = new ArrayList<>() {{
            // 1d Array of all indexes on the board to randomly pick from:
            ArrayList<Integer> indexes = new ArrayList<>() {{
                for (int i = 0; i < n; i++) {
                    add(i);
                }
            }};
            // Iterate over indexes, pick random, and remove to prevent duplicates:
            for (int i = 0; i < numMines; i++) {
                int r = random.nextInt(indexes.size());
                add(indexes.get(r));
                indexes.remove(r);
            }
        }};
        // Build 2d board of each grid tile's value:
        int[][] board = new int[rows][cols];
        for (int x : mineIndexes) {
            // Places mines in a 2d array:
            int r = x / cols;
            int c = x % cols;
            board[r][c] = -1;
            // Increments numbers in 2d array around each mine:
            for (int i = -1; i <= 1; i++) {
                if (r + i >= 0 && r + i < rows) {
                    for (int j = -1; j <= 1; j++) {
                        if (c + j >= 0 && c + j < cols) {
                            if (board[r + i][c + j] != -1) {
                                board[r + i][c + j]++;
                            }
                        }
                    }
                }
            }
        }
        // Converts 2d int array of values into a 2d GameBoardBtn array:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameBoardBtn btn = new GameBoardBtn(board[i][j], i, j);
                gameBoard[i][j] = btn;
                btn.setOnMouseClicked(new BtnEventHandler());
                if (difficulty.equals(Difficulty.DEMO)) btn.setDemoMode(true);
                add(gameBoard[i][j], j, i, 1, 1);
            }
        }
    }

    /**
     * Flood-Fill Algorithm used when the user selects a cell that has no nearby mines.
     * <p>Recursively evaluates cardinally adjacent cells until all return.</p>
     * @param row int of the row index of selected cell.
     * @param col into of the column index of selected cell.
     */
    private void floodSelect(int row, int col) {

        if (col < 0
                || col >= difficulty.getColumns()
                || row < 0
                || row >= difficulty.getRows()) {
            return;
        }

        GameBoardBtn btn = gameBoard[row][col];

        if (btn.isSelected() || btn.isFlagged()) return;

        btn.select();

        if (btn.getValue() != 0) return;

        floodSelect(row + 1, col); // Recursively evaluates the cell above.
        floodSelect(row - 1, col); // Recursively evaluates the cell below.
        floodSelect(row, col + 1); // Recursively evaluates the cell to the right.
        floodSelect(row, col - 1); // Recursively evaluates the cell to the left.
        // TODO: Make this part more efficient:
        floodSelect(row - 1, col + 1); // Recursively evaluates the cell top-right.
        floodSelect(row + 1, col + 1); // Recursively evaluates the cell bottom-right.
        floodSelect(row + 1, col - 1); // Recursively evaluates the cell to the bottom-left.
        floodSelect(row - 1, col - 1); // Recursively evaluates the cell to the top-left.
    }

    /**
     * Evaluates the GameBoard for a win.
     */
    private void checkWin() {
        int n = difficulty.getRows() * difficulty.getColumns() - difficulty.getMines();
        int x = 0;
        // Iterate the GameBoard until a cell indicates a win is false.
        for (int i = 0; i < difficulty.getRows(); i++) {
            for (GameBoardBtn btn : gameBoard[i]) {
                if (btn.isSelected()) {
                    x++;
                }
                else if (btn.getValue() != -1) {
                    return;
                }
            }
        }
        // If iteration exhausts all cells and all indicate a win, set win property to true:
        wonProperty().setValue(x == n);
    }

    private void handleLoss() {
        for (int i = 0; i < difficulty.getRows(); i++) {
            for (GameBoardBtn btn : gameBoard[i]) {
                btn.show();
            }
        }

        lostProperty().setValue(true);
    }

    /**
     * MouseEvent handler invoked when a user clicks on a cell.
     * <p>Handler evaluates if a win, loss, or cell is selected.</p>
     */
    class BtnEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            GameBoardBtn btn = (GameBoardBtn) mouseEvent.getSource();
            // Disable click if game is over:
            if (isLost() || isWon()) return;

            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (btn.isSelected() || btn.isFlagged()) return;
                // Use FloodFill algo if cell value is zero:
                if (btn.getValue() == 0) {
                    floodSelect(btn.getRow(), btn.getCol());
                } else {
                    btn.select();
                }

                if (btn.getValue() == -1) {
                    handleLoss(); // fires when a cell value of -1 is selected.
                } else {
                    checkWin(); // always checks win, even after floodSelect.
                }
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                if (btn.isSelected()) return;
                // If right-click on flag, remove flag and add 1 to the flag count:
                if (btn.isFlagged()) {
                    btn.flag();
                    flagCountProperty().setValue(getFlagCount() + 1);
                } else {
                    // Ensure there are flags left to use:
                    if (getFlagCount() > 0) {
                        btn.flag();
                        flagCountProperty().setValue(getFlagCount() - 1);
                    }
                }
            }
        }
    }
}
