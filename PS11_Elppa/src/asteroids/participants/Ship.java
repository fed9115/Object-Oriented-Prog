package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer, AlienShipDestroyer, AlienBulletDestroyer
{
    /** The outline of the ship without flame */
    private Shape outlineWithoutFlame;

    /** The outline of the ship with flame */
    private Shape outlineWithFlame;

    /** The "switch" of the flame */
    private final int ON = 0;
    private final int OFF = 1;

    /** The condition of the flame */
    private int flame;

    /** Game controller */
    private Controller controller;

    private boolean collided;

    private boolean isAccelerated;

    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public Ship (int x, int y, double direction, Controller controller)
    {
        collided = false;
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);

        // Ship without flame
        if (!Controller.isEnhanced())
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(21, 0);
            poly.lineTo(-21, 12);
            poly.lineTo(-14, 10);
            poly.lineTo(-14, -10);
            poly.lineTo(-21, -12);
            poly.closePath();
            outlineWithoutFlame = poly;
        }
        if (Controller.isEnhanced())
        {
            Path2D.Double p = new Path2D.Double();
            p.moveTo(0, 0);
            p.lineTo(-20, -20);
            p.lineTo(-30, -40);
            p.lineTo(-30, -10);
            p.lineTo(-40, 0);
            p.lineTo(-30, 10);
            p.lineTo(-30, 40);
            p.lineTo(-20, 20);
            p.closePath();
            outlineWithoutFlame = p;
        }

        // Ship with flame
        if (!Controller.isEnhanced())
        {
            Path2D.Double poly2 = new Path2D.Double();
            poly2.moveTo(21, 0);
            poly2.lineTo(-21, 12);
            poly2.lineTo(-14, 10);
            poly2.lineTo(-14, -10);
            poly2.lineTo(-21, -12);
            poly2.closePath();
            poly2.moveTo(-25, 0);
            poly2.lineTo(-15, -5);
            poly2.lineTo(-15, 5);
            poly2.closePath();
            outlineWithFlame = poly2;
        }
        if (Controller.isEnhanced())
        {
            Path2D.Double p2 = new Path2D.Double();
            p2.moveTo(0, 0);
            p2.lineTo(-20, -20);
            p2.lineTo(-30, -40);
            p2.lineTo(-30, -10);
            p2.lineTo(-40, 0);
            p2.lineTo(-30, 10);
            p2.lineTo(-30, 40);
            p2.lineTo(-20, 20);
            p2.closePath();

            p2.moveTo(-45, 0);
            p2.lineTo(-55, -10);
            p2.lineTo(-55, 10);
            p2.closePath();
            outlineWithFlame = p2;
        }

        // Schedule an acceleration in two seconds
        new ParticipantCountdownTimer(this, "move", 2000);
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }

    @Override
    protected Shape getOutline ()
    {
        if (isAccelerated)
        {
            // Turn on the flame
            this.flame = ON;
            if (System.currentTimeMillis() % 2 == 0)
            {
                return outlineWithFlame;
            }
            else
            {
                return outlineWithoutFlame;
            }
        }
        
        else
        {
            return outlineWithoutFlame;
        }
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        applyFriction(SHIP_FRICTION);
        super.move();
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        rotate(Math.PI / 16);
    }

    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }

    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);
    }

    /**
     * fire bullets
     */
    public void shot ()
    {
        Bullet b = new Bullet(getXNose(), getYNose(), getRotation(), controller);
        controller.addParticipant(b);
        new ParticipantCountdownTimer(b, "expire", BULLET_DURATION);

    }

    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {
            collided = true;
            // Turn the ship into debris
            Debris d1 = new Debris(getX(), getY(), 1, controller);
            Debris d2 = new Debris(getX(), getY(), 2, controller);
            Debris d3 = new Debris(getX(), getY(), 2, controller);
            Debris d4 = new Debris(getX(), getY(), 1, controller);
            Debris d5 = new Debris(getX(), getY(), 2, controller);
            Debris d6 = new Debris(getX(), getY(), 2, controller);
            controller.addParticipant(d1);
            new ParticipantCountdownTimer(d1, "expire", 1500);
            controller.addParticipant(d2);
            new ParticipantCountdownTimer(d2, "expire", 1500);
            controller.addParticipant(d3);
            new ParticipantCountdownTimer(d3, "expire", 1500);
            controller.addParticipant(d4);
            new ParticipantCountdownTimer(d4, "expire", 1500);
            controller.addParticipant(d5);
            new ParticipantCountdownTimer(d5, "expire", 1500);
            controller.addParticipant(d6);
            new ParticipantCountdownTimer(d6, "expire", 1500);

            // Tell the controller the ship was destroyed
            controller.shipDestroyed();

            // Expire the ship from the game
            Participant.expire(this);

        }
    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        // For ships
        // Give a burst of acceleration, then schedule another
        // burst for 200 msecs from now.
        if (payload.equals("move"))
        {
            new ParticipantCountdownTimer(this, "move", 200);
        }

    }

    public boolean isCollided ()
    {
        return this.collided;
    }
    
    public void setAccelerate(boolean b)
    {
        isAccelerated = b;
    }
}
