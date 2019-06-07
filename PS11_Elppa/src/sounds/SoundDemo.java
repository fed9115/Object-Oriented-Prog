package sounds;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Demonstrates how to put sound files into a project so that they will be included when the project is exported, and
 * demonstrates how to play sounds.
 * 
 * I rewrite the class to make sure that all the sounds can function well
 * 
 * @author Joe Zachary and Erdi Fan
 */
@SuppressWarnings("serial")
public class SoundDemo extends JFrame implements ActionListener
{
    /**
     * Launches a simple sound demo application
     */
    public static void main (String[] args)
    {
        SoundDemo demo = new SoundDemo();
        demo.setVisible(true);
    }

    /** A Clip that, when played, sounds like an alien ship being banged */
    private Clip bangAlienShipClip;

    /** A Clip that, when played, sounds like a big asteroid being banged */
    private Clip bangLargeClip;

    /** A Clip that, when played, sounds like a medium asteroid being banged */
    private Clip bangMediumClip;

    /** A Clip that, when played, sounds like a big asteroid being banged */
    private Clip bangSmallClip;

    /** A Clip that, when played, sounds like a ship being destroyed */
    private Clip bangShipClip;

    /** A Clip that, when played, sounds like a small beat */
    private Clip beat1Clip;

    /** A Clip that, when played, sounds like a large beat */
    private Clip beat2Clip;

    /** A Clip that, when played, sounds like a weapon being fired */
    private Clip fireClip;

    /** A Clip that, when played repeatedly, sounds like a big saucer flying */
    private Clip bigSaucerClip;

    /** A Clip that, when played repeatedly, sounds like a small saucer flying */
    private Clip smallSaucerClip;

    /** A Clip that, when played, sounds when a space ship is thrusting in space */
    private Clip thrustClip;

    /**
     * Creates the demo.
     */
    public SoundDemo ()
    {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel buttons = new JPanel();
//        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
//
//        JPanel bangAlienShip = new JPanel();
//        JButton bangAlienShipButton = new JButton("Bang alien ship");
//        bangAlienShipButton.setActionCommand("bang alien ship");
//        bangAlienShipButton.addActionListener(this);
//        bangAlienShip.add(bangAlienShipButton);
//        buttons.add(bangAlienShip);
//
//        JPanel bangLarge = new JPanel();
//        JButton bangLargeButton = new JButton("Bang large");
//        bangLargeButton.setActionCommand("bang large");
//        bangLargeButton.addActionListener(this);
//        bangLarge.add(bangLargeButton);
//        buttons.add(bangLarge);
//
//        JPanel bangMedium = new JPanel();
//        JButton bangMediumButton = new JButton("Bang medium");
//        bangMediumButton.setActionCommand("bang medium");
//        bangMediumButton.addActionListener(this);
//        bangMedium.add(bangMediumButton);
//        buttons.add(bangMedium);
//
//        JPanel bangSmall = new JPanel();
//        JButton bangSmallButton = new JButton("Bang small");
//        bangSmallButton.setActionCommand("bang small");
//        bangSmallButton.addActionListener(this);
//        bangSmall.add(bangSmallButton);
//        buttons.add(bangSmall);
//
//        JPanel bangShip = new JPanel();
//        JButton bangShipButton = new JButton("Bang ship");
//        bangShipButton.setActionCommand("bang ship");
//        bangShipButton.addActionListener(this);
//        bangShip.add(bangShipButton);
//        buttons.add(bangShip);
//
//        JPanel beat1 = new JPanel();
//        JButton beat1Button = new JButton("Beat1");
//        beat1Button.addActionListener(this);
//        beat1Button.setActionCommand("beat1");
//        beat1.add(beat1Button);
//        buttons.add(beat1);
//
//        JPanel beat2 = new JPanel();
//        JButton beat2Button = new JButton("Beat2");
//        beat2Button.addActionListener(this);
//        beat2Button.setActionCommand("beat2");
//        beat2.add(beat2Button);
//        buttons.add(beat2);
//
//        JPanel fire = new JPanel();
//        JButton fireButton = new JButton("Fire");
//        fireButton.addActionListener(this);
//        fireButton.setActionCommand("fire");
//        fire.add(fireButton);
//        buttons.add(fire);
//
//        JPanel saucerBig = new JPanel();
//        JButton saucerBigButton = new JButton("Big Saucer");
//        saucerBigButton.setActionCommand("big saucer");
//        saucerBigButton.addActionListener(this);
//        saucerBig.add(saucerBigButton);
//        buttons.add(saucerBig);
//
//        JPanel saucerSmall = new JPanel();
//        JButton saucerSmallButton = new JButton("Small Saucer");
//        saucerSmallButton.setActionCommand("small saucer");
//        saucerSmallButton.addActionListener(this);
//        saucerSmall.add(saucerSmallButton);
//        buttons.add(saucerSmall);
//
//        setContentPane(buttons);
//        pack();

        // We create the clips in advance so that there will be no delay
        // when we need to play them back. Note that the actual wav
        // files are stored in the "sounds" project.
        bangAlienShipClip = createClip("/sounds/bangAlienShip.wav");
        bangLargeClip = createClip("/sounds/bangLarge.wav");
        bangMediumClip = createClip("/sounds/bangMedium.wav");
        bangShipClip = createClip("/sounds/bangShip.wav");
        bangSmallClip = createClip("/sounds/bangSmall.wav");
        beat1Clip = createClip("/sounds/beat1.wav");
        beat2Clip = createClip("/sounds/beat2.wav");
        fireClip = createClip("/sounds/fire.wav");
        bigSaucerClip = createClip("/sounds/saucerBig.wav");
        smallSaucerClip = createClip("/sounds/saucerSmall.wav");
        thrustClip = createClip("/sounds/thrust.wav");
    }

