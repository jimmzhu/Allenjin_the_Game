
public interface Thing {
	
	public String getName();
	public String nextText();
	public Location getLocation();
	public Grid getGrid();
	public void putSelfInGrid(Grid gr, Location loc);
	public void removeSelfFromGrid();
	
	//for spicy things
	public void addSpice(Spice spice);
	public boolean hasSpice();
	public void checkSpices(AllenJin allen);
	
}
