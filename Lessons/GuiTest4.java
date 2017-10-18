/*
 * GuiTest4.java
 *
 * Created on May 5, 2004
 *
 * @author  T. Neuhaus
 *
 * Creates a window and draws some graphics - lines, rectangles, ovals.
 *
 */

// IMPORT the following classes to access GUI functionality:
import java.awt.*;          // access to Container
import java.awt.event.*;    // access to WindowAdapter, WindowEvent
import javax.swing.*;       // access to JFrame and JComponents

// The class needs to extend JFrame:
public class GuiTest4 extends JFrame {
	
	// instance variables
	Image fish;
	
	/** Creates a new instance of gui_test - sets up GUI */
    public GuiTest4() {
        // STEP 1: must call super() first
        super("Demo Graphics: Lines, Rectangles, Ovals");
    
    // STEPS 2-5: not needed here because this example does not
    // include any GUI "components"...
        // STEP 2: get content pane and set its layout
        // STEP 3: construct component(s), such as:     
        // STEP 4: add all components to the Container;
        // STEP 5: register any needed event handlers 

		// need to load up "fish" image used below
		fish = new ImageIcon("fish.gif").getImage();

        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
        // be able to close your application!!!
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        
        // STEP 6: set window size and show window
        setSize( 400, 170);
        setVisible(true);
        
    }
    
    /*
     * This is the graphics demo section.  When you want to use
     * any methods of the Graphics class, you need to call the
     * Component method paint(), which takes a Graphics object
     * as an argument.  The run-time system is in control of when
     * paint() executes, not the programmer, because drawing graphics
     * is "event-driven" (e.g. when a window is covered or uncovered).
     * If the programmer needs to call paint(), the way to do that is by calling the method
     * repaint() instead.  (See the use of repaint() in GuiTest3.)
     *
     * Here are just some of the Graphics methods:
     *  - public void setColor (Color c)
     *			// sets the current color - see Color class for
     *			// available predefined colors
     *	- public void drawLine(int x1, int y1, int x2, int y2)
     *			// draws line between (x1, y1) and (x2, y2)
     *  - public void drawRect (int x, int y, int width, int height)
     *			// draws rectangle with top left corner at (x,y)
     *  - public void fillRect (int x, int y, int width, int height)
     *			// draws a solid rectangle with top left corner at (x,y)
     *  - public void drawOval (int x, int y, int width, int height)
     *			// draws oval in current color with specified width
     *			// and height; bounding rectangle's top left corner
     *			// is at (x,y) and oval touches all sides of bounding
     *			// rectangle
     *	- public void fillOval (int x, int y, int width, int height)
     *			// see drawOval
     *	- public void drawImage (Image img, int x, int y, ImageObserver observer)
     *			// draws image loaded from a GIF, JPEG or PNG file - upper
     *			// left corner at (x, y) - usually make 'observer' be 'this'
     */
     public void paint (Graphics g )
     {
     	// call superclass's paint method
     	super.paint(g);
     	
     	// draw a red line; 2 blue rectangles, one filled and one
     	// not; and 2 magenta ovals, one filled and one not
     	
     	g.setColor(Color.red);
     	g.drawLine(5, 30, 350, 30);
     	
     	g.setColor(Color.blue);
     	g.drawRect(5, 40, 90, 55);
     	g.fillRect(100, 40, 90, 55);
     	
     	g.setColor(Color.magenta);
     	g.drawOval(195, 100, 90, 55);
     	g.fillOval(290, 100, 90, 55);
     	
     	g.drawImage(fish, 300, 40, this);	// 'fish' was loaded in constructor
     }
     
    public static void main(String[] args) {
        GuiTest4 application = new GuiTest4();
    }
    	
}
