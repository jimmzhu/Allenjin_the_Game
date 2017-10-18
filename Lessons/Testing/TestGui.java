import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Point;

public class TestGui extends JPanel implements KeyListener, MouseListener {
	
	private JLabel fish;
	private JLayeredPane layeredPane;
	
	public TestGui() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 310));
        layeredPane.setBorder(BorderFactory.createTitledBorder(
                                    "Move Fish"));
		
		Location loc = new Location(3,4);
		ImageIcon icon;
        try {
        	icon = ImageDisplay.createImageIcon(loc.getClass());
	        fish = new JLabel(icon);
	        fish.setBounds(15,255,icon.getIconWidth(),icon.getIconHeight());
        }
        catch (Exception e) {
        }
        
        layeredPane.add(fish, new Integer(2), 0);
        layeredPane.addMouseListener(this);
        
        //add(Box.createRigidArea(new Dimension(0, 10)));
        add(layeredPane);
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("LayeredPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new TestGui();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.addKeyListener((KeyListener) newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        	case KeyEvent.VK_LEFT:
        		fish.setLocation(fish.getX()-fish.getWidth(), fish.getY());
        		break;
        	case KeyEvent.VK_RIGHT:
        		fish.setLocation(fish.getX()+fish.getWidth(), fish.getY());
        		break;
        	case KeyEvent.VK_UP:
        		fish.setLocation(fish.getX(), fish.getY()-fish.getHeight());
        		break;
        	case KeyEvent.VK_DOWN:
        		fish.setLocation(fish.getX(), fish.getY()+fish.getHeight());
        		break;
        	case KeyEvent.VK_SPACE:
        		
        		break;
        	case KeyEvent.VK_ESCAPE:
        		System.exit(0);
        		break;
        }
		System.out.println("location: (" + fish.getX() + ", " + fish.getY() + ")");
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
        System.out.println("height: " + fish.getHeight() + " width: " + fish.getWidth());
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

    }


    // from the MouseListener interface
    public void mouseExited(MouseEvent e) {

    }
}
