package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Animation
{

    /** Interval in msec between cannon fires */
    private final static int INTERVAL = 2500;

    /** Name to display in title bar */
    public final static String STUDENT_NAME = "Erdi Fan";  // PUT YOUR NAME HERE!!!

    /**
     * This is the method that you need to rewrite to create a custom animation. This method is called repeatedly as the
     * animation proceeds. It needs to draw on g how the animation should look after time milliseconds have passed.
     * 
     * The method returns true if the end of the animation has been reached or false if the animation should continue.
     * 
     * @param g      Graphics object on which to draw
     * @param time   Number of milliseconds that have passed since animation started
     * @param height Current height of the frame
     * @param width  Current width of the frame
     */
    public static boolean paintFrame (Graphics2D g, int time, int height, int width)
    {
        // Change the color of the raindrops
        if ((time / 500) % 2 == 0)
        {
            g.setColor(Color.GREEN);
        }
        else
        {
            g.setColor(Color.YELLOW);
        }

        // Raindrops are falling down.
        for (int i = 0; i < 1000; i = i + 10)
        {
            g.fillOval(i, (int) (Math.random() * -800 + time / 5), 3 + time / 1000, 5 + time / 500);
        }
        // Sun is rising.

        g.setColor(Color.yellow);
        g.fillOval(1000 - 200, 1200 - time / 10, 100, 100);

        // Display different captions

        Font font = new Font("ROMAN", Font.BOLD, 30);
        g.setColor(Color.black);
        if (time < 7000)
        {
            g.setFont(font);
            g.drawString("It's raining", 50, 50);
        }
        else
        {
            g.setFont(font);
            g.drawString("It's sunny!!!", 50, 50);
        }

        // Stop the animation if it has run long enough
        return time >= 5.5 * INTERVAL;
    }

}