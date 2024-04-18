package edu.neu.coe.info6205.mcts.connectfour;

import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFour;
import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFourNode;
import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFourPosition;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConnectFourNodeTest {

    @Test
    public void winsAndPlayouts() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState(ConnectFourPosition.parsePosition(" . . . . . .\n . . . . . .\n . . . . . .\n . . . . . .\n . . . . . .\n X X X X . .",ConnectFour.X));
        ConnectFourNode node = new ConnectFourNode(state);
        assertTrue(node.isLeaf());
        assertEquals(2, node.wins());
        assertEquals(1, node.playouts());

    }
    @Test
    public void state() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState();
        ConnectFourNode node = new ConnectFourNode(state);
        assertEquals(state, node.state());
    }
    @Test
    public void white() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState();
        ConnectFourNode node = new ConnectFourNode(state);
        assertTrue(node.white());
    }

    @Test
    public void children() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState();
        ConnectFourNode node = new ConnectFourNode(state);
        assertTrue(node.children().isEmpty());
    }



    @Test
    public void addChild() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState();
        ConnectFourNode parentNode = new ConnectFourNode(state);
        ConnectFourNode childNode = new ConnectFourNode(state);
        parentNode.addChild(state);
        assertFalse(parentNode.children().isEmpty());
        assertEquals(childNode.state(), parentNode.children().iterator().next().state());
    }


    @Test
    public void backPropagate() {
        ConnectFour.ConnectFourState state = new ConnectFour().new ConnectFourState();
        ConnectFourNode parentNode = new ConnectFourNode(state);

        ConnectFourNode child1 = new ConnectFourNode(state);
        child1.addWins(3);
        child1.incrementPlayouts();
        child1.incrementPlayouts();

        ConnectFourNode child2 = new ConnectFourNode(state);
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
