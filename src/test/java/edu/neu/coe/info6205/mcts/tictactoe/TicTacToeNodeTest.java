package edu.neu.coe.info6205.mcts.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TicTacToeNodeTest {

    @Test
    public void winsAndPlayouts() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState(Position.parsePosition("X . 0\nX O .\nX . 0", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(state);
        assertTrue(node.isLeaf());
        assertEquals(2, node.wins());
        assertEquals(1, node.playouts());
    }

    @Test
    public void state() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode node = new TicTacToeNode(state);
        assertEquals(state, node.state());
    }

    @Test
    public void white() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode node = new TicTacToeNode(state);
        assertTrue(node.white());
    }

    @Test
    public void children() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode node = new TicTacToeNode(state);
        assertTrue(node.children().isEmpty());
    }



    @Test
    public void addChild() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode parentNode = new TicTacToeNode(state);
        TicTacToeNode childNode = new TicTacToeNode(state);
        parentNode.addChild(state);
        assertFalse(parentNode.children().isEmpty());
        assertEquals(childNode.state(), parentNode.children().iterator().next().state());
    }


    @Test
    public void backPropagate() {
        TicTacToe.TicTacToeState state = new TicTacToe().new TicTacToeState();
        TicTacToeNode parentNode = new TicTacToeNode(state);

        TicTacToeNode child1 = new TicTacToeNode(state);
        child1.addWins(3);
        child1.incrementPlayouts();
        child1.incrementPlayouts();

        TicTacToeNode child2 = new TicTacToeNode(state);
        child2.addWins(1);
        child2.incrementPlayouts();

        parentNode.addChild(state);
        parentNode.addChild(state);

        parentNode.children().add(child1);
        parentNode.children().add(child2);

        parentNode.backPropagate();

        assertEquals("Playouts should be sum of children's playouts", 3, parentNode.playouts());
        assertEquals("Wins should be sum of children's wins", 4, parentNode.wins());
    }
}