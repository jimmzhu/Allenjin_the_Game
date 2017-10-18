
public class Stairs extends Obstruction {
	private int direction;
	
	public Stairs(String name) {
		super(name);
		direction = Location.UP;
	}
	public Stairs(String name, int dir) {
		super(name);
		direction = dir;
	}
	public int getDirection() {
		return direction;
	}
	public String getText() {
		return "^^";
	}
}
