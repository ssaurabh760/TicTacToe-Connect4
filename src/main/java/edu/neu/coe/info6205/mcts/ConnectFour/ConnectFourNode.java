package edu.neu.coe.info6205.mcts.ConnectFour;

import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;

import java.util.Collection;

public class ConnectFourNode implements Node<ConnectFour> {
    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public State<ConnectFour> state() {
        return null;
    }

    @Override
    public boolean white() {
        return false;
    }

    @Override
    public Collection<Node<ConnectFour>> children() {
        return null;
    }

    @Override
    public void explore() {
        Node.super.explore();
    }

    @Override
    public void backPropagate() {

    }

    @Override
    public void addChild(State<ConnectFour> state) {

    }

    @Override
    public int wins() {
        return 0;
    }

    @Override
    public int playouts() {
        return 0;
    }
}
