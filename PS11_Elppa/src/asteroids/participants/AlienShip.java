package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import asteroids.game.ParticipantState;
import sounds.SoundDemo;

/**
 * Represents ships
 */
public class AlienShip extends Participant implements AsteroidDestroyer, ShipDestroyer, BulletDestroyer
{
    private static final double[] ALIENSHIP_SPEED = { 10, 5 };

    /** The outline of the alien ship */
    private Shape outline;

    /** Game controller */
    private Controller controller;

    /** Alien ship's direction */
    private double direction;

    /** Alien ship's size */
    private int size;

    /** Alien sounds */
    private SoundDemo alienSound;

    private long current;

    private int random = RANDOM.nextInt(5000) + 5000;

    private Path2D poly;

    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public AlienShip (Controller controller)
    {
        alienSound = new SoundDemo();
        this.controller = controller;

        Path2D.Double poly = new Path2D.Double();
        outline = poly;

        poly.transform(AffineTransform.getScaleInstance(size, size));

        new ParticipantCountdownTimer(this, "spawn", random);
        new ParticipantCountdownTimer(this, "turn", RANDOM.nextInt(3500));
        new ParticipantCountdownTimer(this, "shoot", 1800);

    }

    public void draw ()
    {

        // At level 2, a medium size alien ship appears 5-10 seconds after the level begins
        if (controller.getLevel() == 2)
        {
            alienSound.play("big saucer");
            size = 1;
        }
        // At level 3 and above, the medium alien ship stops appearing and the small alien ship appears instead.
        if (controller.getLevel() > 2)
        {
            alienSound.play("small saucer");
            size = 0;
        }

        // Set velocity of the alien ship so that it can zig-zag
        this.setVelocity(ALIENSHIP_SPEED[size], 0);
        setPosition(0, RANDOM.nextDouble() * SIZE);

        // Draw alien ship
        if (!Controller.isEnhanced())
        {
            poly = new Path2D.Double();
            poly.moveTo(20, 0);
            poly.lineTo(10, 8);
            poly.lineTo(-10, 8);
            poly.lineTo(-20, 0);
            poly.lineTo(20, 0);
            poly.lineTo(-20, 0);
            poly.lineTo(-10, -8);
            poly.lineTo(10, -8);
            poly.lineTo(-8, -8);
            poly.lineTo(-6, -15);
            poly.lineTo(6, -15);
            poly.lineTo(8, -8);
            poly.lineTo(10, -8);
            poly.closePath();
        }
        if (Controller.isEnhanced())
        {
            poly = new Path2D.Double();
            poly.moveTo(0, 20);
            poly.lineTo(5, 25);
            poly.lineTo(10, 20);
            poly.lineTo(10, 15);
            poly.lineTo(12.5, 17.5);
            poly.lineTo(15, 15);
            poly.lineTo(15, -10);
            poly.lineTo(10, 0);
            poly.lineTo(0, 0);
            poly.lineTo(-5, -10);
            poly.lineTo(-5, 15);
            poly.lineTo(-2.5, 17.5);
            poly.lineTo(0, 15);
            poly.closePath();
        }

        // Scale to the desired size, two different scales
        double scale = ALIENSHIP_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        outline = poly;
    }

    // Change the direction of the alien ship, it randomly zig-zags in three directions: either perfectly horizontal, or
    // at a slight angle one radian upward or downward from horizontal.
    public void turn ()
    {
        int randomChoice = RANDOM.nextInt(6);
        if (randomChoice == 0)
        {
            setDirection(1.0);
        }
        else if (randomChoice == 1)
        {
            setDirection(-1.0);
        }
        else if (randomChoice == 2)
        {
            setDirection(0);
        }
        else if (randomChoice == 3)
        {
            setDirection(Math.PI);
        }
        else if (randomChoice == 4)
        {
            setDirection(Math.PI - 1);
        }
        else
        {
            setDirection(Math.PI + 1);
        }
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Customizes the base move method
     */
    @Override
    public void move ()
    {
        super.move();
    }

    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AlienShipDestroyer)
        {
            if (size == 1)
            {
                alienSound.getbigAlien().stop();
            }
            if (size == 0)
            {
                alienSound.getsmallAlien().stop();
            }
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

            // Expire the ship from the game
            Participant.expire(this);

            current = System.currentTimeMillis();

            // Tell the controller the ship was destroyed
            controller.alienShipDestroyed(this.size);

            new ParticipantCountdownTimer(this, "spawn", random);

            if (p instanceof Bullet)
            {
                controller.addScore(ALIENSHIP_SCORE[size]);
            }
        }
    }

    /**
     * This method is invoked when a ParticipantCountdownTimer completes its countdown.
     */
    @Override
    public void countdownComplete (Object payload)
    {
        if (payload.equals("turn"))
        {
            turn();
            new ParticipantCountdownTimer(this, "turn", RANDOM.nextInt(3500));
        }

        if (payload.equals("shoot"))
        {
            fireAlienBullets();
            new ParticipantCountdownTimer(this, "shoot", 1800);
        }

        if (payload.equals("spawn"))
        {
            controller.placeAlienShip();
            draw();
        }
    }

    /**
     * I use this method to fire bullets from alien ship towards my ships when level is higher than 2
     */
    private void fireAlienBullets ()
    {
        double radian;
        double randomRadian = 0;
        double range = 10;

        if (size == 0 && !this.isExpired())
        {
            if (controller.getShip() != null)
            {
                // it fires in a randomly chosen direction that is within five degrees of the direction of the center of
                // the ship
                radian = getAngle(this.getX(), this.getY(), controller.getShip().getX(), controller.getShip().getY());
                double degree = Math.toDegrees(radian);
                double minRangeDegree = degree - (range / 2);
                double randomDegree = minRangeDegree + RANDOM.nextDouble() * range;
                randomRadian = Math.toRadians(randomDegree);
            }

            // Create alien bullet
            AlienBullet b = new AlienBullet(this.getX(), this.getY(), randomRadian, controller);
            controller.addParticipant(b);
            new ParticipantCountdownTimer(b, "expire", BULLET_DURATION);
        }
    }

    // Helper method for me to get the angle the alien ship's bullets shot to
    public double getAngle (double fromX, double fromY, double toX, double toY)
    {
        double angle;
        double normalizedAngle;
        double temp;
        double x = toX - fromX;
        double y = toY - fromY;
        // Using the Pythagorean theorem
        double hypo = Math.sqrt(x * x + y * y);

        temp = x / hypo;
        angle = Math.acos(temp);
        angle = y > 0 ? angle : -angle;
        normalizedAngle = normalize(angle);

        return normalizedAngle;
    }

    public void setSize (int i)
    {
        this.size = i;
    }

    public long getCurrent ()
    {
        return current;
    }
}
