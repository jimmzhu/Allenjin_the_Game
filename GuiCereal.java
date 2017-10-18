import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Point;

public class GuiCereal extends JPanel implements KeyListener {
	
	private static final int STEP = Grid.CELL_WIDTH / 2;
	private static final Dimension WINDOW = new Dimension(310, 300);
	/* 
	 * sprite images
	 * cycles through image sequence when walking
	 */
	private static final ImageIcon LEFT_STANDING = new ImageIcon("test_leftstanding.gif");
	private static final ImageIcon LEFT_WALKING = new ImageIcon("test_leftwalking.gif");
	private static final ImageIcon RIGHT_STANDING = new ImageIcon("test_rightstanding.gif");
	private static final ImageIcon RIGHT_WALKING = new ImageIcon("test_rightwalking.gif");
	private static final ImageIcon UP_STANDING = new ImageIcon("test_upstanding.gif");
	private static final ImageIcon UP_WALKING1 = new ImageIcon("test_upwalking1.gif");
	private static final ImageIcon UP_WALKING2 = new ImageIcon("test_upwalking2.gif");
	private static final ImageIcon DOWN_STANDING = new ImageIcon("test_downstanding.gif");
	private static final ImageIcon DOWN_WALKING1 = new ImageIcon("test_downwalking1.gif");
	private static final ImageIcon DOWN_WALKING2 = new ImageIcon("test_downwalking2.gif");
	private static final ImageIcon[] UP_SEQUENCE = { UP_STANDING, UP_WALKING1,
														UP_STANDING, UP_WALKING2 };
	private static final ImageIcon[] DOWN_SEQUENCE = { DOWN_STANDING, DOWN_WALKING1,
														DOWN_STANDING, DOWN_WALKING2 };
	
	private static JLabel allenjin, bg;
	private static int downMoveIndex = 0;
	
	private JLayeredPane layeredPane;
	private JTextArea textArea;
	private Timer timer;
	
	private JinController jinController;
	
	public GuiCereal(JinController jctrl) {
		jinController = jctrl;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(WINDOW);
		layeredPane.setBorder(BorderFactory.createTitledBorder(
									"Move allenjin with Arrow Keys"));
		
		allenjin = new JLabel(DOWN_STANDING);
		allenjin.setBounds(200,200,Grid.CELL_WIDTH,Grid.CELL_HEIGHT);
		
		ImageIcon background = new ImageIcon("background.gif");
		bg = new JLabel(background);
		bg.setBounds(new Rectangle(WINDOW));
		
		JLabel easterEgg = new JLabel(new ImageIcon("fish.gif"));
		easterEgg.setBounds(450,470,30,15);
		
		layeredPane.add(bg, new Integer(0));
		layeredPane.add(allenjin, new Integer(1));
		layeredPane.add(easterEgg, new Integer(0));
		
		//add(Box.createRigidArea(new Dimension(0, 10)));
		add(layeredPane);
	}
	public void keyPressed(KeyEvent e) {
		final double x0 = allenjin.getX();
		final double y0 = allenjin.getY();
		boolean standing = (x0 % Grid.CELL_WIDTH == 0 &&
											y0 % Grid.CELL_WIDTH == 0);
		
		int dir = -1;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				dir = Location.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				dir = Location.RIGHT;
				break;
			case KeyEvent.VK_UP:
				dir = Location.UP;
				break;
			case KeyEvent.VK_DOWN:
				dir = Location.DOWN;
				break;
			case KeyEvent.VK_SPACE:
				//if facing person, talk to person
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
		}
		if (dir >= 0) {
			if (standing)
				jinController.updateAllen(dir);
			else
				moveAllen(dir);
		}
	}
	public void moveAllen(int dir) {
		AllenHandler.moveAllen(dir);
	}
	public void keyReleased(KeyEvent e) {
		int col = allenjin.getX() / Grid.CELL_WIDTH;
		int row = allenjin.getY() / Grid.CELL_HEIGHT;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				allenjin.setLocation(col*Grid.CELL_WIDTH, allenjin.getY());
				allenjin.setIcon(LEFT_STANDING);
				break;
			case KeyEvent.VK_RIGHT:
				allenjin.setLocation((col+1)*Grid.CELL_WIDTH, allenjin.getY());
				allenjin.setIcon(RIGHT_STANDING);
				break;
			case KeyEvent.VK_UP:
				allenjin.setLocation(allenjin.getX(), row*Grid.CELL_HEIGHT);
				allenjin.setIcon(UP_STANDING);
				break;
			case KeyEvent.VK_DOWN:
				allenjin.setLocation(allenjin.getX(), (row+1)*Grid.CELL_HEIGHT);
				allenjin.setIcon(DOWN_STANDING);
				if (downMoveIndex % 2 != 0)
					downMoveIndex++;
				if (downMoveIndex >= 4)
					downMoveIndex = 0;
				break;
			case KeyEvent.VK_SPACE:
				//handled in keyPressed method
				break;
		}
		System.out.println("location: (" + allenjin.getX() + ", " + allenjin.getY() + ")");
		//timer.stop();
	}
	public void keyTyped(KeyEvent e) {
		
	}
	private static class AllenHandler {
		
		private static void moveAllen(int dir) {
			switch (dir) {
				case Location.LEFT:
					moveLeft();
					break;
				case Location.RIGHT:
					moveRight();
					break;
				case Location.UP:
					moveUp();
					break;
				case Location.DOWN:
					moveDown();
					break;
			}
		}
		private static void moveLeft() {
			allenjin.setLocation(allenjin.getX()-STEP, allenjin.getY());
			
			if (allenjin.getIcon().equals(LEFT_WALKING))
				allenjin.setIcon(LEFT_STANDING);
			else
				allenjin.setIcon(LEFT_WALKING);
		}
		private static void moveRight() {
			allenjin.setLocation(allenjin.getX()+STEP, allenjin.getY());
			
			if (allenjin.getIcon().equals(RIGHT_WALKING))
				allenjin.setIcon(RIGHT_STANDING);
			else
				allenjin.setIcon(RIGHT_WALKING);
		}
		private static void moveUp() {
			allenjin.setLocation(allenjin.getX(), allenjin.getY()-STEP);
			
			if (allenjin.getIcon().equals(UP_WALKING1))
				allenjin.setIcon(UP_STANDING);
			else
				allenjin.setIcon(UP_WALKING1);
		}
		private static void moveDown() {
			allenjin.setLocation(allenjin.getX(), allenjin.getY()+STEP);
			
			downMoveIndex++;
			if (downMoveIndex >= 4)
				downMoveIndex = 0;
			allenjin.setIcon(DOWN_SEQUENCE[downMoveIndex]);
		}
	}
}
