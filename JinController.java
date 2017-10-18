import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

public class JinController {

	public static final Location START_LOC = new Location(5,7);

	private CerealFrame guiFrame;
	private CerealPanel guiDisplay;
	private World allenWorld;

	private AllenJin allenjin;
	private PokemonTrainer pokeTrainer;
	private boolean allensTurn, battling;
	private String homeBackground;


	public JinController(CerealFrame frame, CerealPanel display) {
		guiFrame = frame;
		guiDisplay = display;

		allenWorld = /*a whole*/ new World(display);
		allenjin = new AllenJin(allenWorld, START_LOC);
	}
	public AllenJin getAllen() {
		return allenjin;
	}
	public World getWorld() {
		return allenWorld;
	}
	public void updateAllen(int dir) {
		boolean[] results = allenjin.go(dir);
/**/	if (results == null) {
			System.out.println("grid == null, fail DX");
			return;
		}
/**/	if (results.length != 3) {
			throw new IllegalArgumentException();
		}
		boolean moving = results[0];
		boolean switchGrid = results[1];
		boolean enteringDoor = results[2];

		if (moving)
			guiFrame.advanceAllen(dir);
		else {
			guiFrame.moveAllen(dir);
			if (dir != allenjin.getDirection())
				guiFrame.moveAllen(allenjin.getDirection());
		}
		if (switchGrid) {
			Grid gr = allenjin.getGrid();
			String imgName = gr.getName();
			LinkedList<Organism> organisms = gr.getOrganisms();
			LinkedList<Obstruction> sprites = gr.getSprites();	
			
			guiDisplay.setBackground(imgName);
			guiDisplay.setSprites(organisms, sprites);
			guiFrame.setAllenLoc(allenjin.getLocation());
		}
	}
	public String nothingDefault(boolean show) {
		if (show)
			return "O_O";
		else
			return null;
	}
	public String getSpice() {
		Thing t = allenjin.spicy();
		if (t == null)
			return null;


		String text = null;

		if (t instanceof TalkativeObstruction) {
			if (((TalkativeObstruction) t).hasMoreText()) {
				text = t.nextText();
			}
			else {
				((TalkativeObstruction) t).resetText();

				//you must beat pokémon trainers in order for met == true
				if (t instanceof Organism && !((Organism) t).hasMet()) {
					if (!t.getName().contains("fatty"))
						((Organism) t).meet();
					if (t instanceof PokemonTrainer) {				//deals with pokemon trainers
						pokeTrainer = (PokemonTrainer) t;
						guiFrame.setBattleMode(true);
						guiDisplay.setBattleBackground(true, pokeTrainer);
						battling = true;
						return battle();
					}
				}
				return null;
			}

			if (t instanceof Organism) {
				((Organism) t).face(allenjin.getDirection());
				guiDisplay.turnSprite((Organism) t);

			}
		}
		else {
			text = t.nextText();
			if (text == null && t.hasSpice())
				t.checkSpices(allenjin);
		}

		return text;
	}
	public String battle() {
		String battleText = null;
		
		
		if (!battling) {
			boolean victory = pokeTrainer.isDefeated();
			
			
			allenjin.recover();
			pokeTrainer.recover();
			allensTurn = false;
			
			guiFrame.setBattleMode(false);
			guiDisplay.setBattleBackground(false, pokeTrainer);
			guiDisplay.hideText();
			
			if (victory) {
				String skill = pokeTrainer.getName() + "Pass";
				allenjin.learnSkill(skill);
				
				if (pokeTrainer.hasSpice())
					pokeTrainer.checkSpices(allenjin);
				
				Grid gr = allenjin.getGrid();
				guiDisplay.setSprites(gr.getOrganisms(), gr.getSprites());
				
				return getSpice();
			}
			else {
				pokeTrainer.refresh();
				
				if (pokeTrainer.hasSpice())
					pokeTrainer.checkSpices(allenjin);
				
				if (pokeTrainer.isLeet4()) {
					String gridName = "inside/leet4_anteroom";
					allenjin.setGrid(allenWorld.getGrid(gridName));
					allenjin.setLocation(new Location(13,8));

					LinkedList<Organism> organisms = allenjin.getGrid().getOrganisms();
					LinkedList<Obstruction> sprites = allenjin.getGrid().getSprites();
					
					guiDisplay.setBackground(gridName);
					guiDisplay.setSprites(organisms, sprites);
					guiFrame.setAllenLoc(allenjin.getLocation());

				}
				return null;
			}
		}
		
		
		if (pokeTrainer.isDefeated()) {
			allenjin.levelup();
			battling = false;
			return pokeTrainer.getName() + "'s " + pokeTrainer.getPokemon() +
				" fainted! Charmeleon leveled up! XD     [max HP rose to " + allenjin.getMaxHp() + "]";
		}
		else if (allenjin.isDefeated()) {
			battling = false;
			return "D: Oh noes, Charmeleon fainted! x_x";
		}
		
		
		
		int damage = 0;
		if (allensTurn) {
			damage = allenjin.power();
			pokeTrainer.decreaseHpBy(damage);
			//guiDisplay.animate("charmeleon");
			battleText = "Charmeleon uses flamethrower!                                                      " +
						"Enemy " + pokeTrainer.getPokemon() + " loses " + damage + " life and goes down to " +
						pokeTrainer.getHp() + " HP!";
		}
		else {
			damage = pokeTrainer.power();
			allenjin.decreaseHpBy(damage);
			//guiDisplay.animate("enemy");
			battleText = "Enemy " + pokeTrainer.getPokemon() + " uses " + pokeTrainer.getAttack() +
						"!                                            " + "Charmeleon loses " + damage + " life and goes down to " +
						allenjin.getHp() + " HP!  D:";
		}
		allensTurn = !allensTurn;
		
		
		
		
		return battleText;
	}
}
