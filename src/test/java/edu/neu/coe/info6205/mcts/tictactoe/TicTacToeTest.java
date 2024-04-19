package edu.neu.coe.info6205.mcts.tictactoe;

import edu.neu.coe.info6205.mcts.core.State;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.Assert.*;

public class TicTacToeTest {

    /**
     *
     */
    @Test
    public void runGame() {
        long seed = 0L;
        TicTacToe target = new TicTacToe(seed);
        int mctsWon = 0;
        int randomWon = 0;
        int draw = 0;
        for(int i = 0; i < 100; i++){
            State<TicTacToe> state = target.runGame();
            Optional<Integer> winner = state.winner();
            if(winner.isPresent()){
                if(winner.get() == Integer.valueOf(TicTacToe.X)) mctsWon +=1;
                else if (winner.get() == Integer.valueOf(TicTacToe.O)) randomWon += 1;
            }

            else draw +=1;
        }
        System.out.println("MCTS won " + mctsWon);
        System.out.println("Random won " + randomWon);
        System.out.println("Draw " + draw);


//        if (winner.isPresent()) assertEquals(Integer.valueOf(TicTacToe.O), winner.get());
//        else fail("no winner");
    }
    @Test
    public void testPlayerAlternation() {
        TicTacToe game = new TicTacToe();
        State<TicTacToe> initialState = game.start();
        int firstPlayer = initialState.player();

        State<TicTacToe> nextState = initialState.next(initialState.chooseMove(firstPlayer));
        assertNotEquals(firstPlayer, nextState.player());
    }

    @Test
    public void testTerminalState() {
        TicTacToe game = new TicTacToe();
        State<TicTacToe> state = game.runGame();
        assertTrue(state.isTerminal());
    }

    @Test
    public void testMain() {
        // Redirect console output for testing
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run the main method
        String[] args = {};
        TicTacToe.main(args);

        // Get the output from the console
        String output = outputStream.toString().trim();

        // Assert that the output matches expectations
        assertTrue(output.contains("TicTacToe: winner is:") || output.contains("TicTacToe: draw"));

        // Reset console output
        System.setOut(System.out);
    }

    @Test
    public void testStartingPosition() {

        Position startingPos = TicTacToe.startingPosition();

        assertNotNull(startingPos);

        assertEquals(". . .\n. . .\n. . .", startingPos.render());
    }

    @Test
    public void testPosition() {
        // Create a TicTacToeState object with a specific position
        Position expectedPosition = Position.parsePosition("X . .\n. O .\n. . X", TicTacToe.blank);
        TicTacToe.TicTacToeState ticTacToeState = new TicTacToe().new TicTacToeState(expectedPosition);

        // Get the position using the position() method
        Position actualPosition = ticTacToeState.position();

        // Assert that the actual position matches the expected position
        assertEquals(expectedPosition, actualPosition);
    }

}