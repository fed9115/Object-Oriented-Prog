package cs1410;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Demonstrates layout in a Swing application
 * 
 * @author Joe Zachary
 */
public class GUI
{
    /**
     * Launches the GUI
     */
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new GUI());
    }

    /**
     * Lays out the GUI
     */
    public GUI ()
    {
        JFrame frame = new JFrame();
        frame.setTitle("GUI Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setContentPane(makeContents());
        frame.setVisible(true);
    }

    /**
     * Creates and returns a collection of components to display in the GUI
     */
    public JPanel makeContents ()
    {
        // Create five components
        JButton button = new JButton("This is a button");
        JLabel label = new JLabel("Label");
        JTextField field = new JTextField(20);
        field.setText("This is a text field");
        JTextArea area = new JTextArea(10, 10);
        area.setText("This is a text area");
        JRadioButton radio = new JRadioButton();

        // Create a panel to hold the five components
        JPanel root = new JPanel();

        /**
         * FlowLayout is a default layout which arranges the components it is managing into one or more rows. By
         * default, each row is centered. (Change it by passing parameter FlowLayout.CENTER/LEFT/RIGHT)
         */
        // FlowLayout fl = new FlowLayout(FlowLayout.CENTER);

        /**
         * Try arranging the components from top to bottom. This is a job for a BoxLayout.
         */
        // BoxLayout bl = new BoxLayout(root, BoxLayout.PAGE_AXIS);

        /**
         * A GridLayout will arrange components into a regular grid.
         */
        // GridLayout gl = new GridLayout(2, 3);

        /**
         * A BorderLayout allows you to place components at the North (top), East (right), South (bottom), and West
         * (left) of a panel. Each of those components gets as much room as it needs. You can then place a component in
         * the Center; it gets all of the remaining space.
         */
        BorderLayout bdl = new BorderLayout();

        // Add the components to the panel
        root.setLayout(bdl);
        root.add("North", button);
        root.add("West", label);
        root.add("South", field);
        root.add("Center", area);
        root.add("East", radio);

        // Return the panel
        return root;
    }
}
