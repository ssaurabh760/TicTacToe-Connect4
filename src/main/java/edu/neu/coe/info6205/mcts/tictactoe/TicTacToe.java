package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.*;

/**
 * Class which models the game of TicTacToe.
 */
public class TicTacToe implements Game<TicTacToe> {
    /**
     * Main program to run a random TicTacToe game.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        // NOTE the behavior of the game to be run will be based on the TicTacToe instance field: random.
        State<TicTacToe> state = new TicTacToe().runGame();
        if (state.winner().isPresent()) {
            int winner = state.winner().get();
            if(winner == 1) System.out.println("TicTacToe: winner is: X");
            else System.out.println("TicTacToe: winner is: 0");
        }
        else System.out.println("TicTacToe: draw");
    }

    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    /**
     * Method to yield a starting position.
     *
     * @return a Position.
     */
    static Position startingPosition() {
        return Position.parsePosition(". . .\n. . .\n. . .", blank);
    }

    /**
     * Run a TicTacToe game.
     *
     * @return the terminal State.
     */
    State<TicTacToe> runGame() {
        State<TicTacToe> state = start();
        MCTS mcts = new MCTS(new TicTacToeNode(state)); // Initialize MCTS with the starting state
        Random random = new Random();

        while (!state.isTerminal()) {
            System.out.println(state.toString());

            // MCTS move
            mcts.run(40);
            Node<TicTacToe> bestMove = mcts.bestChild(MCTS.root);
            if (bestMove == null) {
                throw new IllegalStateException("MCTS did not return a move");
            }
            state = bestMove.state();
            System.out.println("MCTS move:");
            System.out.println(state);

            if (state.isTerminal()) {
                break;
            }

            // Random move
            Collection<Move<TicTacToe>> legalMoves = state.moves(state.player());
            Move<TicTacToe> randomMove = legalMoves.stream().skip(random.nextInt(legalMoves.size())).findFirst().orElse(null);
            if (randomMove == null) {
                throw new RuntimeException("Random move is null");
            }
            state = state.next(randomMove);
            System.out.println("Random move:");
            System.out.println(state);

            if (state.isTerminal()) {
                break;
            }

            MCTS.root = new TicTacToeNode(state);
        }

        System.out.println(state.toString());

        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) {
            if (winner.get() == 1) {
                System.out.println("MCTS won!");
            } else {
                System.out.println("Random won!");
            }
        } else {
            System.out.println("It's a draw!");
        }

        return state;
    }


    /**
     * This method determines the opening player (the "white" by analogy with chess).
     * NOTE this should agree with
     *
     * @return the opening player.
     */
    public int opener() {
        return X;
    }

    /**
     * Get the starting state for this game.
     *
     * @return a State of TicTacToe.
     */
    public State<TicTacToe> start() {
        return new TicTacToeState();
    }

    /**
     * Primary constructor.
     *
     * @param random a random source.
     */
    public TicTacToe(Random random) {
        this.random = random;
    }

    /**
     * Secondary constructor.
     *
     * @param seed a seed for the random source.
     */
    public TicTacToe(long seed) {
        this(new Random(seed));
    }

    /**
     * Secondary constructor which uses the current time as seed.
     */
    public TicTacToe() {
        this(System.currentTimeMillis());
    }

    private final Random random;

    /**
     * Inner class to define a Move of TicTacToe.
     */
    static class TicTacToeMove implements Move<TicTacToe> {
        /**
         * @return the player for this Move.
         */
        public int player() {
            return player;
        }

        /**
         * Primary constructor.
         *
         * @param player the player.
         * @param i      the row.
         * @param j      the column.
         */
        public TicTacToeMove(int player, int i, int j) {
            this.player = player;
            this.i = i;
            this.j = j;
        }

        /**
         * @return this move as an array of two coordinates: row and column.
         */
        public int[] move() {
            return new int[]{i, j};
        }

        private final int player;
        private final int i;
        private final int j;
    }

    /**
     * Inner class to define a State of TicTacToe.
     */
    class TicTacToeState implements State<TicTacToe> {
        /**
         * Method to yield the game of which this is a State.
         *
         * @return a G
         */
        public TicTacToe game() {
            return TicTacToe.this;
        }

        /**
         * Method to determine the player who plays to this State.
         * The first player to play is considered to be "white" by analogy with chess.
         *
         * @return a non-negative integer.
         */
        public int player() {
            return switch (position.last) {
                case 0, -1 -> X;
                case 1 -> O;
                default -> blank;
            };
        }

        /**
         * @return the Position of this State.
         */
        public Position position() {
            return this.position;
        }

        /**
         * Method to determine if this State represents the end of the game?
         *
         * @return true if this State is a win/loss/draw.
         */
        public Optional<Integer> winner() {
            return position.winner();
        }

        /**
         * A random source associated with this State.
         * Currently, it is set to the same random as used by TicTacToe.
         * If you need a different random for each state, override this.
         *
         * @return the appropriate RandomState.
         */
        public Random random() {
            return random;
        }

        /**
         * Get the moves that can be made directly from the given state.
         * The moves can be in any order--the order will be randomized for usage.
         *
         * @return all the possible moves from this state.
         */
        public Collection<Move<TicTacToe>> moves(int player) {
            if (player == position.last) throw new RuntimeException("consecutive moves by same player: " + player);
            List<int[]> moves = position.moves(player);
            ArrayList<Move<TicTacToe>> list = new ArrayList<>();
            for (int[] coordinates : moves) list.add(new TicTacToeMove(player, coordinates[0], coordinates[1]));
            return list;
        }

        /**
         * Implement the given move on the given state.
         *
         * @param move the move to implement.
         * @return a new state.
         */
        public State<TicTacToe> next(Move<TicTacToe> move) {
            TicTacToeMove ticTacToeMove = (TicTacToeMove) move;
            int[] ints = ticTacToeMove.move();
            return new TicTacToeState(position.move(move.player(), ints[0], ints[1]));
        }

        /**
         * Is the game over?
         *
         * @return true if position is full or if position is a winner.
         */
        public boolean isTerminal() {
            return position.full() || position.winner().isPresent();
        }

        @Override
        public String toString() {
            return "TicTacToe\n" +
                           position.render() +
                           "\n";
        }

        public TicTacToeState(Position position) {
            this.position = position;
        }

        public TicTacToeState() {
            this(startingPosition());
        }

        private final Position position;
    }
}
