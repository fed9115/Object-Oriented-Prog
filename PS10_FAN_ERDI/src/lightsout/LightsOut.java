package lightsout;

import javax.swing.SwingUtilities;

/**
 * Launches a game of Lights Out.
 * 
 * @author Erdi Fan
 */
public class LightsOut
{
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new LightsOutView());
    }
}
