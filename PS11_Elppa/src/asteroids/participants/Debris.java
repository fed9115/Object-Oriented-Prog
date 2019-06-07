package asteroids.participants;

import static asteroids.game.Constants.RANDOM;
import java.awt.Shape;
import java.awt.geom.Path2D;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Debris extends Participant
{
    /** The outline of the dust */
    private Shape outline;

    /** The game controller */
    private Controller controller;

    // Constructor of Debris. There are two kinds of debris, smaller ones are denoted as size is 0, bigger ones are
    // denoted as size is 1
    public Debris (double x, double y, int size, Controller controller)
    {
        // Create the dust
        this.controller = controller;
        setPosition(x, y);
        setVelocity(3, RANDOM.nextDouble() * 2 * Math.PI);
        rotate(RANDOM.nextDouble() * Math.PI);
        createDebrisOutline(x, y, size);
    }

    public void createDebrisOutline (double x, double y, int size)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        // Check what size of outline is needed to be drawn

        // draw small debris when size is 0
        if (size == 0)
        {
            poly.moveTo(0, 0);
            poly.lineTo(0, 5);
            poly.closePath();
        }
        // draw big debris when size is 1
        if (size == 1)
        {
            poly.moveTo(0, 0);
            poly.lineTo(0, 15);
            poly.closePath();
        }
        outline = poly;
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
    }

    @Override
    public void countdownComplete (Object payload)
    {
        if (payload.equals("expire"))
        {
            Participant.expire(this);
        }
    }
}
