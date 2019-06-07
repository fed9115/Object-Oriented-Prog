package asteroids.participants;

import static asteroids.game.Constants.RANDOM;
import java.awt.Shape;
import java.awt.geom.Path2D;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Dust extends Participant
{
    /** The outline of the dust */
    private Shape outline;

    /** The game controller */
    private Controller controller;

    public Dust (double x, double y, Controller controller)
    {
        // Create the dust
        this.controller = controller;
        setPosition(x, y);
        setVelocity(3, RANDOM.nextDouble() * 2 * Math.PI);
        createDustOutline(x, y);
    }

    public void createDustOutline (double x, double y)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        poly.moveTo(0, -1);
        poly.lineTo(0.4, 0);
        poly.lineTo(-0.4, 0);
        poly.closePath();
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
