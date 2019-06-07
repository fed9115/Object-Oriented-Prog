package cs1410;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Demonstrates nested layout in a Swing application
 * 
 * @author Joe Zachary
 */
public class TicTacToe
{
    /**
     * Launches the TicTacToe board
     */
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new TicTacToe());
    }

    /**
     * Lays out the TicTacToe board
     */
    public TicTacToe ()
    {
        JFrame frame = new JFrame();
        frame.setTitle("TicTacToe Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);
        frame.setContentPane(makeContents());
        frame.setVisible(true);
    }

    /**
     * Creates and returns a collection of components to display in the GUI
     */
    public JPanel makeContents ()
    {
        // Create a 3x3 grid of buttons
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++)
        {
            grid.add(new JButton(i + ""));
        }

        // Lay out two buttons left to right
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());
        controls.add(new JButton("New Game"));
        controls.add(new JButton("Quit"));

        // Lay out two labels top to bottom
        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));
        labels.add(new JLabel("Wins by X: 7"));
        labels.add(new JLabel("Wins by O: 3"));

        // Compose everything into the root panel
        JPanel root = new JPanel();
        
        BorderLayout bdl = new BorderLayout();
        root.setLayout(bdl);
        root.add("North", controls);
        root.add("East", labels);
        root.add("Center", grid);

        // Return the root panel
        return root;
    }
}
