package edu.neu.coe.info6205.mcts.tictactoe;


import edu.neu.coe.info6205.mcts.core.Node;
import edu.neu.coe.info6205.mcts.core.State;
import org.junit.Test;

import static org.junit.Assert.*;

public class MCTSTests {

    @Test
    public void testMCTSInitialMoveSelection() {
        TicTacToe game = new TicTacToe();
        State<TicTacToe> initialState = game.start();
        MCTS mcts = new MCTS(new TicTacToeNode(initialState));
        mcts.run(10);  // Run a small number of iterations for simplicity
        Node<TicTacToe> bestMove = mcts.bestChild(MCTS.root);
        assertFalse("Selected move should not be terminal", bestMove.state().isTerminal());
    }
    @Test
    public void testMCTSRunUpdates() {
        TicTacToe game = new TicTacToe();
        TicTacToeNode initialNode = new TicTacToeNode(game.start());
        MCTS mcts = new MCTS(initialNode);
        mcts.run(10);  // Run 10 simulations
        assertTrue("Root node playouts should be updated after running MCTS", MCTS.root.playouts() > 0);
    }
    @Test
    public void testMCTSBestMoveSelection() {
        TicTacToe game = new TicTacToe();
        TicTacToeNode initialNode = new TicTacToeNode(game.start());
        MCTS mcts = new MCTS(initialNode);
        mcts.run(100);  // Run enough simulations to ensure some statistical significance

        Node<TicTacToe> bestMove = mcts.bestChild(MCTS.root);
        assertNotNull(bestMove.toString(), "A best move should be selected from the root node");
        // Assuming some basic criteria or heuristic, like not choosing a losing move if avoidable
        assertTrue("The best move should not be a losing move if avoidable",
                bestMove.state().winner().isEmpty() || bestMove.state().winner().get() != -1);
    }
    @Test
    public void testMCTSSimulationOutcome() {
        TicTacToe game = new TicTacToe(); // Create an instance of the outer class
        // Now use the game instance to create the TicTacToeState
        State<TicTacToe> initialState = game.new TicTacToeState(Position.parsePosition("X X .\nO O .\n. . .", TicTacToe.X));
        TicTacToeNode node = new TicTacToeNode(initialState);
        MCTS mcts = new MCTS(node);

        int simulationResult = mcts.simulate(node);
        // Check if the result is correctly identified as a win for X or a draw
        assertTrue("Simulation should result in a win for X or a draw", simulationResult == TicTacToe.X || simulationResult == 0);
    }
    @Test
    public void testMCTSBackpropagationEffectiveness() {
        TicTacToe game = new TicTacToe();
        State<TicTacToe> initialState = game.start();
        TicTacToeNode rootNode = new TicTacToeNode(initialState);

        // Create and add a child node
        TicTacToeNode childNode = new TicTacToeNode(game.new TicTacToeState(new Position(new int[][]{{1, 1, -1}, {0, 0, -1}, {-1, -1, -1}}, 4, 0)));
        rootNode.addChild(childNode.state());  // Ensure this method sets child's parent
        childNode.setParent(rootNode);  // Explicitly set parent if addChild doesn't

        // Manually set simulation outcomes for the child node
        childNode.setWins(5);
        childNode.setPlayouts(10);

        // Check initial conditions before backpropagation
        System.out.println("Before backPropagate - Root playouts: " + rootNode.playouts() + ", Child playouts: " + childNode.playouts());

        // Run backpropagation from child to root
        MCTS mcts = new MCTS(rootNode);
        mcts.backPropagate(childNode, 1);  // Assume simulation result is a win for X

        // Check results after backpropagation
        System.out.println("After backPropagate - Root playouts: " + rootNode.playouts() + ", Child playouts: " + childNode.playouts());

        // Assertions to verify the correct behavior
        assertEquals("Root node playouts should match the total playouts of its children", 10, rootNode.playouts());
        assertEquals("Root node wins should match the wins of its children if backpropagation is correct", 5, rootNode.wins());
    }


}
