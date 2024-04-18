package edu.neu.coe.info6205.mcts.connectfour;

import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFour;
import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFourMove;
import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFourNode;
import edu.neu.coe.info6205.mcts.ConnectFour.MCTS;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MCTSTest {

    @Test
    public void testInitialization() {
        ConnectFour connectFourGame = new ConnectFour();
        ConnectFourNode root = new ConnectFourNode(connectFourGame.start());
        MCTS mcts = new MCTS(root);
        assertNotNull(mcts);
        assertNotNull(root);
    }

    @Test
    public void testBackPropagate() {
        ConnectFour connectFourGame = new ConnectFour();
        ConnectFourNode root = new ConnectFourNode(connectFourGame.start());
        MCTS mcts = new MCTS(root);
        ConnectFourNode childNode = new ConnectFourNode(connectFourGame.start());
        mcts.backPropagate(childNode, 1); // Simulate a win
        assertEquals(2, childNode.wins());
    }



    @Test
    public void testMainRunMethod() {
        ConnectFour connectFourGame = new ConnectFour();
        ConnectFourNode root = new ConnectFourNode(connectFourGame.start());
        MCTS mcts = new MCTS(root);
        mcts.run(100);
        assertNotNull(mcts.bestChild(root));
    }


}
