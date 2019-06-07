package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.sound.sampled.Clip;
import javax.swing.*;
import asteroids.participants.AlienShip;
import asteroids.participants.Asteroid;
import asteroids.participants.Bullet;
import asteroids.participants.Ship;
import sounds.SoundDemo;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener
{
    /** The state of all the Participants */
    private ParticipantState pstate;

    /** The ship (if one is active) or null (otherwise) */
    private Ship ship;
    // another ship for two players
    private Ship ship2;

    /** The alien ship */
    private AlienShip alienShip;

    /** The bullets */
    private Bullet bullet;

    /** Two clips of sound effects */
    private Clip clip1;
    private Clip clip2;

    /** Spawn alien ship */
    private double spawnAS;

    /** When this timer goes off, it is time to refresh the animation */
    private Timer refreshTimer;

    /** Timer for two clips(beats) */
    private Timer beat1Timer;
    private Timer beat2Timer;

    /** Annotate the start of two beatsTimer */
    private int beat1Start;
    private int beat2Start;

    /**
     * The time at which a transition to a new stage of the game should be made. A transition is scheduled a few seconds
     * in the future to give the user time to see what has happened before doing something like going to a new level or
     * resetting the current level.
     */
    private long transitionTime;

    /** Note the sound, I rewrite the class SoundDemo to make sure that all the sounds can function well */
    private SoundDemo sound;

    /** Check if the game is in enhanced mode or not */
    private static boolean enhanced;

    /** Number of lives left */
    private int lives;

    /** The levels of the game */
    private int level;

    /** The score of the game */
    private int score;

    /** Turn the ship left or right */
    private boolean turnLeft;
    private boolean turnRight;

    private boolean turnLeft2;
    private boolean turnRight2;

    /** Drive the ship when flame occurs */
    private boolean accelerate;

    private boolean accelerate2;

    /** Fire bullets to shot */
    private boolean fireBullet;

    private boolean fireBullet2;

    /** The game display */
    private Display display;

    /** The time delay for alien ship to be created */
    private double alienSpawn;

    /** Begin the game */
    private boolean start;

    private boolean clear;

    private HashMap<Integer,Integer> levelHighestScore;
    private int levelScore;
    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller ()
    {
        // Initialize the ParticipantState
        pstate = new ParticipantState();

        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);

        // The number of milliseconds between beats, initially.
        beat1Start = INITIAL_BEAT;
        beat2Start = INITIAL_BEAT;

        beat1Timer = new Timer(beat1Start, this);
        beat2Timer = new Timer(beat2Start, this);

        // Clear the transitionTime
        transitionTime = Long.MAX_VALUE;

        // Record the display object
        display = new Display(this);

        // Record the highest score in each section
        levelHighestScore = new HashMap<Integer, Integer>();
        
        // Create default info
        score = 0;
        lives = 3;
        level = 1;
        levelScore = 0;
        levelHighestScore.put(level, 0);
        turnLeft = false;
        turnRight = false;
        accelerate = false;
        fireBullet = false;

        turnLeft2 = false;
        turnRight2 = false;
        accelerate2 = false;
        fireBullet2 = false;

        // Bring up the splash screen and start the refresh timer
        splashScreen();
        display.setVisible(true);
        refreshTimer.start();

        // Initialize sounds
        sound = new SoundDemo();

        clip1 = sound.getBeat1();
        clip2 = sound.getBeat2();

        // initialize the spawn of alien ships, emerge after 5 - 10s
        spawnAS = System.currentTimeMillis() + ALIEN_DELAY + RANDOM.nextInt(5000);
    }

    /**
     * Returns the ship, or null if there isn't one
     */
    public Ship getShip ()
    {
        return ship;
    }

    public Ship getShip2 ()
    {
        return ship2;
    }

    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        display.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();
    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
        display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip ()
    {
        // Place a new ship
        Participant.expire(ship);
        ship = new Ship(SIZE / 2, SIZE / 2, -Math.PI / 2, this);
        addParticipant(ship);
        display.setLegend("");
    }

    /**
     * Place another ship for enhanced mode
     */
    private void placeShip2 ()
    {
        // Place a new ship
        Participant.expire(ship2);
        ship2 = new Ship(SIZE / 3, SIZE / 3, -Math.PI / 2, this);
        addParticipant(ship2);
        display.setLegend("");
    }

    /**
     * Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeAsteroids ()
    {
        // Place four asteroids on each corner of the screen
        Asteroid[] asteroids = new Asteroid[4];
        asteroids[0] = new Asteroid(0, 2, EDGE_OFFSET, EDGE_OFFSET, this);
        asteroids[1] = new Asteroid(1, 2, -EDGE_OFFSET, EDGE_OFFSET, this);
        asteroids[2] = new Asteroid(2, 2, EDGE_OFFSET, -EDGE_OFFSET, this);
        asteroids[3] = new Asteroid(3, 2, -EDGE_OFFSET, -EDGE_OFFSET, this);

        // For level 1, there are only four asteroids
        addParticipant(asteroids[0]);
        addParticipant(asteroids[1]);
        addParticipant(asteroids[2]);
        addParticipant(asteroids[3]);

        // For level higher than 2, generate one more asteroid for this level
        for (int i = 2; i <= level; i++)
        {
            addParticipant(asteroids[RANDOM.nextInt(4)]);
        }
    }

    /**
     * Places an alien ship on the screen.
     */
    public void placeAlienShip ()
    {
        if (pstate.countAlienShips() == 0)
        {
            alienShip = new AlienShip(this);
            if (level >= 3)
            {
                alienShip.setSize(0);
            }
            if (level == 2)
            {
                alienShip.setSize(1);
            }

            if (!(pstate.countAsteroids() == 0 && (pstate.countAlienShips() != 0
                    && (alienShip.getCurrent() > pstate.currentTime() - alienShip.getCurrent()))))
            {
                addParticipant(alienShip);

            }
        }
    }

    /**
     * Clears the screen so that nothing is displayed
     */
    private void clear ()
    {
        pstate.clear();
        display.setLegend("");
        ship = null;
        ship2 = null;
    }

    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        clear();

        // Begin the game
        start = true;

        // Place asteroids
        placeAsteroids();

        // Place the ship
        placeShip();

        if (enhanced)
        {
            placeShip2();
        }

        // Reset statistics
        lives = 3;
        level = 1;
        score = 0;

        if (enhanced)
        {
            lives = 6;
        }

        // Start listening to events (but don't listen twice)
        display.removeKeyListener(this);
        display.addKeyListener(this);

        // Give focus to the game screen
        display.requestFocusInWindow();
    }

    /**
     * Adds a new Participant
     */
    public void addParticipant (Participant p)
    {
        pstate.addParticipant(p);
    }

    /**
     * The ship has been destroyed
     */
    public void shipDestroyed ()
    {
        // Play sound
        sound.play("bangShip");
        // Null out the ship
        if (ship != null && ship.isCollided())
        {
            ship = null;
            // "Lock" the controller of the ship
            turnRight = false;
            turnLeft = false;
            accelerate = false;
            fireBullet = false;
        }

        if (enhanced && ship2 != null && ship2.isCollided())
        {
            ship2 = null;
            // "Lock" the controller of the ship
            turnRight2 = false;
            turnLeft2 = false;
            accelerate2 = false;
            fireBullet2 = false;
        }

        // Display a legend
        display.setLegend("Ouch!");

        // Decrement lives
        lives--;

        // Since the ship was destroyed, schedule a transition
        scheduleTransition(END_DELAY);
    }

    /**
     * An asteroid has been destroyed, and play particular sound effect
     */
    public void asteroidDestroyed (int size)
    {
        // Play sound
        if (size == 0)
        {
            sound.play("bang small");
        }
        if (size == 1)
        {
            sound.play("bang medium");
        }
        if (size == 2)
        {
            sound.play("bang large");
        }

        // If all the asteroids are gone, schedule a transition
        if (pstate.countAsteroids() == 0)
        {
            scheduleTransition(END_DELAY);
        }
    }

    /**
     * An alien ship has been destroyed, and play particular sound effect
     */
    public void alienShipDestroyed (int size)
    {
        alienShip = null;
        sound.play("bang alien ship");
        alienSpawn = System.currentTimeMillis() + ALIEN_DELAY + RANDOM.nextInt(5000);
    }

    /**
     * Schedules a transition m msecs in the future
     */
    private void scheduleTransition (int m)
    {
        transitionTime = System.currentTimeMillis() + m;
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == beat1Timer)
        {
            beat1Timer.stop();
            if (beat1Start > FASTEST_BEAT)
            {
                beat1Start -= BEAT_DELTA;
            }
            clip1.setFramePosition(0);
            clip1.start();
            beat2Timer = new Timer(beat2Start, this);
            beat2Timer.start();
        }

        if (e.getSource() == beat2Timer)
        {
            beat2Timer.stop();
            if (beat2Start > FASTEST_BEAT)
            {
                beat2Start -= BEAT_DELTA;
            }
            clip2.setFramePosition(0);
            clip2.start();
            beat1Timer = new Timer(beat1Start, this);
            beat1Timer.start();
        }
        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial screen
        if (e.getSource() instanceof JButton)
        {
            initialScreen();
        }

        // Time to refresh the screen and deal with keyboard input
        else if (e.getSource() == refreshTimer)
        {
            if (alienSpawn <= System.currentTimeMillis())
            {
                alienSpawn = Long.MAX_VALUE;
                if (level > 1)
                {
                    placeAlienShip();
                }
            }
            if (turnLeft && ship != null)
            {
                ship.turnLeft();
            }
            if (turnRight && ship != null)
            {
                ship.turnRight();
            }
            if (accelerate && ship != null)
            {
                ship.accelerate();
                sound.play("thrust");
            }
            if (fireBullet && ship != null && pstate.countBullets() <= BULLET_LIMIT)
            {
                ship.shot();
                sound.play("fire");
            }

            if (enhanced)
            {
                if (turnLeft2 && ship2 != null)
                {
                    ship2.turnLeft();
                }
                if (turnRight2 && ship2 != null)
                {
                    ship2.turnRight();
                }
                if (accelerate2 && ship2 != null)
                {
                    ship2.accelerate();
                    sound.play("thrust");
                }
                if (fireBullet2 && ship2 != null && pstate.countBullets() <= BULLET_LIMIT)
                {
                    ship2.shot();
                    sound.play("fire");
                }
            }

            if (enhanced && clear)
            {
                int currentLives = getLives();
                int currentScore = getScores();
              
                initialScreen();
                lives = currentLives;
                score = currentScore;
               
            }

            // It may be time to make a game transition
            performTransition();

            // Move the participants to their new locations
            pstate.moveParticipants();

            // Refresh screen
            display.refresh();
        }
    }

    /**
     * Returns an iterator over the active participants
     */
    public Iterator<Participant> getParticipants ()
    {
        return pstate.getParticipants();
    }

    /**
     * If the transition time has been reached, transition to a new state
     */
    private void performTransition ()
    {
        // Do something only if the time has been reached
        if (transitionTime <= System.currentTimeMillis())
        {
            // Clear the transition time
            transitionTime = Long.MAX_VALUE;

            // If there are no lives left, the game is over. Show the final
            // screen.
            System.out.println(levelScore);
            
            if (lives <= 0)
            {
                finalScreen();
            }
            if(enhanced && this.lives <= 0) {
                if (this.levelHighestScore.containsKey(this.level)&&this.levelHighestScore.get(this.level)<this.levelScore)
                {
                    this.levelHighestScore.put(this.level, this.levelScore);
                }
                levelScore = 0;
                this.level=1;
            }
            else if (pstate.countAsteroids() == 0)
            {
               
                level++;
                
                beat1Timer.stop();
                beat1Timer.restart();
                beat2Timer.stop();
                beat2Timer.restart();

                placeAsteroids();
                
                if(enhanced) {
                    
                    
                    lives++;
                    if (this.levelHighestScore.containsKey(this.level-1)&&this.levelHighestScore.get(this.level-1)<this.levelScore)
                    {
                        this.levelHighestScore.put(this.level-1, this.levelScore);
                    }
                 
                    if (!this.levelHighestScore.containsKey(this.level))
                    {
                        this.levelHighestScore.put(level, 0);
                        
                    }
                  
                    levelScore=0;
                }
                if (this.getShip() == null)
                {
                    placeShip();
                }
                if (enhanced && this.getShip2() == null)
                {
                    placeShip2();
                }

                if (level > 1 && pstate.countAlienShips() == 0)
                {
                    placeAlienShip();
                }

                beat1Timer.stop();
                beat2Timer.stop();

                beat1Timer.start();
                beat2Timer.start();
                
             //   
            }
            else
            {
                if (this.getShip() == null)
                {
                    placeShip();
                }
                if (enhanced && this.getShip2() == null)
                {
                    placeShip2();
                }
            }

        }
    }

    /**
     * If a key of interest is pressed, record that it is down.
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null)
        {
            turnRight = true;
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null)
        {
            turnLeft = true;
        }
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null)
        {
            accelerate = true;
            ship.setAccelerate(true);
        }
        if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_SPACE) && ship != null && pstate.countBullets() <= BULLET_LIMIT)
        {
            fireBullet = true;
        }

        if (enhanced)
        {
            if ((e.getKeyCode() == KeyEvent.VK_L) && ship2 != null)
            {
                turnRight2 = true;
            }
            if ((e.getKeyCode() == KeyEvent.VK_J) && ship2 != null)
            {
                turnLeft2 = true;
            }
            if ((e.getKeyCode() == KeyEvent.VK_I) && ship2 != null)
            {
                accelerate2 = true;
                ship2.setAccelerate(true);
            }
            if ((e.getKeyCode() == KeyEvent.VK_K) && ship2 != null && pstate.countBullets() <= BULLET_LIMIT)
            {
                fireBullet2 = true;
            }
        }

        // Keys for additional functions (pause/restart) (These features are for extra credits in enhanced mode)
        if (enhanced)
        {
            if (e.getKeyCode() == KeyEvent.VK_P)
            {
                refreshTimer.stop();
            }
            if (e.getKeyCode() == KeyEvent.VK_O)
            {
                refreshTimer.start();
            }
        }

        if (enhanced && (this.getScores() - 500) > 0)
        {
            if (e.getKeyCode() == KeyEvent.VK_C)
            {
                clear = true;
            }
        }

    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyTyped (KeyEvent e)
    {
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null)
        {
            turnRight = false;
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null)
        {
            turnLeft = false;
        }
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null)
        {
            accelerate = false;
            ship.setAccelerate(false);
        }
        if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_SPACE) && ship != null)
        {
            fireBullet = false;
        }

        if (enhanced)
        {
            if ((e.getKeyCode() == KeyEvent.VK_L) && ship2 != null)
            {
                turnRight2 = false;
            }
            if ((e.getKeyCode() == KeyEvent.VK_J) && ship2 != null)
            {
                turnLeft2 = false;
            }
            if ((e.getKeyCode() == KeyEvent.VK_I) && ship2 != null)
            {
                accelerate2 = false;
                ship2.setAccelerate(false);
            }
            if ((e.getKeyCode() == KeyEvent.VK_K) && ship2 != null)
            {
                fireBullet2 = false;
            }
        }

        if (enhanced)
        {
            if (e.getKeyCode() == KeyEvent.VK_C)
            {
                clear = false;
            }
        }
    }

    public void addScore (int i)
    {
        this.score += i;
        this.levelScore += i;
    }

    public boolean isStarted ()
    {
        return start;
    }

    public boolean isAcclerated ()
    {
        return accelerate;
    }

    public boolean isAcclerated2 ()
    {
        return accelerate2;
    }

    public int getLevel ()
    {
        return level;
    }

    public int getLives ()
    {
        return lives;
    }

    public int getScores ()
    {
        return score;
    }
    
    public int getlevelHighestScore(int level) {
        return this.levelHighestScore.get(level);
    }

    public ParticipantState getPsate ()
    {
        return this.pstate;
    }

    public static void setEnhanced (boolean b)
    {
        enhanced = b;
    }

    public static boolean isEnhanced ()
    {
        return enhanced;
    }

}
