/*
 * GuiTest5.java
 *
 * Created on May 4, 2004, 9:20 PM
 *
 * Adapted by T. Neuhaus
 * from "Developing Games in Java" by David Brackeen (2004):
 *      Listing 3.3 - MouseTest.java
 *
 *  A  mouse test. When in "trail mode", draws "Hello World!" messages
 *   at the locations of the cursor. Left click on mouse to turn 
 *   "trail mode" on and off.  Defaults to "trail mode" = off.
 *   Use the mouse wheel (if available) to change colors.
 *
 *CHECK: need for Thread, Runnable, run, synchronize; vs. repaint
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Point;

public class GuiTest5 extends JFrame implements KeyListener,
    MouseMotionListener, MouseListener, MouseWheelListener, Runnable
{

    private static final int TRAIL_SIZE = 10;
    private static final Color[] COLORS = {
        Color.blue, Color.white, Color.black, Color.yellow, Color.magenta
    };

    private LinkedList trailList;
    private boolean trailMode;
    private int colorIndex;

    public static void main(String[] args) {

        GuiTest5 application = new GuiTest5();
    }
   

    public GuiTest5() {
        super("Demo: Dragging Mouse and Synchronization");
        
        Thread thread = new Thread(this);
        thread.start();
        
        trailList = new LinkedList();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        
        setSize( 275, 170);
        setVisible(true);
        
    }


    public synchronized void paint(Graphics g) {
//        System.out.println("Executing paint method...");
        int count = trailList.size();

        if (count > 1 && !trailMode) {
            count = 1;
//            System.out.println("Count reset to 1");
        }


        g.setColor(COLORS[colorIndex]);
        g.drawString("MouseTest. Left click to toggle trail mode on/off.", 10, 40);
        g.drawString("Turn mouse wheel to change colors.  Press Escape to exit.", 10, 50); 
                                  

        // draw mouse trail
        if (trailMode) {
            for (int i=0; i<count; i++) {
//                System.out.println("Drawing mouse trail...");
                Point p = (Point)trailList.get(i);
                g.drawString("Hello World!", (int)p.getX(), (int)p.getY());
            }
        }
    }

    // needed to implement Runnable, which supports threads
    public void run()
    {
        // empty body 
    }
    
    // from the MouseListener interface
    public void mousePressed(MouseEvent e) {
        trailMode = !trailMode;
        System.out.println("trailMode: " + trailMode);
    }


    // from the MouseListener interface
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }


    // from the MouseListener interface
    public void mouseClicked(MouseEvent e) {
        // called after mouse is released - ignore it
    }


    // from the MouseListener interface
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseListener interface
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseMotionListener interface
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }


    // from the MouseMotionListener interface
    public synchronized void mouseMoved(MouseEvent e) {
        System.out.println("X, Y: " + e.getX() + " " + e.getY());
        Point p = new Point(e.getX(), e.getY());
        trailList.addFirst(p);
        while (trailList.size() > TRAIL_SIZE) {
            trailList.removeLast();
        }
        repaint();
    }


    // from the MouseWheelListener interface
    public void mouseWheelMoved(MouseWheelEvent e) {
        colorIndex = (colorIndex + e.getWheelRotation()) %
            COLORS.length;

        if (colorIndex < 0) {
            colorIndex+=COLORS.length;
        }
//        Window window = screen.getFullScreenWindow();
//        window.setForeground(COLORS[colorIndex]);
    }


    // from the KeyListener interface
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // exit the program
            System.exit(0);
        }
    }


    // from the KeyListener interface
    public void keyReleased(KeyEvent e) {
        // do nothing
    }


    // from the KeyListener interface
    public void keyTyped(KeyEvent e) {
        // do nothing
    }
}


