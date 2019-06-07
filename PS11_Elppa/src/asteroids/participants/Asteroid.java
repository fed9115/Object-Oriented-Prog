package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AlienBulletDestroyer;
import asteroids.destroyers.AlienShipDestroyer;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.BulletDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

/**
 * Represents asteroids
 */
public class Asteroid extends Participant
        implements ShipDestroyer, BulletDestroyer, AlienShipDestroyer, AlienBulletDestroyer
{
    /** The size of the asteroid (0 = small, 1 = medium, 2 = large) */
    private int size;

    /** The variety property */
    private int variety;

    /** The outline of the asteroid */
    private Shape outline;

    /** The speed of the asteroid */
    private int speed;

    /** The game controller */
    private Controller controller;

    /**
     * Throws an IllegalArgumentException if size or variety is out of range.
     * 
     * Creates an asteroid of the specified variety (0 through 3) and size (0 = small, 1 = medium, 2 = large) and
     * positions it at the provided coordinates with a random rotation. Its velocity has the given speed but is in a
     * random direction.
     */
    public Asteroid (int variety, int size, double x, double y, Controller controller)
    {
        this.controller = controller;
        this.size = size;
        this.variety = variety;

        // Make sure size and variety are valid
        if (size < 0 || size > 2)
        {
            throw new IllegalArgumentException("Invalid asteroid size: " + size);
        }
        else if (variety < 0 || variety > 3)
        {
            throw new IllegalArgumentException();
        }

        // If the asteroid is large size, then its speed is between slow and 0 speed
        if (size == 2)
        {
            this.speed = RANDOM.nextInt(MAXIMUM_LARGE_ASTEROID_SPEED - 1) + 1;
        }
        // If the asteroid is medium size, then its speed is between slow and medium speed
        if (size == 1)
        {
            this.speed = RANDOM.nextInt(MAXIMUM_MEDIUM_ASTEROID_SPEED - MAXIMUM_LARGE_ASTEROID_SPEED)
                    + MAXIMUM_LARGE_ASTEROID_SPEED;
        }
        // If the asteroid is small size, then its speed is between slow and fast speed
        if (size == 0)
        {
            this.speed = RANDOM.nextInt(MAXIMUM_SMALL_ASTEROID_SPEED - MAXIMUM_LARGE_ASTEROID_SPEED)
                    + MAXIMUM_LARGE_ASTEROID_SPEED;
        }

        // Create the asteroid
        setPosition(x, y);
        setVelocity(speed, RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI * RANDOM.nextDouble());
        createAsteroidOutline(variety, size);
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Creates the outline of the asteroid based on its variety and size.
     */
    private void createAsteroidOutline (int variety, int size)
    {
        // This will contain the outline
        Path2D.Double poly = new Path2D.Double();

        // Fill out according to variety
        if (variety == 0)
        {
            poly.moveTo(0, -30);
            poly.lineTo(28, -15);
            poly.lineTo(20, 20);
            poly.lineTo(4, 8);
            poly.lineTo(-1, 30);
            poly.lineTo(-12, 15);
            poly.lineTo(-5, 2);
            poly.lineTo(-25, 7);
            poly.lineTo(-10, -25);
            poly.closePath();
        }
        else if (variety == 1)
        {
            poly.moveTo(10, -28);
            poly.lineTo(7, -16);
            poly.lineTo(30, -9);
            poly.lineTo(30, 9);
            poly.lineTo(10, 13);
            poly.lineTo(5, 30);
            poly.lineTo(-8, 28);
            poly.lineTo(-6, 6);
            poly.lineTo(-27, 12);
            poly.lineTo(-30, -11);
            poly.lineTo(-6, -15);
            poly.lineTo(-6, -28);
            poly.closePath();
        }
        else if (variety == 2)
        {
            poly.moveTo(10, -30);
            poly.lineTo(30, 0);
            poly.lineTo(15, 30);
            poly.lineTo(0, 15);
            poly.lineTo(-15, 30);
            poly.lineTo(-30, 0);
            poly.lineTo(-10, -30);
            poly.closePath();
        }
        else
        {
            poly.moveTo(30, -18);
            poly.lineTo(5, 5);
            poly.lineTo(30, 15);
            poly.lineTo(15, 30);
            poly.lineTo(0, 25);
            poly.lineTo(-15, 30);
            poly.lineTo(-25, 8);
            poly.lineTo(-10, -25);
            poly.lineTo(0, -30);
            poly.lineTo(10, -30);
            poly.closePath();
        }

        // Scale to the desired size
        double scale = ASTEROID_SCALE[size];
        poly.transform(AffineTransform.getScaleInstance(scale, scale));

        // Save the outline
        outline = poly;
    }

    /**
     * Returns the size of the asteroid
     */
    public int getSize ()
    {
        return size;
    }

    /**
     * When an Asteroid collides with an AsteroidDestroyer, it expires.
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AsteroidDestroyer)
        {
            // Set four dusts so that when asteroid is destroyed, the dust effect can be seen, and the dusts will last
            // 1500ms
            Dust d1 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d1);
            new ParticipantCountdownTimer(d1, "expire", 1500);
            Dust d2 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d2);
            new ParticipantCountdownTimer(d2, "expire", 1500);
            Dust d3 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d3);
            new ParticipantCountdownTimer(d3, "expire", 1500);
            Dust d4 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d4);
            new ParticipantCountdownTimer(d4, "expire", 1500);
            Dust d5 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d5);
            new ParticipantCountdownTimer(d5, "expire", 1500);
            Dust d6 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d6);
            new ParticipantCountdownTimer(d6, "expire", 1500);
            Dust d7 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d7);
            new ParticipantCountdownTimer(d7, "expire", 1500);
            Dust d8 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d8);
            new ParticipantCountdownTimer(d8, "expire", 1500);
            Dust d9 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d9);
            new ParticipantCountdownTimer(d9, "expire", 1500);
            Dust d10 = new Dust(getX(), getY(), controller);
            controller.addParticipant(d10);
            new ParticipantCountdownTimer(d10, "expire", 1500);

            // Expire the asteroid
            Participant.expire(this);

            // Inform the controller
            controller.asteroidDestroyed(this.getSize());

            // Add scores
            controller.addScore(ASTEROID_SCORE[this.size]);

            // Shrink the asteroid, and change its properties
            size = this.getSize() - 1;
            variety = RANDOM.nextInt(3);

            // try to create some smaller asteroids, if it is already small, "catch the exception", and make it only
            // expire
            try
            {
                controller.addParticipant(new Asteroid(variety, size, this.getX(), this.getY(), controller));
                controller.addParticipant(new Asteroid(variety, size, this.getX(), this.getY(), controller));
            }
            catch (Exception e)
            {
            }

        }
    }
}
