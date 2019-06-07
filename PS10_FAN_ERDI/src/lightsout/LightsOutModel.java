package lightsout;

import java.util.Random;

/**
 * Represents the state of a Lights Out board on which multiple games can be played. The board consists of a rectangular
 * grid of squares in which "lights" status can be illustrated. Squares are identified by their zero-based row and
 * column numbers, where rows are numbered starting with the bottom row, and columns are numbered by starting from the
 * leftmost column.
 * 
 * Multiple games can be played on a single board. Whenever a new game begins, the board is given in a random
 * combination. There are two modes, identified by the Enter Manual Setup button and Exit Manual Setup button. In manual
 * mode, clicking on a grid control should toggle only the grid control being clicked, not any of its neighbors. When
 * the "Exit Manual Set" button is clicked, the button text should change back and normal game play should resume. The
 * purpose of manual setup mode is to allow a player to configure a game any way he or she pleases.
 * 
 * A LightsOutModel also keeps track of the outcomes of the games that have been played on it. It tracks the number of
 * wins by P1, the number of wins by P2, and the number of ties. It does not track games that were abandoned before
 * being completed.
 */

public class LightsOutModel
{
    /** The number used to indicate ON status */
    public static final int ON = 1;

    /** The number used to indicate OFF status */
    public static final int OFF = 2;

    /** The created LightsOutModel */
    private int[][] board;

    /** The manual/default mode */
    private boolean isManual;

    /** The number of rows */
    private int row;

    /** The number of cols */
    private int col;

    /**
     * Creates a Lights Out model with the specified number of rows and columns. There should be random status of lights
     * (solvable) pieces on the board.
     * 
     * However, if either rows or cols is not five, throws an IllegalArgumentException.
     */
    public LightsOutModel (int rows, int cols)
    {
        row = rows;
        col = cols;
        if (row != 5 || col != 5)
        {
            throw new IllegalArgumentException();
        }

        // Create a board
        board = new int[row][col];

        isManual = false;
        newGame();
    }

    /**
     * Sets up the board to play a new game, whether or not the current game is complete. All of the pieces should be
     * randomly set.
     */
    public void newGame ()
    {
        turnOff();

        // Turn on some lights randomly, there must be at least 5 lights are still off
        for (int k = 0; k < 25; k++)
        {
            Random r = new Random();
            status(r.nextInt(5), r.nextInt(5));
        }
    }

    /**
     * Initialize the board to turn off all the lights firstly
     */

    public void turnOff ()
    {
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                board[i][j] = OFF;
            }
        }
    }

    /**
     * In LightsStatus method, there are two different modes. In default mode, when you click on a light, the on/off
     * state of that light---as well as the lights above, below, to the left, and to the right of it---toggle. In manual
     * mode, clicking on a grid control should toggle only the grid control being clicked, not any of its neighbors.
     */
    public void status (int rows, int cols)
    {
        final int above = rows - 1;
        final int below = rows + 1;
        final int left = cols - 1;
        final int right = cols + 1;
        if (isManual)
        {
            changeStatus(rows, cols);
        }
        else
        {
            changeStatus(rows, cols);
            if (right < 5)
            {
                changeStatus(rows, right);
            }
            if (left >= 0)
            {
                changeStatus(rows, left);
            }
            if (below < 5)
            {
                changeStatus(below, cols);
            }
            if (above >= 0)
            {
                changeStatus(above, cols);
            }
        }
    }

    public void changeStatus (int rows, int cols)
    {
        if (board[rows][cols] == ON)
        {
            board[rows][cols] = OFF;
        }
        else
        {
            board[rows][cols] = ON;
        }
    }

    /**
     * Reports the status of the board square at the specified row and column. If the row or column doesn't exist,
     * throws an IllegalArgumentException. If the light is on, returns 1. Otherwise, returns 2.
     */
    public int getStatus (int rows, int cols)
    {
        // Throw IllegalArgumentException when the row or column does not exist
        if (cols < 0 || cols >= this.col || rows < 0 || rows >= this.row)
        {
            throw new IllegalArgumentException();
        }
        return board[rows][cols];
    }

    /**
     * Check if the mode is manual
     */
    public boolean isManual ()
    {
        return isManual;
    }

    /**
     * Change the mode to manual if needed
     */
    public void changeToManual (boolean change)
    {
        isManual = change;
    }

    /**
     * Determine the player has won or not
     */
    public boolean hasWon ()
    {
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                if (board[i][j] == ON)
                    return false;
            }
        }
        return true;
    }
}
