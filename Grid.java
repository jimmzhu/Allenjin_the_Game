import java.util.ArrayList;
import java.util.LinkedList;

/* 
 * Main differences between this Grid class and the GridWorld Grid class:
 * 		-can only store Objects of type Thing
 * 		-DOES NOT need to keep track of the location of Allenjin, the player
 * 		-DOES NOT need to keep track of non-Organism Things
 * 		-DOES need to keep track of all Organisms contained in the grid
 **/
public class Grid {
	private static final int GRID_HEIGHT = 17;
	private static final int GRID_WIDTH = 17;

    private Thing[][] occupantArray;
    private LinkedList<Organism> oList = new LinkedList<Organism>();
    private LinkedList<Obstruction> vList = new LinkedList<Obstruction>();

    private String name;
    private Location locInWorld = null;
    private boolean savePlace = false;
	
	
    public Grid(String name, int numRows, int numCols)
    {
    	this.name = name;
    	
        if (numRows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (numCols <= 0)
            throw new IllegalArgumentException("cols <= 0");
            
        occupantArray = new Thing[numRows][numCols];
    }
    public Grid(int numRows, int numCols)
    {
        if (numRows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (numCols <= 0)
            throw new IllegalArgumentException("cols <= 0");
            
        occupantArray = new Thing[numRows][numCols];
    }
    public void savePlace() {
    	savePlace = true;
    }
    public boolean isSaved() {
    	return savePlace;
    }
    public void setLocInWorld(Location loc) {
    	locInWorld = loc;
    }
    public Location getLocInWorld() {
    	return locInWorld;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    public String getName() {
    	return name;
    }
    
    public int getNumRows() {
        return occupantArray.length;
    }
    public int getNumCols() {
        return occupantArray[0].length;
    }
    public LinkedList<Organism> getOrganisms() {
    	return oList;
    }
    public LinkedList<Obstruction> getSprites() {
		return vList;
    }
    
    public boolean isInBounds(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public Thing get(Location loc) {
        if (!isInBounds(loc))
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        return occupantArray[loc.getRow()][loc.getCol()];
    }

    public Thing put(Location loc, Thing obj) {
        if (!isInBounds(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        Thing oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        
        if (obj instanceof Organism) {
        	oList.add((Organism) obj);
        } else if (obj instanceof Obstruction && ((Obstruction) obj).isVisible()) {
			vList.add((Obstruction) obj);
		}
	
        
        return oldOccupant;
    }
    public Thing remove(Location loc)
    {
        if (!isInBounds(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        Thing r = get(loc);
        
        if (r instanceof Organism) {
        	oList.remove((Organism) r);
        }
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return r;
    }
}

