package edu.neu.coe.info6205.mcts.ConnectFour;


import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import edu.neu.coe.info6205.mcts.ConnectFour.MCTS;
import edu.neu.coe.info6205.mcts.tictactoe.TicTacToe;

import java.util.*;

public class ConnectFour implements Game<ConnectFour> {

    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    private final Random random;

    public ConnectFour(Random random) {
        this.random = random;
    }


    public ConnectFour() {
        this(System.currentTimeMillis());
    }

    public ConnectFour(long seed) {
        this(new Random(seed));
    }

    /**
     * Secondary constructor which uses the current time as seed.
     */


    static ConnectFourPosition startingPosition() {
        return ConnectFourPosition.parsePosition(
                ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .\n" +
                        ". . . . . . .", blank);
    }

    public State<ConnectFour> runGame() {
        State<ConnectFour> state = start();
        // Initialize MCTS with the starting state

        while (!state.isTerminal()) {
            if (state.player() == X){
                MCTS mcts = new MCTS(new ConnectFourNode(state));
                mcts.run(1000);
                System.out.println("Player: " + state.player() + " Move");
                Node<ConnectFour> bestMove = mcts.bestChild(MCTS.root);
                if (bestMove == null){
                    throw new Error("Best move is null");
                }
                state = bestMove.state();
            }
            else {
                // Generate random input

                System.out.println("Player " + state.player() + " (Random) Move:");
                List<Move<ConnectFour>> legalMoves = new ArrayList<>(state.moves(state.player()));
                Move<ConnectFour> randomMove = legalMoves.get(random.nextInt(legalMoves.size()));
                state = state.next(randomMove);

            }
            System.out.println(state);
            MCTS.root = new ConnectFourNode(state);
        }
        return state;
    }

    public static void main(String[] args) {

        State<ConnectFour> state = new ConnectFour().runGame();
        if (state.winner().isPresent()) {
            int winner = state.winner().get();
            if(winner == 1) System.out.println("ConnectFour: winner is: X");
            else System.out.println("ConnectFour: winner is: 0");
        }
        else System.out.println("ConnectFour: draw");
    }

    @Override
    public State<ConnectFour> start() {
        return new ConnectFourState();
    }

    @Override
    public int opener() {
        return X;
    }

    public class ConnectFourState implements State<ConnectFour> {

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
            if (player == connectFourPosition.last) throw new RuntimeException("consecutive moves by same player: " + player);
            List<Integer> moves = connectFourPosition.moves(player);
            ArrayList<Move<ConnectFour>> legalMoves = new ArrayList<>();
            for (Integer col: moves) {
                if (connectFourPosition.grid[0][col] == blank) {
                    legalMoves.add(new ConnectFourMove(player, col));
                }
            }
            return legalMoves;
        }

        @Override
        public State<ConnectFour> next(Move<ConnectFour> move) {
            ConnectFourMove connectFourMove = (ConnectFourMove) move;
            int col = connectFourMove.column();

            return new ConnectFourState(connectFourPosition.move(move.player(), col));
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
