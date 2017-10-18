package Testing;

import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Point;

public class CerealPanel extends JPanel implements KeyListener {
	
	public static final int CELL_WIDTH = 22;
	public static final int CELL_HEIGHT = 22;
	private static final int STEP = CELL_WIDTH / 2;
	private static final Dimension WINDOW = new Dimension(310, 300);
	/* 
	 * sprite images
	 * cycles through image sequence when walking
	 */
	private static final ImageIcon LEFT_STANDING = new ImageIcon("allenjin_leftstanding.gif");
	private static final ImageIcon LEFT_WALKING1 = new ImageIcon("allenjin_lefthandforward.gif");
	private static final ImageIcon LEFT_WALKING2 = new ImageIcon("allenjin_lefthandbackward.gif");
	
	private static final ImageIcon RIGHT_STANDING = new ImageIcon("allenjin_rightstanding.gif");
	private static final ImageIcon RIGHT_WALKING1 = new ImageIcon("allenjin_righthandforward.gif");
	private static final ImageIcon RIGHT_WALKING2 = new ImageIcon("allenjin_righthandbackward.gif");
	
	private static final ImageIcon UP_STANDING = new ImageIcon("allenjin_upstanding.gif");
	private static final ImageIcon UP_WALKING1 = new ImageIcon("allenjin_upleftstep.gif");
	private static final ImageIcon UP_WALKING2 = new ImageIcon("allenjin_uprightstep.gif");
	
	private static final ImageIcon DOWN_STANDING = new ImageIcon("allenjin_downstanding.gif");
	private static final ImageIcon DOWN_WALKING1 = new ImageIcon("allenjin_downleftstep.gif");
	private static final ImageIcon DOWN_WALKING2 = new ImageIcon("allenjin_downrightstep.gif");
	
	private static final ImageIcon[] LEFT_SEQUENCE = { LEFT_STANDING, LEFT_WALKING1,
														LEFT_STANDING, LEFT_WALKING2 };
	private static final ImageIcon[] RIGHT_SEQUENCE = { RIGHT_STANDING, RIGHT_WALKING1,
														RIGHT_STANDING, RIGHT_WALKING2 };
	private static final ImageIcon[] UP_SEQUENCE = { UP_STANDING, UP_WALKING1,
														UP_STANDING, UP_WALKING2 };
	private static final ImageIcon[] DOWN_SEQUENCE = { DOWN_STANDING, DOWN_WALKING1,
														DOWN_STANDING, DOWN_WALKING2 };
	
	private static JLabel allenjin, bg;
	private static int leftMoveIndex = 0;
	private static int rightMoveIndex = 0;
	private static int upMoveIndex = 0;
	private static int downMoveIndex = 0;
	
	private JLayeredPane layeredPane;
	private JTextArea textArea;
	private Timer timer;
	
	private JinController jinController;
	
	public CerealPanel(JinController jctrl) {
		jinController = jctrl;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(WINDOW);
		layeredPane.setBorder(BorderFactory.createTitledBorder(
									"Move allenjin with Arrow Keys"));
		
		allenjin = new JLabel(DOWN_STANDING);
		allenjin.setBounds(200,200,CELL_WIDTH,CELL_HEIGHT);
		
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
		boolean standing = (x0 % CELL_WIDTH == 0 &&
											y0 % CELL_WIDTH == 0);
		
		int dir = -1;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				dir = 270;
				break;
			case KeyEvent.VK_RIGHT:
				dir = 90;
				break;
			case KeyEvent.VK_UP:
				dir = 0;
				break;
			case KeyEvent.VK_DOWN:
				dir = 180;
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
		/*int delay = 10000;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//nothing
			}
		};
		Timer timer = new Timer(delay, taskPerformer);
		//timer.setRepeats(false);
		timer.start();
		
		try {
			Thread.sleep(200);
			repaint();
		}
		catch (Exception error) {
		}*/
	}
	public void moveAllen(int dir) {
		AllenHandler.moveAllen(dir);
	}
	public void keyReleased(KeyEvent e) {
		int col = allenjin.getX() / CELL_WIDTH;
		int row = allenjin.getY() / CELL_HEIGHT;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				allenjin.setLocation(col*CELL_WIDTH, allenjin.getY());
				allenjin.setIcon(LEFT_STANDING);
				if (leftMoveIndex == 1)
					leftMoveIndex = 2;
				else if (leftMoveIndex == 3)
					leftMoveIndex = 0;
				break;
			case KeyEvent.VK_RIGHT:
				allenjin.setLocation((col+1)*CELL_WIDTH, allenjin.getY());
				allenjin.setIcon(RIGHT_STANDING);
				if (rightMoveIndex == 1)
					rightMoveIndex = 2;
				else if (rightMoveIndex == 3)
					rightMoveIndex = 0;
				break;
			case KeyEvent.VK_UP:
				allenjin.setLocation(allenjin.getX(), row*CELL_HEIGHT);
				allenjin.setIcon(UP_STANDING);
				if (upMoveIndex == 1)
					upMoveIndex = 2;
				else if (upMoveIndex == 3)
					upMoveIndex = 0;
				break;
			case KeyEvent.VK_DOWN:
				allenjin.setLocation(allenjin.getX(), (row+1)*CELL_HEIGHT);
				allenjin.setIcon(DOWN_STANDING);
				if (downMoveIndex == 1)
					downMoveIndex = 2;
				else if (downMoveIndex == 3)
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
				case 270:
					moveLeft();
					break;
				case 90:
					moveRight();
					break;
				case 0:
					moveUp();
					break;
				case 180:
					moveDown();
					break;
			}
		}
		private static void moveLeft() {
			allenjin.setLocation(allenjin.getX()-STEP, allenjin.getY());
			
			leftMoveIndex++;
			if (leftMoveIndex >= 4)
				leftMoveIndex = 0;
			allenjin.setIcon(LEFT_SEQUENCE[leftMoveIndex]);
		}
		private static void moveRight() {
			allenjin.setLocation(allenjin.getX()+STEP, allenjin.getY());
			
			rightMoveIndex++;
			if (rightMoveIndex >= 4)
				rightMoveIndex = 0;
			allenjin.setIcon(RIGHT_SEQUENCE[rightMoveIndex]);
		}
		private static void moveUp() {
			allenjin.setLocation(allenjin.getX(), allenjin.getY()-STEP);
			
			upMoveIndex++;
			if (upMoveIndex >= 4)
				upMoveIndex = 0;
			allenjin.setIcon(UP_SEQUENCE[upMoveIndex]);
		}
		private static void moveDown() {
			allenjin.setLocation(allenjin.getX(), allenjin.getY()+STEP);
			
			downMoveIndex++;
			if (downMoveIndex >= 4)
				downMoveIndex = 0;
			allenjin.setIcon(DOWN_SEQUENCE[downMoveIndex]);
		}
	}
	public void setBackground(String imgName) {
		System.out.println("images loaded: " + (images != null));
		if (images == null)
			return;
		
		ImageIcon background = images.getImageIcon(imgName);
		
		if (background == null) {
			System.out.println("image not found");
			return;
		}
		else System.out.println("image found");
		
		
		bg = new JLabel(background);
		bg.setBounds(WINDOW);
		repaint();
	}
}
