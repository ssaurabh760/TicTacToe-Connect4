package edu.neu.coe.info6205.mcts.connectfour;
import edu.neu.coe.info6205.mcts.ConnectFour.ConnectFourPosition;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ConnectFourPositionTest {
    @Test
    public void testParseCell() {
        assertEquals(0, ConnectFourPosition.parseCell("O"));
        assertEquals(0, ConnectFourPosition.parseCell("0"));
        assertEquals(1, ConnectFourPosition.parseCell("X"));
        assertEquals(1, ConnectFourPosition.parseCell("1"));
        assertEquals(-1, ConnectFourPosition.parseCell(" "));
        assertEquals(-1, ConnectFourPosition.parseCell("-"));
    }
    @Test
    public void testParsePosition() {
        String grid = "O - O - O - O\n" +
                              "X - X - X - X\n" +
                              "O - O - O - O\n" +
                              "X - X - X - X\n" +
                              "O - O - O - O\n" +
                              "X - X - X - X";
        ConnectFourPosition position = ConnectFourPosition.parsePosition(grid, 1);
        assertNotNull(position);
        assertFalse(position.full());
    }
    @Test
    public void testWinner() {
        String grid = "O O O O - - -\n" +
                              "X - - - X - -\n" +
                              "X - - X - - -\n" +
                              "X - X - - - -\n" +
                              "O - - - - - -\n" +
                              "O - - - - - -";
        ConnectFourPosition position = ConnectFourPosition.parsePosition(grid, 0);
        Optional<Integer> winner = position.winner();
        assertTrue(winner.isPresent());
        assertEquals(0, (int) winner.get());
    }

    @Test
    public void testFull() {
        String grid = "O O O O O O O\n" +
                              "X X X X X X X\n" +
                              "O O O O O O O\n" +
                              "X X X X X X X\n" +
                              "O O O O O O O\n" +
                              "X X X X X X X";
        ConnectFourPosition position = ConnectFourPosition.parsePosition(grid, 1);
        assertTrue(position.full());
    }
    @Test
    public void testMove() {
        int[][] grid = {
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1}
        };
        ConnectFourPosition position = new ConnectFourPosition(grid, 0, -1);
        ConnectFourPosition newPosition = position.move(0, 3);
        assertEquals(0, newPosition.grid[5][3]);
    }
    @Test(expected = RuntimeException.class)
    public void testMoveColumnFull() {
        int[][] grid = {
                {-1, -1, -1, -1, -1, -1, 1},
                {-1, -1, -1, -1, -1, -1, 0},
                {-1, -1, -1, -1, -1, -1, 1},
                {-1, -1, -1, -1, -1, -1, 0},
                {-1, -1, -1, -1, -1, -1, 1},
                {-1, -1, -1, -1, -1, -1, 0}
        };
        ConnectFourPosition position = new ConnectFourPosition(grid, 0, -1);
        position.move(1, 6);
    }


    @Test
    public void testMoves() {
        int[][] grid = {
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1}
        };
        ConnectFourPosition position = new ConnectFourPosition(grid, 0, -1);
        List<Integer> moves = position.moves(0);
        assertEquals(7, moves.size());
        assertTrue(moves.contains(0));
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
    }
    @Test
    public void testReflect() {
        int[][] grid = {
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1}
        };
        ConnectFourPosition position = new ConnectFourPosition(grid, 0, -1);
        ConnectFourPosition reflectedPosition = position.reflect(0);
        assertArrayEquals(position.grid, reflectedPosition.grid);
    }


}


