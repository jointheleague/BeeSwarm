package org.wintrisstech;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * This project is designed as a starting point for students learning about
 * arrays. <p> Images in this project are public domain from
 * http://openclipart.org
 *
 * @author aaron@wintrisstech.org (Aaron VonderHaar)
 * @author http://wintrisstech.org
 */
public class BeeSwarm extends JComponent implements ActionListener
{

    private Image background;
    private Image beeLeft;
    private Image beeRight;
    
    private final int windowHeight = 751;
    private final int windowWidth = 1024;
    private final int queenWidth = 100;
    private final int queenHeight = queenWidth * 75 / 100;

    private int counter = 0;
    private int lastQueenX;
    private boolean queenIsMovingRight;
    private int queenX = windowWidth / 2;
    private int queenY = windowHeight / 2;

    public static void main(String[] args) throws IOException
    {
        JFrame window = new JFrame("Bees!");
        BeeSwarm game = new BeeSwarm();
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        Timer t = new Timer(30, game);
        t.start();
    }

    public BeeSwarm() throws IOException
    {
        background = ImageIO.read(getClass().getResource("carlitos_Cartoon_Landscape.png"));
        beeLeft = ImageIO.read(getClass().getResource("bee_left.png"));
        beeRight = ImageIO.read(getClass().getResource("bee_right.png"));
        // Initialize the queen's position
        updateQueen();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(windowWidth, windowHeight);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.drawImage(background, 0, 0, windowWidth, windowHeight, null);

        if (queenIsMovingRight)
        {
            g.drawImage(beeRight, queenX - queenWidth / 2,
                    queenY - queenHeight / 2,
                    queenWidth, queenHeight, null);
        }
        else
        {
            g.drawImage(beeLeft, queenX - queenWidth / 2,
                    queenY - queenHeight / 2,
                    queenWidth, queenHeight, null);

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        updateQueen();

        repaint();
    }

    private void updateQueen()
    {
        // Increment the counter used to calculate cr and ci.  The faster
        // we increment this, the faster the queen will move.
        counter += 2;

        // This is code taken from the Julia screensaver which calculates the 
        // position of a point that wanders around the screen.
        // http://www.jwz.org/xscreensaver/
        double cr = 1.5 * (Math.sin(Math.PI * (counter / 290.0))
                * Math.sin(counter * Math.PI / 210.0));
        double ci = 1.5 * (Math.cos(Math.PI * (counter / 310.0))
                * Math.cos(counter * Math.PI / 190.0));

        cr += 0.5 * Math.cos(Math.PI * counter / 395.0);
        ci += 0.5 * Math.sin(Math.PI * counter / 410.0);

        // Save the queen's last X position so we can calculate later which
        // direction it is moving
        lastQueenX = queenX;

        // Convert cr and ci (which range from -2 to +2) into X-Y coordinates
        // in our window
        queenX = (int) ((cr + 2) / 4 * windowWidth);
        queenY = (int) ((ci + 2) / 4 * windowHeight);

        // Calculate whether the queen is moving right or left
        if (queenX > lastQueenX)
        {
            queenIsMovingRight = true;
        }
        else
        {
            queenIsMovingRight = false;
        }
    }
}
