/*
 * GuiTest3.java
 *
 * Created on April 29, 2004, 9:49 PM
 *
 * Demonstrates mouse and keyboard input; border layout; and
 * graphics object - drawString() method.  A JTextArea object is
 * placed in the SOUTH area of the 'BorderLayout'.  When a key is
 * pressed or mouse button is clicked, a message shows up in the
 * text area.  Also, when the mouse is clicked, a 'graphics' string
 * appears in spot where mouse is clicked.
 *
 * @author  T. Neuhaus
 */


import java.awt.*;          // access to Container
import java.awt.event.*;    // access to WindowAdapter, WindowEvent
import javax.swing.*;       // access to JFrame and JComponents

public class GuiTest3 extends JFrame {
    
    final static int HORIZ_GAP = 5;
    final static int VERT_GAP = 5;
    
    private JTextArea textArea1, textArea2;
    private int xPos, yPos;
    
    /** Creates a new instance of GuiTest3 */
    public GuiTest3() {
        // STEP 1: must call super() first
        super("Demo of Mouse and Keyboard Input");
        
        // STEP 2: get content pane and set its layout
        Container container = getContentPane();
        container.setLayout( new BorderLayout(HORIZ_GAP, VERT_GAP) );
                    // set BorderLayout; horizonal and vertical gap
                    // between components should be >= 0
        
        // STEP 3: construct component(s), such as:
        //      JTextArea - displays text
        textArea1 = new JTextArea(2, 10);   // text area size 2H x 10W
        textArea1.setText("Press any key on keyboard or click mouse in gray area below...");
        textArea1.setEnabled(false);     // user can't type in text area
        
        textArea2 = new JTextArea(3, 10);	// 3H x 10W
        textArea2.setEnabled(false);
        
        // STEP 4: add components to the Container; note that with
        // BorderLayout, a 2nd argument is provided that specifies which
        // region to add the component; one component may be added to
        // each of the 5 regions: BorderLayout.NORTH, BorderLayout.SOUTH,
        // BorderLayout.EAST, BorderLayout.WEST, BorderLayout.CENTER
        container.add(textArea1, BorderLayout.NORTH);
        container.add(textArea2, BorderLayout.SOUTH);
        
        // STEP 5: register any needed event handlers 
        //      - each event handler is defined below as a private inner class -
        //      see section after main()
        //      - method addKeyListener is called to handle keyboard input;
        //      method addMouseListener is called to handle mouse input;
        //      where the argument is the appropriate type of event handler

        addKeyListener( new KeyHandler());
        addMouseListener( new MouseClickHandler() );

        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
        // be able to close your application!!!
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        
        // STEP 6: set window size and show window
        setSize( 500, 400);     // width=500, height=400
        setVisible(true);
    }
    
    // draw String at location where mouse was clicked
    public void paint (Graphics g )
    {
        // call superclass paint method
        super.paint(g);
        
        g.drawString("Clicked at [" + xPos + ", " + yPos + "]", xPos, yPos);
            // first argument is the String to display, next 2 arguments
            // are the x, y positions to place String, where upper left
            // corner of window is (0, 0).
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GuiTest3 application = new GuiTest3();
    }
    /******** PRIVATE INNER CLASSES FOR EVENT HANDLING ***************/
    /*
     * - Provide MouseListener event handlers for mouse events
     * - Provide KeyListener event handlers for key events
     *
     */
    private class KeyHandler implements KeyListener {
        // the following 3 methods need to be provided in order to
        // implement the KeyListener interface: 
        // keyPressed(), keyReleased(), keyTyped();
        // if you don't need some of these methods, leave method body
        // empty
        
        public void keyPressed ( KeyEvent event )
        {
            textArea2.setText("Key pressed: " + event.getKeyText(event.getKeyCode() ));
                    // message shows up in text area; note that
                    // method getKeyCode() gets a 'virtual key code' of the key
                    // pressed - for a list of virtual key constants, see online
                    // documentation for class KeyEvent in java.awt.event;
                    // method getKeyText() converts that code to a String containing
                    // the name of the key pressed
            
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
            		// sample code to test for a particular
            		// virtual key code
            		textArea2.setText("Escape key was pressed!");
        }
        
        public void keyReleased (KeyEvent event )
        {
            // called when key is released after a keyPressed or keyTyped event
        }
        
        public void keyTyped (KeyEvent event )
        {
            // only responds to pressing "non-action" keys; (action keys
            // include arrow key, Home, etc)
        }
        
    }   // end KeyHandler

//    private class MouseClickHandler implements MouseListener
            // the following 5 methods are required to implement MouseListener:
            // mousePressed(), mouseClicked(), mouseReleased(),
            // mouseEntered(), mouseExited(); to avoid having to provide
            // all 5, you can extend MouseAdapter instead, and override
            // only those of the 5 methods that you really need
    private class MouseClickHandler extends MouseAdapter
    {
        public void mouseClicked ( MouseEvent event )
        {
            xPos = event.getX();
            yPos = event.getY();
            textArea2.setText("Mouse clicked at [" + xPos + ", " + yPos + "]\n");
                // message shows up in text area
            
            repaint();  // message also shows up at location that clicked on
        }
    }   // end MouseClickHandler
        
}
