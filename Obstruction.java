import java.util.ArrayList;

public class Obstruction implements Thing {
	private ArrayList<Spice> spices = new ArrayList<Spice>();
	private String myName, text;
	private boolean textOn = false;
	private boolean visible;
	private int xOffset = 0, yOffset = 0;
	private Location myLoc;
	private Grid grid;
	
	public Obstruction () {
		//do nothing
	}
	public Obstruction (String name) {
		myName = name.replace('_', ' ');
		text = "Ohai, i am a " + myName;
	}
	public Obstruction (String name, String txt) {
		myName = name;
		text = txt.replace('_', ' ');
	}
	public void setText(String txt) {
		text = txt;
	}
	public String getName() {
		return myName;
	}
	public String nextText() {
		textOn = !textOn;
		if (textOn)
			return text;
		else
			return null;
	}
	
	
	public void setVisible() {
		visible = true;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setXOffset(int num) {
		xOffset = num;
	}
	public void setYOffset(int num) {
		yOffset = num;
	}
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
	
	public Location getLocation() {
		return myLoc;
	}
	public Grid getGrid() {
		return grid;
	}
	
	public void putSelfInGrid(Grid gr, Location loc)
	{
		if (grid != null)
			throw new IllegalStateException("This actor is already contained in a grid.");
		
		gr.put(loc, this);
		myLoc = loc;
		grid = gr;
	}

    /**
     * Removes this actor from its grid. <br />
     * Precondition: This actor is contained in a grid
     */
	public void removeSelfFromGrid()
	{
		if (grid == null)
			throw new IllegalStateException("This actor is not contained in a grid.");
		if (grid.get(myLoc) != this)
			throw new IllegalStateException("The grid contains a different actor at location " + myLoc + ".");

		grid.remove(myLoc);
		grid = null;
		myLoc = null;
	}
	
	
	
	/* 
	 * following methods used for "spicy" Obstructions that 
	 * perform special actions
	 *
	 */
	public void addSpice(Spice spice) {
		System.out.println(myName + " has spice");
		spices.add(spice);
	}
	public boolean hasSpice() {
		return !spices.isEmpty();
	}
	public void checkSpices(AllenJin allen) {
		String skill = null;
		for (Spice sp : spices) {
			if (sp.isSpicy(allen)) {
				skill = sp.activateSpice(allen);
				if (skill != null) {
					if (skill.charAt(0) == '!')
						allen.unLearnSkill(skill.substring(1,skill.length()));
					else
						allen.learnSkill(skill);
				}
			}
		}
	}
}
