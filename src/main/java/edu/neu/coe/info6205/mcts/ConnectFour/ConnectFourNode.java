package edu.neu.coe.info6205.mcts.ConnectFour;

import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ConnectFourNode implements Node<ConnectFour> {

    private final State<ConnectFour> state;
    private final ArrayList<Node<ConnectFour>> children;
    private int wins;
    private int playouts;

    public ConnectFourNode(State<ConnectFour> state) {
        this.state = state;
        this.children = new ArrayList<>();
        initializeNodeData();
    }

    @Override
    public boolean isLeaf() {
        return state().isTerminal();
    }

    @Override
    public State<ConnectFour> state() {
        return state;
    }

    @Override
    public boolean white() {
        return state.player() == state.game().opener();
    }

    @Override
    public Collection<Node<ConnectFour>> children() {
        return children;
    }

    @Override
    public void addChild(State<ConnectFour> state) {
        children.add(new ConnectFourNode(state));
    }

    @Override
    public void backPropagate() {
        playouts = 0;
        wins = 0;
        for (Node<ConnectFour> child : children) {
            wins += child.wins();
            playouts += child.playouts();
        }
    }

    @Override
    public int wins() {
        return wins;
    }

    @Override
    public int playouts() {
        return playouts;
    }

    private void initializeNodeData() {
        if (isLeaf()) {
            playouts = 1;
            Optional<Integer> winner = state.winner();
            if (winner.isPresent())
                wins = 2; // A win is worth 2 points.
            else
                wins = 1; // A draw is worth 1 point.
        }
    }
}
