package edu.neu.coe.info6205.mcts.connectfour;

import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFour;
import edu.neu.coe.info6205.mcts.core.State;
import org.junit.Test;

import java.util.Optional;

import static com.phasmidsoftware.number.core.FP.fail;
import static org.junit.Assert.*;

public class ConnectFourTest {
    @Test
    public void runGame() {
        long seed = 8L;
        ConnectFour target = new ConnectFour(seed); // games run here will all be deterministic.
        State<ConnectFour> state = target.runGame();
        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) assertEquals(Integer.valueOf(ConnectFour.X), winner.get());
        else fail("no winner");
    }
    @Test
    public void testPlayerAlternation() {
        ConnectFour game = new ConnectFour();
        State<ConnectFour> initialState = game.start();
        int firstPlayer = initialState.player();

        State<ConnectFour> nextState = initialState.next(initialState.chooseMove(firstPlayer));
        assertNotEquals(firstPlayer, nextState.player());
    }

    @Test
    public void testTerminalState() {
        ConnectFour game = new ConnectFour();
        State<ConnectFour> state = game.runGame();
        assertTrue(state.isTerminal());
    }

}