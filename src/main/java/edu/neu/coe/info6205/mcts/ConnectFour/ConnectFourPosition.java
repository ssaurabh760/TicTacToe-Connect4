package edu.neu.coe.info6205.mcts.ConnectFour;

import java.util.Optional;

public class ConnectFourPosition {

    final int[][] grid;
    final int last;
    final int count;
    private final int[] xxx;

    static final int gridColumns = 7;
    static final int gridRows = 6;

    ConnectFourPosition(int[][] grid, int count, int last) {
        this.grid = grid;
        this.count = count;
        this.last = last;
        xxx = new int[]{last, last, last};
    }

    /**
     * Method to parse a single cell.
     *
     * @param cell the String for the cell.
     * @return a number between -1 and 1 inclusive.
     */
    static int parseCell(String cell) {
        return switch (cell.toUpperCase()) {
            case "O", "0" -> 0;
            case "X", "1" -> 1;
            default -> -1;
        };
    }

    static ConnectFourPosition parsePosition(final String grid, final int last) {
        int[][] matrix = new int[gridRows][gridColumns];
        int count = 0;
        String[] rows = grid.split("\\n", gridRows);
        for (int i = 0; i < gridRows; i++) {
            String[] cells = rows[i].split(" ", gridColumns);
            for (int j = 0; j < gridColumns; j++) {
                int cell = parseCell(cells[j].trim());
                if (cell >= 0) count++;
                matrix[i][j] = cell;
            }
        }
        return new ConnectFourPosition(matrix, count, last);
    }

    public Optional<Integer> winner() {
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                int player = grid[i][j];
                if (player == -1) continue; // Skip empty cells
                // Check horizontal
                if (j + 3 < gridColumns && grid[i][j + 1] == player && grid[i][j + 2] == player && grid[i][j + 3] == player) {
                    return Optional.of(player);
                }
                // Check vertical
                if (i + 3 < gridRows && grid[i + 1][j] == player && grid[i + 2][j] == player && grid[i + 3][j] == player) {
                    return Optional.of(player);
                }
                // Check diagonal (up-right)
                if (i + 3 < gridRows && j + 3 < gridColumns && grid[i + 1][j + 1] == player && grid[i + 2][j + 2] == player && grid[i + 3][j + 3] == player) {
                    return Optional.of(player);
                }
                // Check diagonal (up-left)
                if (i + 3 < gridRows && j - 3 >= 0 && grid[i + 1][j - 1] == player && grid[i + 2][j - 2] == player && grid[i + 3][j - 3] == player) {
                    return Optional.of(player);
                }
            }
        }
        return Optional.empty();
    }

    public boolean full() {
        return count == gridRows * gridColumns;
    }
}
