package c4;

/**
 * Represents the state of a Connect Four board on which multiple games can be played. The board consists of a
 * rectangular grid of squares in which playing pieces can be placed. Squares are identified by their zero-based row and
 * column numbers, where rows are numbered starting with the bottom row, and columns are numbered by starting from the
 * leftmost column.
 * 
 * Multiple games can be played on a single board. Whenever a new game begins, the board is empty. There are two
 * players, identified by the constants P1 (Player 1) and P2 (Player 2). P1 moves first in the first game, P2 moves
 * first in the second game, and so on in alternating fashion.
 * 
 * A C4Board also keeps track of the outcomes of the games that have been played on it. It tracks the number of wins by
 * P1, the number of wins by P2, and the number of ties. It does not track games that were abandoned before being
 * completed.
 */
public class C4Board
{
    /** The number used to indicate Player 1 */
    public static final int P1 = 1;

    /** The number used to indicate Player 2 */
    public static final int P2 = 2;

    /** The number used to indicate a tie */
    public static final int TIE = 3;

    /** The created C4Board */
    private int[][] C4Board;

    /** The number of rows */
    private int row;

    /** The number of cols */
    private int col;

    /** The current turn */
    private int turn;

    /** The numbers of win or tie */
    private int P1Win;
    private int P2Win;
    private int tie;

    /** Outcome of one match */
    private boolean hasWonOrTied;

    /** Mark the row index for specific column */
    private int[] rowIndex;

    /**
     * Creates a C4Board with the specified number of rows and columns. There should be no pieces on the board and it
     * should be player 1's turn to move.
     * 
     * However, if either rows or cols is less than four, throws an IllegalArgumentException.
     */
    public C4Board (int rows, int cols)
    {
        row = rows;
        col = cols;
        if (row < 4 || col < 4)
        {
            throw new IllegalArgumentException();
        }

        // Create a C4Board
        C4Board = new int[row][col];

        // Initialize the board
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                C4Board[i][j] = 0;
            }
        }

        // First player's turn to move
        turn = P1;

        // Initialize the numbers of win or tie
        P1Win = 0;
        P2Win = 0;
        tie = 0;

        // Initialize win condition
        hasWonOrTied = false;

        // Mark the index of rows of specific column
        rowIndex = new int[this.col];

    }

    /**
     * Sets up the board to play a new game, whether or not the current game is complete. All of the pieces should be
     * removed from the board. The player who had the second move in the previous game should have the first move in the
     * new game.
     */
    public void newGame ()
    {
        // Initialize the board
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                C4Board[i][j] = 0;
            }
        }
        turn = changeFirstPlayer(turn);
        hasWonOrTied = false;
        rowIndex = new int[this.col];
    }

    /** Change the first player for newGame */
    private int changeFirstPlayer (int currFirst)
    {
        if (currFirst == P1)
        {
            return P2;
        }
        else
        {
            return P1;
        }
    }

    /**
     * Records a move, by the player whose turn it is to move, in the first open square in the specified column. Returns
     * P1 if the move resulted in a win for player 1, returns P2 if the move resulted in a win for player 2, returns TIE
     * if the move resulted in a tie, and returns 0 otherwise.
     * 
     * If the column is full, or if the game is over because a win or a tie has previously occurred, does nothing but
     * return zero.
     * 
     * If a non-existent column is specified, throws an IllegalArgumentException.
     */
    public int moveTo (int col)
    {
        // Throw an IllegalArgumentException when a non-existent column is specified
        if (col < 0 || col >= this.col)
        {
            throw new IllegalArgumentException();
        }

        int currRowNum = rowIndex[col];

        // Return 0 when the column is full, or if the game is over because a win or a tie has previously occurred
        if (currRowNum == this.row || hasWonOrTied == true)
        {
            return 0;
        }

        // Record the place where the current player moved their piece to
        C4Board[currRowNum][col] = turn;
        rowIndex[col]++;

        // Check the overcome of this game, return the result
        if (FourInARow.fourInRow(C4Board))
        {
            hasWonOrTied = true;
            if (turn == P1)
            {
                P1Win++;
                return P1;
            }
            if (turn == P2)
            {
                P2Win++;
                return P2;
            }
        }

        if (isTie(rowIndex) == true)
        {
            hasWonOrTied = true;
            tie++;
            return TIE;
        }

        turn = changeFirstPlayer(turn);
        return 0;
    }

    /** check if this game is a tie */
    private boolean isTie (int[] index)
    {
        for (int i : index)
        {
            if (i != this.row)
                return false;
        }
        return true;
    }

    /**
     * Reports the occupant of the board square at the specified row and column. If the row or column doesn't exist,
     * throws an IllegalArgumentException. If the square is unoccupied, returns 0. Otherwise, returns P1 (if the square
     * is occupied by player 1) or P2 (if the square is occupied by player 2).
     */
    public int getOccupant (int row, int col)
    {
        // Throw IllegalArgumentException when the row or column does not exist
        if (col < 0 || col >= this.col || row < 0 || row >= this.row)
        {
            throw new IllegalArgumentException();
        }
        return C4Board[row][col];
    }

    /**
     * Reports whose turn it is to move. Returns P1 (if it is player 1's turn to move), P2 (if it is player 2's turn to
     * move, or 0 (if it is neither player's turn to move because the current game is over because of a win or tie).
     */
    public int getToMove ()
    {
        if (!hasWonOrTied)
        {
            return turn;
        }
        return 0;
    }

    /**
     * Reports how many games have been won by player 1 since this board was created.
     */
    public int getWinsForP1 ()
    {
        return P1Win;
    }

    /**
     * Reports how many games have been won by player 2 since this board was created.
     */
    public int getWinsForP2 ()
    {
        return P2Win;
    }

    /**
     * Reports how many ties have occurred since this board was created.
     */
    public int getTies ()
    {
        return tie;
    }
}
