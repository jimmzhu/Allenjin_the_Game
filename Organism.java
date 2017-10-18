import java.util.ArrayList;

public class Organism extends TalkativeObstruction {
	
	private Location myLoc;
	private int initialDir, dir;
	protected boolean met;
	private ArrayList<String> firstText;
	private ArrayList<String> secondText;
	private Grid grid;
	
	public Organism() {
		super("ohai");
	}
	public Organism(String name, Location loc, int d, Grid gr) {
		super(name);
		initialDir = d;
		dir = d;
		met = false;
		putSelfInGrid(gr, loc);
	}
	public Organism(String name, Location loc, Grid gr) {
		super(name);
		initialDir = Location.DOWN;
		dir = initialDir;
		met = false;
		putSelfInGrid(gr, loc);
	}
	public int getDirection() {
		return dir;
	}
	public int getInitialDir() {
		return initialDir;
	}
	public void setDirection(int direction) {
		dir = direction;
	}
    
    
	public void setLocation (Location loc) {
		myLoc = loc;
	}
	public boolean hasMet() {
		return met;
	}
	public void face(int direction) {
		dir = direction + 180;
	}
	public void meet() {
		met = true;
		myText = secondText;
	}
	public void refresh() {
		met = false;
		myText = firstText;
	}
	public void setText(ArrayList<String> text) {
		super.setText(text);
		firstText = text;
	}
	public void setSecondaryText(ArrayList<String> text2) {
		secondText = text2;
	}
}
