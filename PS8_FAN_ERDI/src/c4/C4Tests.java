package c4;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class C4Tests
{
    // Create a board contains cols less than 4
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBoardCol ()
    {
        new C4Board(3, 9);
    }

    // Create a board contains rows less than 4
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBoardRow ()
    {
        new C4Board(9, 3);
    }

    // Test C4Board which meets the requirement
    @Test
    public void testNormalBoard ()
    {
        new C4Board(5, 5);
    }

    // Create a C4Board
    C4Board cb = new C4Board(4, 4);

    // Test C4Board is initialized correctly
    @Test
    public void testInitialization ()
    {
        assertEquals(0, cb.getOccupant(0, 0));
        assertEquals(0, cb.getOccupant(0, 1));
        assertEquals(0, cb.getOccupant(0, 2));
        assertEquals(0, cb.getOccupant(0, 3));
        assertEquals(0, cb.getOccupant(1, 0));
        assertEquals(0, cb.getOccupant(1, 1));
        assertEquals(0, cb.getOccupant(1, 2));
        assertEquals(0, cb.getOccupant(1, 3));
        assertEquals(0, cb.getOccupant(2, 0));
        assertEquals(0, cb.getOccupant(2, 1));
        assertEquals(0, cb.getOccupant(2, 2));
        assertEquals(0, cb.getOccupant(2, 3));
        assertEquals(0, cb.getOccupant(3, 0));
        assertEquals(0, cb.getOccupant(3, 1));
        assertEquals(0, cb.getOccupant(3, 2));
        assertEquals(0, cb.getOccupant(3, 3));

        // Check that the first player to move is P1
        assertEquals(1, cb.getToMove());

        // Check that the number of different overcomes are all 0
        assertEquals(0, cb.getTies());
        assertEquals(0, cb.getWinsForP1());
        assertEquals(0, cb.getWinsForP2());

        // P1 add a piece to the column
        assertEquals(0, cb.moveTo(0));

        // Check that P1 added a piece to the (0,0)
        assertEquals(1, cb.getOccupant(0, 0));
    }

    // Test newGame
    @Test
    public void testNewGame ()
    {
        cb.newGame();

        // Check if the board is cleared
        assertEquals(0, cb.getOccupant(0, 0));
        assertEquals(0, cb.getOccupant(0, 1));
        assertEquals(0, cb.getOccupant(0, 2));
        assertEquals(0, cb.getOccupant(0, 3));
        assertEquals(0, cb.getOccupant(1, 0));
        assertEquals(0, cb.getOccupant(1, 1));
        assertEquals(0, cb.getOccupant(1, 2));
        assertEquals(0, cb.getOccupant(1, 3));
        assertEquals(0, cb.getOccupant(2, 0));
        assertEquals(0, cb.getOccupant(2, 1));
        assertEquals(0, cb.getOccupant(2, 2));
        assertEquals(0, cb.getOccupant(2, 3));
        assertEquals(0, cb.getOccupant(3, 0));
        assertEquals(0, cb.getOccupant(3, 1));
        assertEquals(0, cb.getOccupant(3, 2));
        assertEquals(0, cb.getOccupant(3, 3));

        // Check that player is changed
        assertEquals(2, cb.getToMove());

        // Check that the number of different overcomes are all 0
        assertEquals(0, cb.getTies());
        assertEquals(0, cb.getWinsForP1());
        assertEquals(0, cb.getWinsForP2());

        // P2 add a piece to the column
        assertEquals(0, cb.moveTo(0));

        // Check that P2 added a piece to the (0,0)
        assertEquals(2, cb.getOccupant(0, 0));
    }

    // Test outcome throws an exception
    @Test(expected = IllegalArgumentException.class)
    public void testOutcomeException1 ()
    {
        cb.moveTo(-1);
    }

    // Test outcome throws an exception
    @Test(expected = IllegalArgumentException.class)
    public void testOutcomeException2 ()
    {
        cb.moveTo(4);
    }

    // Test outcome
    @Test
    public void testOutcome ()
    {
        // Check that P1 wins the game and get credits
        assertEquals(0, cb.moveTo(0));
        assertEquals(0, cb.moveTo(1));

        assertEquals(0, cb.moveTo(0));
        assertEquals(0, cb.moveTo(2));

        assertEquals(0, cb.moveTo(0));
        assertEquals(0, cb.moveTo(3));

        assertEquals(1, cb.moveTo(0));

        // Check that the number of wins for P1 has increased to 1, other results are not changed
        assertEquals(1, cb.getWinsForP1());
        assertEquals(0, cb.getWinsForP2());
        assertEquals(0, cb.getTies());

        // Check return 0 when hasWonOrTied is true
        assertEquals(0, cb.moveTo(3));
        assertEquals(0, cb.getToMove());

        cb.newGame();

        // Check that player is changed
        assertEquals(2, cb.getToMove());

        // Check that P2 wins the game and get credits
        assertEquals(0, cb.moveTo(1));
        assertEquals(0, cb.moveTo(0));

        assertEquals(0, cb.moveTo(1));
        assertEquals(0, cb.moveTo(2));

        assertEquals(0, cb.moveTo(1));
        assertEquals(0, cb.moveTo(3));

        assertEquals(2, cb.moveTo(1));

        // Check that the number of wins for P2 has increased to 1, other results are not changed
        assertEquals(1, cb.getWinsForP1());
        assertEquals(1, cb.getWinsForP2());
        assertEquals(0, cb.getTies());

        // Check return 0 when hasWonOrTied is true
        assertEquals(0, cb.moveTo(3));

        cb.newGame();

        // Check that player is changed
        assertEquals(1, cb.getToMove());

        assertEquals(0, cb.moveTo(0));
        assertEquals(0, cb.moveTo(1));

        assertEquals(0, cb.moveTo(2));
        assertEquals(0, cb.moveTo(0));

        assertEquals(0, cb.moveTo(1));
        assertEquals(0, cb.moveTo(3));

        assertEquals(0, cb.moveTo(3));
        assertEquals(0, cb.moveTo(2));

        assertEquals(0, cb.moveTo(1));
        assertEquals(0, cb.moveTo(0));

        assertEquals(0, cb.moveTo(0));
        assertEquals(0, cb.moveTo(2));

        assertEquals(0, cb.moveTo(2));
        assertEquals(0, cb.moveTo(3));

        assertEquals(0, cb.moveTo(3));
        assertEquals(3, cb.moveTo(1));

        // Check that the number of ties has increased to 1, other results are not changed
        assertEquals(1, cb.getWinsForP1());
        assertEquals(1, cb.getWinsForP2());
        assertEquals(1, cb.getTies());

        // Check if the column is full, returns 0
        assertEquals(0, cb.moveTo(0));
    }

    // Check if the row or column doesn't exist, throws an IllegalArgumentException in the method getOccupant
    @Test(expected = IllegalArgumentException.class)
    public void testGetOccupantException1 ()
    {
        cb.getOccupant(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOccupantException2 ()
    {
        cb.getOccupant(1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOccupantException3 ()
    {
        cb.getOccupant(1, -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOccupantException4 ()
    {
        cb.getOccupant(5, 1);
    }

    // Check the normal case of the method getOccupant
    @Test
    public void testGetOccupant ()
    {
        assertEquals(0, cb.getOccupant(1, 1));
    }
}
