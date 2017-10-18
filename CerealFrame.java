import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

public class CerealFrame extends JFrame implements KeyListener {
	
	private ClassLoader cldr = this.getClass().getClassLoader();
	public static final int CELL_WIDTH = 22;
	public static final int CELL_HEIGHT = 22;
	private static final int STEP = CELL_WIDTH / 2;
	
	/* 
	 * sprite images
	 * cycles through image sequence when walking
	 */
	private final ImageIcon LEFT_STANDING = new ImageIcon(cldr.getResource("allenjin_leftstanding.gif"));
	private final ImageIcon LEFT_WALKING1 = new ImageIcon(cldr.getResource("allenjin_lefthandforward.gif"));
	private final ImageIcon LEFT_WALKING2 = new ImageIcon(cldr.getResource("allenjin_lefthandbackward.gif"));
	
	private final ImageIcon RIGHT_STANDING = new ImageIcon(cldr.getResource("allenjin_rightstanding.gif"));
	private final ImageIcon RIGHT_WALKING1 = new ImageIcon(cldr.getResource("allenjin_righthandforward.gif"));
	private final ImageIcon RIGHT_WALKING2 = new ImageIcon(cldr.getResource("allenjin_righthandbackward.gif"));
	
	private final ImageIcon UP_STANDING = new ImageIcon(cldr.getResource("allenjin_upstanding.gif"));
	private final ImageIcon UP_WALKING1 = new ImageIcon(cldr.getResource("allenjin_upleftstep.gif"));
	private final ImageIcon UP_WALKING2 = new ImageIcon(cldr.getResource("allenjin_uprightstep.gif"));
	
	private final ImageIcon DOWN_STANDING = new ImageIcon(cldr.getResource("allenjin_downstanding.gif"));
	private final ImageIcon DOWN_WALKING1 = new ImageIcon(cldr.getResource("allenjin_downleftstep.gif"));
	private final ImageIcon DOWN_WALKING2 = new ImageIcon(cldr.getResource("allenjin_downrightstep.gif"));
	
	private final ImageIcon[] LEFT_SEQUENCE = { LEFT_STANDING, LEFT_WALKING1,
														LEFT_STANDING, LEFT_WALKING2 };
	private final ImageIcon[] RIGHT_SEQUENCE = { RIGHT_STANDING, RIGHT_WALKING1,
														RIGHT_STANDING, RIGHT_WALKING2 };
	private final ImageIcon[] UP_SEQUENCE = { UP_STANDING, UP_WALKING1,
														UP_STANDING, UP_WALKING2 };
	private final ImageIcon[] DOWN_SEQUENCE = { DOWN_STANDING, DOWN_WALKING1,
														DOWN_STANDING, DOWN_WALKING2 };
	
	private static JLabel allenSprite;
	private int leftMoveIndex = 0;
	private int rightMoveIndex = 0;
	private int upMoveIndex = 0;
	private int downMoveIndex = 0;
	
	private boolean busy = false;
	private boolean battleMode = false;
	
	private JinController control;
	private CerealPanel display;
	private JTextArea messageArea;
	private World world;
	private ImageDatabase images;
	//private PokemonTrainer pokeTrainer;
	
	private AllenHandler allenhandler;
	
	public CerealFrame() {
		super("AllenJin: Pokémon version :D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		display = new CerealPanel();
		
		control = new JinController(this, display);
		world = control.getWorld(); //unnecessary
		images = world.getImages(); //unnecessary
		display.setImages(images);
		allenhandler = new AllenHandler();
	
		allenSprite = new JLabel(DOWN_STANDING);
		allenSprite.setBounds(getX(control.START_LOC),getY(control.START_LOC),CELL_WIDTH,CELL_HEIGHT);
		display.setBackground("outside/grid_1.0");
		display.addPlayer(allenSprite);
		display.setOpaque(true); //content panes must be opaque
		
		setContentPane(display);
		addKeyListener((KeyListener) this);
		
		//Display the window.
		pack();
		setVisible(true);

	}
	public void setAllenLoc(Location loc) {
		allenSprite.setLocation(locToPoint(loc));
	}
	private void hideAllen() {
		allenSprite.setVisible(false);
	}
	private void showAllen() {
		allenSprite.setVisible(true);
	}
	/*
	 * methods called during battle
	 * 
	 *
	 **/
	public void setBattleMode(boolean battle) {
		battleMode = battle;
		if (battleMode == true) {
			hideAllen();
		}
		else {
			showAllen();
		}
	}
	public void setBusy(boolean occupied) {
		busy = occupied;
	}
	
	
	public static void main(String[] args) {
		CerealFrame application = new CerealFrame();
	}
	
	
	
	public void advanceAllen(int dir) {
		allenhandler.advanceAllen(dir);
	}
	public void moveAllen(int dir) {
		allenhandler.moveAllen(dir);
	}
	
	/*
	 * methods keyPressed, keyReleased, and keyTyped
	 * from the KeyListener interface
	 *
	 **/
	
	// moves image sprite one STEP (half the length of a cell)
	// cycles through images using AllenHandler
	// updates allenjin's location every other STEP
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_SPACE) {
			String text;
			
			if (battleMode)
				text = control.battle();
			else
				text = control.getSpice();
			
			if (text != null) {
				display.showText(text);
				busy = true;
			} else {
				display.hideText();
				busy = false;
			}
		}
		
