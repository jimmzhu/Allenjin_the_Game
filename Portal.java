/*
 * A Portal is a visual representation of a grid that can be placed
 * within another Grid. When the player walks into a Portal, the player
 * essentially walks into another Grid, whose information is stored
 * using the Portal class.
 *
 **/
public class Portal extends Obstruction {
	
	private boolean locked = true;
	private boolean isLadder = false;
	private String key;
	private boolean textOn = false;
	private int myDir = Location.DOWN;
	
	private Grid outGrid;
	private Location outLoc;
	
	
	public Portal(String name) {
		super(name);
	}
	public Portal(String name, Location loc) {
		super(name);
		outLoc = loc;
	}
	public Portal(String name, Grid gr, Location loc) {
		super(name);
		outGrid = gr;
		outLoc = loc;
	}
	public void setLadder() {
		isLadder = true;
	}
	public boolean isLadder() {
		return isLadder;
	}
	
	//just in case
	public void setOutLocation(Location loc) {
		outLoc = loc;
	}
	public void setOutGrid(Grid gr) {
		outGrid = gr;
	}
	public Location getOutLocation() {
		return outLoc;
	}
	public Grid getOutGrid() {
		return outGrid;
	}
	/*
	 * methods that store variables and return those variables as
	 * necessary in order to implement the Thing interface
	 *
	 **/
	public String nextText() {
		textOn = !textOn;
		if (textOn)
			return "Open sesame :O";
		else
			return null;
	}
}
