import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.awt.Point;

public class CerealPanel extends JPanel {
	private ClassLoader cldr = this.getClass().getClassLoader();
	
	private static final Dimension DIM = new Dimension(374,374);
	private static final Rectangle WINDOW = new Rectangle(DIM);
	
	private final ImageIcon DOWN_STANDING = new ImageIcon(cldr.getResource("allenjin_downstanding.gif"));
	private final ImageIcon RIGHT_STANDING = new ImageIcon(cldr.getResource("allenjin_rightstanding.gif"));
	private final ImageIcon CHARMELEON = new ImageIcon(cldr.getResource("pokemon/charmeleon.gif"));
	private final ImageIcon NINETAILS = new ImageIcon(cldr.getResource("pokemon/ninetails.gif"));
	private final ImageIcon BATTLE_BACKGROUND = new ImageIcon(cldr.getResource("battlescene.gif"));
	private final ImageIcon BLACK_BACKGROUND = new ImageIcon(cldr.getResource("black.gif"));
	
	private JLabel allenSprite, charmeleon, enemyPoke, enemyTrainer;
	
	
	private ImageIcon homeIcon;
	/* 
	 * sprite images
	 * cycles through image sequence when walking
	 */
	
	
	private static JLabel allenjin;
	private static JLabel bg;
	//private static int downMoveIndex = 0;
	
	private int numRows, numCols, originRow, originCol;
	private ImageDatabase images;
	private ImageDatabase pokemonImgs;
	private Location myLoc;
	private Map<Location, JLabel> spriteMap = new HashMap<Location, JLabel>();
	
	private JLayeredPane layeredPane;
	private JTextArea messageArea;
	private JLabel messageArrow;
	private Timer timer;
	
	private JinController jinController;
	
	public CerealPanel() {
		this(null);
	}
	public CerealPanel(ImageDatabase images) {
		
		this.images = images;
		pokemonImgs = new ImageDatabase();
		pokemonImgs.loadFile("pokemon/", "pokemon/pokemon.txt");
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(DIM);
		
		
		//for battling
		allenSprite = new JLabel(RIGHT_STANDING);
		charmeleon = new JLabel(CHARMELEON);
		enemyTrainer = new JLabel(DOWN_STANDING);
		enemyPoke = new JLabel(CHARMELEON);
		
		
		allenSprite.setBounds(88, 176, RIGHT_STANDING.getIconWidth(), RIGHT_STANDING.getIconHeight());
		charmeleon.setBounds(154, 176, CHARMELEON.getIconWidth(), CHARMELEON.getIconHeight());
		enemyTrainer.setBounds(264, 176, RIGHT_STANDING.getIconWidth(), RIGHT_STANDING.getIconHeight());
		enemyPoke.setBounds(198, 176, 29, 22);
		
		
		allenSprite.setVisible(false);
		charmeleon.setVisible(false);
		enemyPoke.setVisible(false);
		enemyTrainer.setVisible(false);
		
		
		
		//background
		bg = new JLabel(new ImageIcon());
		bg.setBounds(WINDOW);
		
		JLabel easterEgg = new JLabel(new ImageIcon(cldr.getResource("fish.gif")));
		easterEgg.setBounds(450,470,30,15);
		
		
		messageArea = new JTextArea(2, 20);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setBackground(new Color(0xFFFFFF));
        messageArea.setVisible(false);
        
        Border textBorder = BorderFactory.createCompoundBorder(
        	BorderFactory.createEtchedBorder(new Color(68,39,53), new Color(120,70,96)), 
        	BorderFactory.createLineBorder(Color.WHITE, 4)  );
        messageArea.setBorder(textBorder);
		messageArea.setBounds(11,300,352,63);
		
		ImageIcon arrowIcon = new ImageIcon("moreTextArrow.gif");
		messageArrow = new JLabel(arrowIcon);
		messageArrow.setVisible(false);
		messageArrow.setBounds(308,352,arrowIcon.getIconWidth(),arrowIcon.getIconHeight());
		
		
        layeredPane.add(messageArea, new Integer(2));
        layeredPane.add(messageArrow, new Integer(3));
		layeredPane.add(bg, new Integer(0));
		layeredPane.add(easterEgg, new Integer(0));
		
		layeredPane.add(allenSprite, new Integer(1));
		layeredPane.add(charmeleon, new Integer(1));
		layeredPane.add(enemyPoke, new Integer(1));
		layeredPane.add(enemyTrainer, new Integer(1));
		
		//add(Box.createRigidArea(new Dimension(0, 10)));
		add(layeredPane);
	}
	public void setImages(ImageDatabase images) {
		this.images = images;
	}
	public void addPlayer(JLabel label) {
		layeredPane.add(label, new Integer(1));
	}
	public void addSprite(String imgName, Location loc) {
		ImageIcon icon = images.getImageIcon(imgName);
		System.out.println(imgName + " icon exists? " + (icon != null));
		JLabel sprite = new JLabel(images.getImageIcon(imgName));
		sprite.setBounds(CerealFrame.getX(loc), CerealFrame.getY(loc), icon.getIconWidth(), icon.getIconHeight());
		spriteMap.put(loc, sprite);
		layeredPane.add(sprite, new Integer(1));
	}
	public Set<Location> getSpriteLocs() {
		return spriteMap.keySet();
	}
	