    /**
     * Creates an audio clip from a sound file.
     */
    public Clip createClip (String soundFile)
    {
        // Opening the sound file this way will work no matter how the
        // project is exported. The only restriction is that the
        // sound files must be stored in a package.
        try (BufferedInputStream sound = new BufferedInputStream(getClass().getResourceAsStream(soundFile)))
        {
            // Create and return a Clip that will play a sound file. There are
            // various reasons that the creation attempt could fail. If it
            // fails, return null.
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            return clip;
        }
        catch (LineUnavailableException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        catch (UnsupportedAudioFileException e)
        {
            return null;
        }
    }

    /**
     * Plays sounds depending on which button was clicked.
     */
    public void play (String s)
    {
        // We "rewind" the bangAlienShip and play it.
        if (s.equals("bang alien ship") && bangAlienShipClip != null)
        {
            if (bangAlienShipClip.isRunning())
            {
                bangAlienShipClip.stop();
            }
            bangAlienShipClip.setFramePosition(0);
            bangAlienShipClip.start();
        }

        // We "rewind" the bangLarge and play it.
        if (s.equals("bang large") && bangLargeClip != null)
        {
            if (bangLargeClip.isRunning())
            {
                bangLargeClip.stop();
            }
            bangLargeClip.setFramePosition(0);
            bangLargeClip.start();
        }

        // We "rewind" the bangMedium and play it.
        if (s.equals("bang medium") && bangMediumClip != null)
        {
            if (bangMediumClip.isRunning())
            {
                bangMediumClip.stop();
            }
            bangMediumClip.setFramePosition(0);
            bangMediumClip.start();
        }

        // We "rewind" the bangShip and play it.
        if (s.equals("bang ship") && bangShipClip != null)
        {
            if (bangShipClip.isRunning())
            {
                bangShipClip.stop();
            }
            bangShipClip.setFramePosition(0);
            bangShipClip.start();
        }

        // We "rewind" the bangSmall and play it.
        if (s.equals("bang small") && bangSmallClip != null)
        {
            if (bangSmallClip.isRunning())
            {
                bangSmallClip.stop();
            }
            bangSmallClip.setFramePosition(0);
            bangSmallClip.start();
        }

        // We "rewind" the beat1 and play it.
        if (s.equals("beat") && beat1Clip != null && beat2Clip != null)
        {
            if (beat1Clip.isRunning())
            {
                beat1Clip.stop();
            }
            beat1Clip.setFramePosition(0);
            beat1Clip.start();

            if (beat2Clip.isRunning())
            {
                beat2Clip.stop();
            }
            beat2Clip.setFramePosition(0);
            beat2Clip.start();
        }

        // We "rewind" the fireClip and play it.
        if (s.equals("fire") && fireClip != null)
        {
            if (fireClip.isRunning())
            {
                fireClip.stop();
            }
            fireClip.setFramePosition(0);
            fireClip.start();
        }

        // We "rewind" the bigSaucerClip and play it ten times in a row. To loop
        // continuously, pass Clip.LOOP_CONTINUOUSLY as the parameter.
        if (s.equals("big saucer") && bigSaucerClip != null)
        {
            if (bigSaucerClip.isRunning())
            {
                bigSaucerClip.stop();
            }
            bigSaucerClip.setFramePosition(0);
            bigSaucerClip.loop(10);
        }

        // We "rewind" the smallSaucerClip and play it ten times in a row. To loop
        // continuously, pass Clip.LOOP_CONTINUOUSLY as the parameter.
        if (s.equals("small saucer") && smallSaucerClip != null)
        {
            if (smallSaucerClip.isRunning())
            {
                smallSaucerClip.stop();
            }
            smallSaucerClip.setFramePosition(0);
            smallSaucerClip.loop(10);
        }

        // We "rewind" the thrust and play it.
        if (s.equals("thrust") && thrustClip != null)
        {
            if (thrustClip.isRunning())
            {
                thrustClip.stop();
            }
            thrustClip.setFramePosition(0);
            thrustClip.start();
        }
    }

    /** Some getters */
    public Clip getbigAlien ()
    {
        return bigSaucerClip;
    }

    public Clip getsmallAlien ()
    {
        return smallSaucerClip;
    }

    public Clip getBeat1 ()
    {
        return beat1Clip;
    }

    public Clip getBeat2 ()
    {
        return beat2Clip;
    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        // TODO Auto-generated method stub
        
    }

}
