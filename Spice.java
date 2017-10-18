import java.util.LinkedList;

public class Spice {
	String triggerSkill, spicyAction;
	Object[] actionParam;
	boolean activated;
	boolean not;
	
	public Spice(String skill, String action) {		
		if (skill.charAt(0) == '!') {
			skill = skill.substring(1, skill.length());
			not = true;
		}
		triggerSkill = skill;
		spicyAction = action;
	}
	
	
	public void setParameters(Object[] parameters) {
		actionParam = parameters;
	}
	public boolean isSpicy(AllenJin allen) {
		if (not) 
			return !(allen.hasSkill(triggerSkill));
		else
			return allen.hasSkill(triggerSkill);
	}
	
	//just in case
	public void setSpice(String action) {
		spicyAction = action;
	}
	public void setTrigger(String skill) {
		triggerSkill = skill;
	}
	
	
	// only to be used in conjunction w/ isSpicy()	For example:
	// if (t.isSpicy(allenjin)) t.activateSpice();
	public String activateSpice(AllenJin allen) {
		if (activated)
			return null;
		activated = true;
		
/**/	System.out.println("activating spice: " + spicyAction);
		if (spicyAction.equals("learn")) {
			if (actionParam.length != 1)
				throw new IllegalArgumentException("You're doing it wrong");
			
			String skill = (String) actionParam[0];
			return skill;
		}
		else if (spicyAction.equals("move")) {
			if (actionParam.length != 4)
				throw new IllegalArgumentException("You're doing it wrong");
			moveSpice();
		}
		else if (spicyAction.equals("transform")) {
			if (actionParam.length != 3)
				throw new IllegalArgumentException("You're doing it wrong");
			transformSpice();
		}
		
		//cheats
		else if (spicyAction.equals("reset")) {
			LinkedList<Grid> liveGrids = allen.getWorld().getLiveGrids();
			LinkedList<Organism> oList;
			
			for (Grid gr : liveGrids) {
				oList = gr.getOrganisms();
				for (Organism oo : oList)
					oo.refresh();
			}
			activated = false;
		}
		else if (spicyAction.equals("drain")) {
			allen.setMaxHp(5);
			allen.setExp(1);
			activated = false;
		}
		else if (spicyAction.equals("buff")) {
			allen.setMaxHp(108);
			allen.setExp(27);
			activated = false;
		}
		
		
		return null;
	}
	private void moveSpice() {
		Grid startGrid = (Grid) actionParam[0];
		Location startLoc = (Location) actionParam[1];
		Grid endGrid = (Grid) actionParam[2];
		Location endLoc = (Location) actionParam[3];
		
		Thing t = startGrid.get(startLoc);
		
		if (t == null) return;
		
		t.removeSelfFromGrid();
		t.putSelfInGrid(endGrid, endLoc);
		
		if (t instanceof Organism)
			((Organism) t).meet();
		
	}
	private void transformSpice() {
		Grid gr = (Grid) actionParam[0];
		Location loc = (Location) actionParam[1];
		Thing product = (Thing) actionParam[2];
		
		Thing t = gr.get(loc);
		if (t != null)
			t.removeSelfFromGrid();
		
		if (product != null)
			product.putSelfInGrid(gr, loc);
	}
}
