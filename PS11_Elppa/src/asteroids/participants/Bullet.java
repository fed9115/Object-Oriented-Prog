package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Path2D;
import asteroids.destroyers.AlienShipDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.game.Constants;
import asteroids.game.Controller;
import asteroids.game.Participant;

public class Bullet extends Participant implements AlienShipDestroyer, AsteroidDestroyer
{
    /** The outline of the bullet */
    private Shape outline;

    /** The game controller */
    private Controller controller;

    public Bullet (double x, double y, double direction, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setVelocity(Constants.BULLET_SPEED, direction);

        // Draw the bullet
        if (Controller.isEnhanced())
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(1, 1);
            poly.lineTo(1, -1);
            poly.lineTo(-1, -1);
            poly.lineTo(-1, 1);
            poly.lineTo(1, 1);
            poly.closePath();
            outline = poly;
        }
        if (!Controller.isEnhanced())
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(0, 0);
            poly.lineTo(1, -1);
            poly.lineTo(2, 0);
            poly.lineTo(1, 2);
            poly.closePath();
            outline = poly;
        }
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof BulletDestroyer)
        {
            // Expire the bullet
            Participant.expire(this);
        }
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
