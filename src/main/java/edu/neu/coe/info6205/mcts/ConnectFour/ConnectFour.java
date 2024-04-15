package edu.neu.coe.info6205.mcts.ConnectFour;

import edu.neu.coe.info6205.mcts.core.Game;
import edu.neu.coe.info6205.mcts.core.Move;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

public class ConnectFour implements Game<ConnectFour> {

//    class variables
    public static final int X = 1;
    public static final int O = 0;
    public static final int blank = -1;

    private final Random random;

    public ConnectFour(Random random) {
        this.random = random;
    }

    /**
     * Method to yield a starting position.
     *
     * @return a Position.
     */
    static ConnectFourPosition startingPosition() {
        return ConnectFourPosition.parsePosition(". . . . . .\n. . . . . .\n. . . . . .\n. . . . . .\n. . . . . .\n. . . . . .\n. . . . . .", blank);
    }

    public static void main(String[] args) {

    }

    @Override
    public State<ConnectFour> start() {
        return new ConnectFourState();
    }




//  Let human play first move
    @Override
    public int opener() {
        return X;
    }

    private class ConnectFourState implements State<ConnectFour> {


        private final ConnectFourPosition connectFourPosition;

        @Override
        public String toString() {
            return "TicTacToe{\n" +
                    connectFourPosition +
                    "\n}";
        }

        public ConnectFourState(ConnectFourPosition position) {
            this.connectFourPosition = position;
        }

        public ConnectFourState(){
            this(startingPosition());
        }

        @Override
        public ConnectFour game() {
            return ConnectFour.this;
        }

        @Override
        public boolean isTerminal() {
            //return position.full() || position.winner().isPresent();
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

        public ConnectFourPosition connectFourPosition(){
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
            //Yet to implement
            return null;
        }

        @Override
        public State<ConnectFour> next(Move<ConnectFour> move) {
            //Yet to implement
            return null;
        }

        @Override
        public Iterator<Move<ConnectFour>> moveIterator(int player) {
            return State.super.moveIterator(player);
        }

        @Override
        public Move<ConnectFour> chooseMove(int player) {
            return State.super.chooseMove(player);
        }
    }
}