	public void turnSprite(Organism oo) {
		ImageIcon imgIcon = images.getImageIcon("organisms/" + oo.getName() + "_" + Location.getString(oo.getDirection()));
		spriteMap.get(oo.getLocation()).setIcon(imgIcon);
	}
	private void clearSprites() {
		for (Location loc : spriteMap.keySet()) {
			layeredPane.remove(spriteMap.get(loc));
			System.out.println("removing sprite at loc " + loc);
		}
	}
	public void setSprites(LinkedList<Organism> oList, LinkedList<Obstruction> vList) {
		//gets rid of images of all previous organisms
		for (Location loc : spriteMap.keySet()) {
			layeredPane.remove(spriteMap.get(loc));
			System.out.println("removing sprite at loc " + loc);
		}
		
		spriteMap = new HashMap<Location, JLabel>();
		
		
		String fileName;
		ImageIcon icon;
		JLabel sprite;
		Location loc;
		
		for (Organism oo : oList) {
			loc = oo.getLocation();
			icon = images.getImageIcon("organisms/" + oo.getName() + "_" + Location.getString(oo.getInitialDir()));
			
			if (icon == null)
				System.out.println("image " + oo.getName() + " not found");
			else
				System.out.println(oo.getName() + " sprite image loaded :D");
			
			sprite = new JLabel(icon);
			sprite.setBounds(loc.getCol()*CerealFrame.CELL_WIDTH, loc.getRow()*CerealFrame.CELL_HEIGHT,
													 CerealFrame.CELL_WIDTH, CerealFrame.CELL_HEIGHT);
			spriteMap.put(loc, sprite);
			layeredPane.add(sprite, new Integer(1));
		}

		for (Obstruction v : vList) {
			loc = v.getLocation();
			icon = images.getImageIcon("misc/" + v.getName());
			
			if (icon == null)
				System.out.println("image " + v.getName() + " not found");
			else
				System.out.println(v.getName() + " sprite image loaded :D");
			
			sprite = new JLabel(icon);
			sprite.setBounds(CerealFrame.getX(loc) + v.getXOffset(),
							CerealFrame.getY(loc) + v.getYOffset(), icon.getIconWidth(), icon.getIconHeight());

			spriteMap.put(loc, sprite);
			layeredPane.add(sprite, new Integer(1));
		}
		
		
	}
	public void setBackground(String imgName) {
		for (Location loc : spriteMap.keySet()) {
			layeredPane.remove(spriteMap.get(loc));
			System.out.println("removing sprite at loc " + loc);
		}
		
		System.out.println("loading image: " + imgName);
		if (images == null)
			return;
		
		ImageIcon background = images.getImageIcon(imgName);
		
		System.out.println("found image" + (background != null));
		if (background == null)
			return;
		
		homeIcon = background;
		bg.setIcon(background);
	}
	public void setBattleBackground(boolean battleMode, PokemonTrainer trainer) {
		if (battleMode == true) {
			bg.setIcon(BATTLE_BACKGROUND);
			for (Location loc : spriteMap.keySet()) {
				spriteMap.get(loc).setVisible(false);
			}
			ImageIcon pokeImg = pokemonImgs.getImageIcon("pokemon/" + trainer.getPokemon());
			enemyPoke.setIcon(pokeImg);
			
			ImageIcon trainerImg = images.getImageIcon("organisms/" + trainer.getName() + "_left");
			System.out.println("found image of Stevens: " + trainerImg != null);
			enemyTrainer.setIcon(trainerImg);
			
			allenSprite.setVisible(true);
			charmeleon.setVisible(true);
			enemyPoke.setVisible(true);
			enemyTrainer.setVisible(true);
			
			
		} else {
			bg.setIcon(homeIcon);
			for (Location loc : spriteMap.keySet()) {
				spriteMap.get(loc).setVisible(true);
			}
			
			allenSprite.setVisible(false);
			charmeleon.setVisible(false);
			enemyPoke.setVisible(false);
			enemyTrainer.setVisible(false);
		}
	}
	public void showText(String text) {
		messageArea.setVisible(true);
		
		if (text == null)
			messageArea.setText("O_O");
		else
			messageArea.setText(text);
	}
	public void hideText() {
		messageArea.setVisible(false);
	}
}