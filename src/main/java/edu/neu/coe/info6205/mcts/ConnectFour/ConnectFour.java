package edu.neu.coe.info6205.mcts.ConnectFour;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.*;

public class ConnectFour implements Game<ConnectFour> {

    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    private final Random random;

    public ConnectFour(Random random) {
        this.random = random;
    }

    static ConnectFourPosition startingPosition() {
        return ConnectFourPosition.parsePosition(
                ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .", blank);
    }

    public static void main(String[] args) {
        ConnectFour connectFour = new ConnectFour(new Random());
        State<ConnectFour> state = connectFour.start();
        while (!state.isTerminal()) {
            System.out.println(state);
            Move<ConnectFour> move = state.chooseMove(state.player());
            state = state.next(move);
        }
        System.out.println(state);
        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) {
            System.out.println("Winner: Player " + (winner.get() == 1 ? "X" : "O"));
        } else {
            System.out.println("Draw!");
        }
    }

    @Override
    public State<ConnectFour> start() {
        return new ConnectFourState();
    }

    @Override
    public int opener() {
        return X;
    }

    private class ConnectFourState implements State<ConnectFour> {

        private final ConnectFourPosition connectFourPosition;

        public ConnectFourState(ConnectFourPosition position) {
            this.connectFourPosition = position;
        }

        public ConnectFourState() {
            this(startingPosition());
        }

        @Override
        public ConnectFour game() {
            return ConnectFour.this;
        }

        @Override
        public boolean isTerminal() {
            return connectFourPosition.full() || connectFourPosition.winner().isPresent();
        }

        @Override
        public int player() {
            return switch (connectFourPosition.last) {
                case 0, -1 -> X;
                case 1 -> O;
                default -> blank;
            };
        }

        public ConnectFourPosition connectFourPosition() {
            return this.connectFourPosition;
        }

        @Override
        public Optional<Integer> winner() {
            return connectFourPosition.winner();
        }

        @Override
        public Random random() {
            return random;
        }

        @Override
        public Collection<Move<ConnectFour>> moves(int player) {
            ArrayList<Move<ConnectFour>> legalMoves = new ArrayList<>();
            for (int col = 0; col < ConnectFourPosition.gridColumns; col++) {
                if (connectFourPosition.grid[0][col] == blank) {
                    legalMoves.add(new ConnectFourMove(player, col));
                }
            }
            return legalMoves;
        }

        @Override
        public State<ConnectFour> next(Move<ConnectFour> move) {
            ConnectFourMove connectFourMove = (ConnectFourMove) move;
            int player = connectFourMove.player();
            int col = connectFourMove.column();
            int[][] newGrid = connectFourPosition.grid.clone();
            for (int row = ConnectFourPosition.gridRows - 1; row >= 0; row--) {
                if (newGrid[row][col] == blank) {
                    newGrid[row][col] = player;
                    break;
                }
            }
            return new ConnectFourState(new ConnectFourPosition(newGrid, connectFourPosition.count + 1, player));
        }

        @Override
        public Iterator<Move<ConnectFour>> moveIterator(int player) {
            return moves(player).iterator();
        }

        private final Scanner scanner = new Scanner(System.in);

        public int getMove() {
            System.out.print("Enter column number (0-6): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
            int column = scanner.nextInt();
            if (column < 0 || column > 6) {
                System.out.println("Invalid column. Please enter a number between 0 and 6.");
                return getMove(); // Recursive call to retry input
            }
            return column;
        }

        @Override
        public Move<ConnectFour> chooseMove(int player) {
            Collection<Move<ConnectFour>> legalMoves = moves(player);
            int choosenIndex = -1;
            if (player == X){
                choosenIndex = getMove();
            }
            else {
                choosenIndex = random.nextInt(legalMoves.size());
            }
            Iterator<Move<ConnectFour>> iterator = legalMoves.iterator();
            for (int i = 0; i < choosenIndex; i++) {
                iterator.next();
            }
            return iterator.next();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int[] row : connectFourPosition.grid) {
                for (int cell : row) {
                    sb.append(cell == blank ? "." : (cell == X ? "X" : "O")).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
