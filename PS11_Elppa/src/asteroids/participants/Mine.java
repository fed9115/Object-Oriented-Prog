package asteroids.participants;

import java.awt.Shape;
import java.awt.geom.Path2D;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.game.Participant;

public class Mine extends Participant implements AsteroidDestroyer
{

    /** The outline of the ship */
    private Shape outline;

    public Mine ()
    {
        super();
        setPosition(this.getX(), this.getY());
        setRotation(this.getDirection());
        setVelocity(this.getSpeed(), this.getDirection());

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(25, 3);
        poly.lineTo(-21, 11);
        poly.lineTo(-14, 12);
        poly.lineTo(-13, -20);
        poly.lineTo(-31, -22);
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
        // TODO Auto-generated method stub

    }

}
