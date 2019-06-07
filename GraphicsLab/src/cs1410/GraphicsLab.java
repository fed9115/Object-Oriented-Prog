package cs1410;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import cs1410lib.GraphicsWindow;

/**
 * Code in support of a CS 1410 lab.
 * 
 * @author Erdi Fan for CS 1410
 */
public class GraphicsLab
{
    /**
     * Starts an application containing two GraphicsWindows.
     */
    public static void main (String[] args)
    {
        new GraphicsWindow("Window 1", 400, 400, 0, 0, Color.WHITE, g -> draw1(g));
        new GraphicsWindow("Window 2", 200, 500, 500, 0, Color.WHITE, g -> draw2(g));
    }

    /**
     * Draws a picture on g.
     */
    public static void draw1 (Graphics g)
    {
       drawHouse(g, 0, 150);
       drawHouse(g, 50, 140);
       drawHouse(g, 100, 130);
    }
    
    /**
     * Draws a picture on g.
     */
    public static void drawHouse ( Graphics g, int x, int y)
    {

        // Draw using red
        g.setColor(Color.RED);

        // Draw a rectangle
        g.drawRect(x, y, 300, 120);

        // Draw first line of a triangle
        g.drawLine(x, y, x + 150, y - 90);

        // Draw second line of a triangle
        g.drawLine(x + 150, y - 90, x + 300, y);
        
        // Draw a window (a rectangle)
        g.drawRect((x+200)/2, (y+160)/2, 150, 60);
    }
    
   
    /**
     * Draws a picture on g.
     */
    public static void draw2 (Graphics g)
    {
        Font font = new Font ("ROMAN", Font.BOLD,16);
        int x = 65;
        int y = 50;
        g.setColor(Color.BLUE);
        g.fillOval(x, y-50, 50, 150);
        g.setColor(Color.magenta);
        g.fillRect(x, y+50, 50, 300);
        g.setFont(font);
        g.drawString("Happy Birthday", 50, 450);
    }
}
