package lightsout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LightsOutModelTests
{
    // Throw exception when rows & cols are not 5
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalRows ()
    {
        new LightsOutModel(1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCols ()
    {
        new LightsOutModel(5, 2);
    }

    // Test if cols and rows are both 5
    @Test
    public void testCorrectRowsCols ()
    {
        new LightsOutModel(5, 5);
    }

    // Create a new board
    LightsOutModel l = new LightsOutModel(5, 5);

    // Test for a new game
    @Test
    public void testNewGame ()
    {
        // Test if the default mode is not manual mode and it has not won yet
        assertFalse(l.hasWon());
        assertFalse(l.isManual());

        // Test I can get the status of all valid grids
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                l.getStatus(i, j);
            }
        }

        // Turn off all the lights
        l.turnOff();
        assertTrue(l.hasWon());

        // In default mode, test when I click on one grid, all of its adjacent grids and itself will change status
        l.status(2, 2);
        assertEquals(1, l.getStatus(2, 2));
        assertEquals(1, l.getStatus(1, 2));
        assertEquals(1, l.getStatus(3, 2));
        assertEquals(1, l.getStatus(2, 1));
        assertEquals(1, l.getStatus(2, 3));

        // Other grids will not change their status
        assertEquals(2, l.getStatus(0, 0));
        assertEquals(2, l.getStatus(0, 1));
        assertEquals(2, l.getStatus(0, 2));
        assertEquals(2, l.getStatus(0, 3));
        assertEquals(2, l.getStatus(0, 4));
        assertEquals(2, l.getStatus(1, 0));
        assertEquals(2, l.getStatus(1, 1));
        assertEquals(2, l.getStatus(1, 3));
        assertEquals(2, l.getStatus(1, 4));
        assertEquals(2, l.getStatus(2, 0));
        assertEquals(2, l.getStatus(2, 4));
        assertEquals(2, l.getStatus(3, 0));
        assertEquals(2, l.getStatus(3, 1));
        assertEquals(2, l.getStatus(3, 3));
        assertEquals(2, l.getStatus(3, 4));
        assertEquals(2, l.getStatus(4, 0));
        assertEquals(2, l.getStatus(4, 1));
        assertEquals(2, l.getStatus(4, 2));
        assertEquals(2, l.getStatus(4, 3));
        assertEquals(2, l.getStatus(4, 4));

        assertFalse(l.hasWon());

        // click on it again, change back to off status
        l.status(2, 2);
        assertEquals(2, l.getStatus(2, 2));
        assertEquals(2, l.getStatus(1, 2));
        assertEquals(2, l.getStatus(3, 2));
        assertEquals(2, l.getStatus(2, 1));
        assertEquals(2, l.getStatus(2, 3));

        // Change to manual mode
        l.changeToManual(true);
        assertTrue(l.isManual());

        // In manual mode, test when I click on one grid, only itself will change status
        l.status(2, 2);
        assertEquals(1, l.getStatus(2, 2));

        // Other grids will not change their status
        assertEquals(2, l.getStatus(0, 0));
        assertEquals(2, l.getStatus(0, 1));
        assertEquals(2, l.getStatus(0, 2));
        assertEquals(2, l.getStatus(0, 3));
        assertEquals(2, l.getStatus(0, 4));
        assertEquals(2, l.getStatus(1, 0));
        assertEquals(2, l.getStatus(1, 1));
        assertEquals(2, l.getStatus(1, 2));
        assertEquals(2, l.getStatus(1, 3));
        assertEquals(2, l.getStatus(1, 4));
        assertEquals(2, l.getStatus(2, 0));
        assertEquals(2, l.getStatus(2, 1));
        assertEquals(2, l.getStatus(2, 3));
        assertEquals(2, l.getStatus(2, 4));
        assertEquals(2, l.getStatus(3, 0));
        assertEquals(2, l.getStatus(3, 1));
        assertEquals(2, l.getStatus(3, 2));
        assertEquals(2, l.getStatus(3, 3));
        assertEquals(2, l.getStatus(3, 4));
        assertEquals(2, l.getStatus(4, 0));
        assertEquals(2, l.getStatus(4, 1));
        assertEquals(2, l.getStatus(4, 2));
        assertEquals(2, l.getStatus(4, 3));
        assertEquals(2, l.getStatus(4, 4));

        // Test changeStatus
        l.changeStatus(2, 2);
        assertEquals(2, l.getStatus(2, 2));
    }

    // Tests for getStatus method that has illegal parameter (rows or cols are out of bound)
    @Test(expected = IllegalArgumentException.class)
    public void testGetStatusOutOfBounds ()
    {
        l.getStatus(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStatusOutOfBounds2 ()
    {
        l.getStatus(1, -3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStatusOutOfBounds3 ()
    {
        l.getStatus(1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStatusOutOfBounds4 ()
    {
        l.getStatus(6, 3);
    }

}
