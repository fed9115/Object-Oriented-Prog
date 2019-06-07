package lightsout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static lightsout.LightsOutView.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Implements a Lights out game with a GUI interface.
 * 
 * @author Erdi Fan
 */

@SuppressWarnings("serial")
public class LightsOutView extends JFrame implements ActionListener
{
    // Some formatting constants
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    public final static int ROWS = 5;
    public final static int COLS = 5;
    public final static Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public final static Color ON_COLOR = Color.WHITE;
    public final static String ON_COLOR_NAME = "White";
    public final static Color OFF_COLOR = Color.GRAY;
    public final static String OFF_COLOR_NAME = "Gray";
    public final static Color BOARD_COLOR = Color.BLACK;
    public final static Color TIE_COLOR = Color.WHITE;
    public final static int BORDER = 5;
    public final static int FONT_SIZE = 20;
    public final static int ON = 1;
    public final static int OFF = 2;

    /** The "smarts" of the game **/
    private LightsOutModel model;
    /** The portion of the GUI that contains the playing surface **/
    private Board board;

    /** Change the manual mode on or off */
    private JButton manualMode;

    public LightsOutView ()
    {
        // Set the title that appears at the top of the window
        setTitle("CS 1410 Lights Out");

        // Cause the application to end when the windows is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give the window its initial dimensions
        setSize(WIDTH, HEIGHT);

        // The root panel contains everything
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // The center portion contains the playing board
        model = new LightsOutModel(ROWS, COLS);
        board = new Board(model, this);
        root.add(board, "Center");

        // Creates a JPanel that contains buttons
        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());

        // Creates a New Game button
        JButton newGame = new JButton("New Game");

        newGame.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        newGame.setBackground(BACKGROUND_COLOR);
        newGame.addActionListener(this);
        button.add(newGame);

        // Creates a Manual Mode Button
        manualMode = new JButton("Enter Manual Setup");

        manualMode.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
        manualMode.setBackground(BACKGROUND_COLOR);
        manualMode.addActionListener(this);
        button.add(manualMode);

        root.add(button, "South");

        // Refresh the display and we're ready
        board.refresh();
        setVisible(true);

    }

    /**
     * Called when the New Game button is clicked. Tells the model to begin a new game, then refreshes the display.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getActionCommand().equals("New Game"))
        {
            if (model.isManual())
            {
                model.changeToManual(false);
                manualMode.setText("Enter Manual Setup");
            }
            model.newGame();
            board.refresh();
        }
        else
        {
            if (!model.isManual())
            {
                model.changeToManual(true);
                manualMode.setText("Exit Manual Setup");
            }
            else
            {
                model.changeToManual(false);
                manualMode.setText("Enter Manual Setup");
            }
        }
    }
}

/**
 * The playing surface of the game.
 */
@SuppressWarnings("serial")
class Board extends JPanel implements MouseListener
{
    /** The "smarts" of the game */
    private LightsOutModel model;

    /** The top level GUI for the game */
    private LightsOutView view;

    /**
     * Creates the board portion of the GUI.
     */
    public Board (LightsOutModel model, LightsOutView view)
    {
        // Record the model and the top-level display
        this.model = model;
        this.view = view;

        // Set the background color and the layout
        setBackground(BOARD_COLOR);
        setLayout(new GridLayout(ROWS, COLS));

        // Create and lay out the grid of squares that make up the game.
        for (int i = ROWS - 1; i >= 0; i--)
        {
            for (int j = 0; j < COLS; j++)
            {
                Square s = new Square(i, j);
                s.addMouseListener(this);
                add(s);
            }
        }
    }

    /**
     * Refreshes the display. This should be called whenever something changes in the model.
     */
    public void refresh ()
    {
        // Iterate through all of the squares that make up the grid
        Component[] squares = getComponents();
        for (Component c : squares)
        {
            Square s = (Square) c;

            // Set the color of the squares appropriately
            int status = model.getStatus(s.getRow(), s.getCol());
            if (status == ON)
            {
                s.setColor(ON_COLOR);
            }
            else if (status == OFF)
            {
                s.setColor(OFF_COLOR);
            }
            else
            {
                s.setColor(BACKGROUND_COLOR);
            }
        }

        // Ask that this board be repainted
        repaint();
    }

    /**
     * Called whenever a Square is clicked. Notifies the model that a move has been attempted.
     */
    @Override
    public void mouseClicked (MouseEvent e)
    {
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        Square s = (Square) e.getSource();
        model.status(s.getRow(), s.getCol());
        refresh();
        if (model.hasWon() && (!model.isManual()))
        {
            JOptionPane.showMessageDialog(this, "Win! Conguatulations!");
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }
}

/**
 * A single square on the board where a move can be made
 */
@SuppressWarnings("serial")
class Square extends JPanel
{
    /** The row within the board of this Square. Rows are numbered from zero; row zero is at the bottom of the board. */
    private int row;

    /** The column within the board of this Square. Columns are numbered from zero; column zero is at the left */
    private int col;

    /** The current Color of this Square */
    private Color color;

    /**
     * Creates a square and records its row and column
     */
    public Square (int row, int col)
    {
        this.row = row;
        this.col = col;
        this.color = BACKGROUND_COLOR;
    }

    /**
     * Returns the row of this Square
     */
    public int getRow ()
    {
        return row;
    }

    /**
     * Returns the column of this Square
     */
    public int getCol ()
    {
        return col;
    }

    /**
     * Sets the color of this square
     */
    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this Square onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.fillOval(BORDER, BORDER, getWidth() - 2 * BORDER, getHeight() - 2 * BORDER);
    }
}
