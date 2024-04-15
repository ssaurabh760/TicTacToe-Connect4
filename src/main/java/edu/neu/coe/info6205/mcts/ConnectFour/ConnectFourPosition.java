package edu.neu.coe.info6205.mcts.ConnectFour;


import edu.neu.coe.info6205.mcts.tictactoe.Position;

public class ConnectFourPosition {

    private final int[][] grid;
    final int last;
    private final int count;
    private final int[] xxx;

    private final static int gridColumns = 6;

    private final static int gridRows = 6;


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
}