		if (busy)
			return;
		
		
		
		int x0 = allenSprite.getX();
		int y0 = allenSprite.getY();
		
		boolean horizontalStanding = allenSprite.getX() % CELL_WIDTH == 0;
		boolean verticalStanding = allenSprite.getY() % CELL_HEIGHT == 0;
		boolean standing = true;
		int dir = -1;
		
		if (keyCode == KeyEvent.VK_LEFT) {
			dir = Location.LEFT;
			standing = horizontalStanding;
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			dir = Location.RIGHT;
			standing = horizontalStanding;
		} else if (keyCode == KeyEvent.VK_UP) {
			dir = Location.UP;
			standing = verticalStanding;
		} else if (keyCode == KeyEvent.VK_DOWN) {
			dir = Location.DOWN;
			standing = verticalStanding;
		}
		
		if (dir >= 0) {
			if (standing)
				control.updateAllen(dir);
			else
				advanceAllen(dir);
		}
	}
	/*public void setBattleMode(PokemonTrainer trainer, boolean mode) {
		pokeTrainer = trainer;
	}*/
	// relocates sprite image to corresponding location stored in allenjin
	// updates moveIndex as necessary for cycling through images
	// standing == true if sprite image is not between grid cells
	public void keyReleased(KeyEvent e) {
		if (busy)
			return;
		
		boolean standing = (allenSprite.getX() % CELL_WIDTH == 0 &&
											allenSprite.getY() % CELL_HEIGHT == 0);
		
		int dir = control.getAllen().getDirection();
		
		switch (dir) {
			case Location.LEFT:
				allenSprite.setIcon(LEFT_STANDING);
				
				if (leftMoveIndex == 1)
					leftMoveIndex = 2;
				else if (leftMoveIndex == 3)
					leftMoveIndex = 0;
					
				break;
			case Location.RIGHT:
				allenSprite.setIcon(RIGHT_STANDING);
				
				if (rightMoveIndex == 1)
					rightMoveIndex = 2;
				else if (rightMoveIndex == 3)
					rightMoveIndex = 0;
					
				break;
			case Location.UP:
				allenSprite.setIcon(UP_STANDING);
				
				if (upMoveIndex == 1)
					upMoveIndex = 2;
				else if (upMoveIndex == 3)
					upMoveIndex = 0;
				
				break;
			case Location.DOWN:
				allenSprite.setIcon(DOWN_STANDING);
				
				if (downMoveIndex == 1)
					downMoveIndex = 2;
				else if (downMoveIndex == 3)
					downMoveIndex = 0;
				
				break;
		}
		if (!standing) {
			Location loc = control.getAllen().getLocation();
			allenSprite.setLocation(locToPoint(loc));
		}
		System.out.print(control.getAllen().getGrid().getName());
		System.out.print("  location: " + pointToLoc(allenSprite.getLocation()));
		System.out.println("  Allen's location: " + control.getAllen().getLocation());
		
	}
	public void keyTyped(KeyEvent e) {
	}
	private class AllenHandler {
		
		private void advanceAllen(int dir) {
			switch (dir) {
				case Location.LEFT:
					allenSprite.setLocation(allenSprite.getX()-STEP, allenSprite.getY());
					moveLeft();
					break;
				case Location.RIGHT:
					moveRight();
					allenSprite.setLocation(allenSprite.getX()+STEP, allenSprite.getY());
					break;
				case Location.UP:
					allenSprite.setLocation(allenSprite.getX(), allenSprite.getY()-STEP);
					moveUp();
					break;
				case Location.DOWN:
					allenSprite.setLocation(allenSprite.getX(), allenSprite.getY()+STEP);
					moveDown();
					break;
			}
		}
		private void moveAllen(int dir) {
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
		private void moveLeft() {
			leftMoveIndex++;
			if (leftMoveIndex >= 4)
				leftMoveIndex = 0;
			allenSprite.setIcon(LEFT_SEQUENCE[leftMoveIndex]);
		}
		private void moveRight() {
			rightMoveIndex++;
			if (rightMoveIndex >= 4)
				rightMoveIndex = 0;
			allenSprite.setIcon(RIGHT_SEQUENCE[rightMoveIndex]);
		}
		private void moveUp() {
			upMoveIndex++;
			if (upMoveIndex >= 4)
				upMoveIndex = 0;
			allenSprite.setIcon(UP_SEQUENCE[upMoveIndex]);
		}
		private void moveDown() {
			downMoveIndex++;
			if (downMoveIndex >= 4)
				downMoveIndex = 0;
			allenSprite.setIcon(DOWN_SEQUENCE[downMoveIndex]);
		}
	}
	
	/* 
	 * methods used to convert between Location and Point information
	 * used in conjunction with allenjin's location to determine image location
	 * 
	 **/
    private Location pointToLoc(Point pt) {
    	return new Location((int) pt.getY()/CELL_HEIGHT, (int) pt.getX()/CELL_WIDTH);
    }
    public static Point locToPoint(Location loc) {
    	return new Point(loc.getCol()*CELL_WIDTH, loc.getRow()*CELL_HEIGHT);
    }
    public static int getY(Location loc) {
    	return loc.getRow()*CELL_HEIGHT;
    }
    public static int getX(Location loc) {
    	return loc.getCol()*CELL_WIDTH;
    }
}