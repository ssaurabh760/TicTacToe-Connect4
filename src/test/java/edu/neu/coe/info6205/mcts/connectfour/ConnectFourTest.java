package edu.neu.coe.info6205.mcts.connectfour;

import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFour;
import edu.neu.coe.info6205.mcts.core.State;
import org.junit.Test;

import java.util.Optional;

public class ConnectFourTest {
    @Test
    public void runGameMultiple() {
        long seed = 2L;
        ConnectFour target = new ConnectFour(seed); // games run here will all be deterministic.
        int firstPlayerCount = 0;
        int seconfPlayerCount = 0;
        for (int i=0; i < 10; i++){
            State<ConnectFour> state = target.runGame();
            Optional<Integer> winner = state.winner();
            if(winner.get() == Integer.valueOf(ConnectFour.X)){
                firstPlayerCount += 1;
            } else if (winner.get() == Integer.valueOf(ConnectFour.O)) {
                seconfPlayerCount += 1;
            }

        }
        System.out.println("MCTS: " + firstPlayerCount);
        System.out.println("Random: " + seconfPlayerCount);
//        if (winner.isPresent()) assertEquals(Integer.valueOf(ConnectFour.X), winner.get());
//        else fail("no winner");
    }
}
