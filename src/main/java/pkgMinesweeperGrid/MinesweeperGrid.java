package pkgMinesweeperGrid;

import java.util.Random;

public class MinesweeperGrid {
    private Cell[][] grid;
    private final int ROWS = 9;
    private final int COLS = 7;
    private final int NUM_MINES = 14;
    private boolean gameOver = false;
    private int cumulativeScore = 0;

    public MinesweeperGrid() {
        grid = new Cell[ROWS][COLS];
        initializeGrid();
        placeMines();
        verifyMineCount();
        calculateScores();
        printInitialBoard();
    }

    private void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void verifyMineCount() {
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j].isMine()) {
                    count++;
                }
            }
        }
        if (count != NUM_MINES) {
            initializeGrid();
            placeMines();
            verifyMineCount();
        }
    }


    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < NUM_MINES) {
            int row = ROWS - 1 - random.nextInt(ROWS);  // Flip the row coordinate
            int col = random.nextInt(COLS);

            if (!grid[row][col].isMine()) {
                grid[row][col].setMine(true);
                grid[row][col].setScore(0);
                minesPlaced++;
            }
        }
    }

    private void calculateScores() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!grid[i][j].isMine()) {
                    grid[i][j].setScore(calculateCellScore(i, j));
                }
            }
        }
    }

    private int calculateCellScore(int row, int col) {
        if (grid[row][col].isMine()) return 0;

        int score = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, ROWS - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, COLS - 1); j++) {
                if (i == row && j == col) continue;
                if (grid[i][j].isMine()) {
                    score += 10;
                } else {
                    score += 5;
                }
            }
        }
        return score;
    }

    public void printInitialBoard() {
        // Print M/G configuration from bottom to top
        for (int i = ROWS - 1; i >= 0; i--) {  // Changed loop to start from bottom
            for (int j = 0; j < COLS; j++) {
                System.out.print(grid[i][j].isMine() ? "M " : "G ");
            }
            System.out.println();
        }
        System.out.println();

        // Print scores from bottom to top
        for (int i = ROWS - 1; i >= 0; i--) {  // Changed loop to start from bottom
            for (int j = 0; j < COLS; j++) {
                System.out.printf("%2d ", grid[i][j].getScore());
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public void handleClick(int row, int col) {
        if (gameOver || !isInBounds(row, col)) {
            return;
        }

        Cell cell = grid[row][col];
        if (!cell.isRevealed()) {
            cell.reveal();
            if (cell.isMine()) {
                gameOver = true;
                revealAll();
                System.out.println("Game Over - Hit a mine!");
                System.out.println(cell.getMessage());
                printInitialBoard();
            } else {
                cumulativeScore += cell.getScore();
                System.out.printf("Mouse click at: (%d, %d) score: %d%n",
                        row, col, cumulativeScore);
                System.out.println(cell.getMessage());
            }
        }
    }
    private void revealAll() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j].reveal();
            }
        }
    }

    public Cell[][] getGrid() {
        Cell[][] flippedGrid = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                flippedGrid[i][j] = grid[ROWS - 1 - i][j];
            }
        }
        return flippedGrid;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}