import java.util.HashMap;

public class AllenJin {
	private String name = "Allenjin";
	private int myDir;
	private Location loc;
	private Grid gr;
	private World world;
	private int exp;
	private int hp;
	private int maxHp;
	private HashMap<String,Boolean> skillz;

	public AllenJin(World world, Location l) {
		this.world = world;
		gr = world.getGrid(1,0);

		loc = l;
		myDir = 180;

		exp = 2;
		maxHp = 7;
		hp = maxHp;
		skillz = new HashMap<String,Boolean>();
		learnSkill("nothing");
	}
	public void learnSkill(String name) {
		skillz.put(name, true);
/**/	System.out.println("allen learned " + name + "! :D");
	}
	public void unLearnSkill(String name) {
		skillz.put(name, false);
	}
	public boolean hasSkill(String name) {
		if (skillz.containsKey(name))
			return skillz.get(name);
		else
			return false;
	}
	public int getDirection()
	{
		return myDir;
	}
	public Grid getGrid()
	{
		return gr;
	}
	public World getWorld()
	{
		return world;
	}
	public Location getLocation() {

		return loc;
	}
	public void setGrid(Grid grid) {
		gr = grid;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public boolean[] go(int dir)
	{
		myDir = dir;

		//impossible case
		if (gr == null)
			return null;

	//	boolean moving, offGrid, enteringDoor;
		boolean[] goodies = new boolean[3];
		goodies[0] = true;

		Location next = loc.getAdjacentLocation(dir);


		if (!gr.isInBounds(next))
		{
			goodies[0] = false;

			Grid nextGrid = world.getAdjacentGrid(gr, dir);
			if (nextGrid != null) {
				gr = nextGrid;
				goodies[1] = true;


				loc = world.scrollLoc(next);
			}
			goodies[2] = false;
			return goodies;
		}



		Thing t = gr.get(next);

		if (t != null) {
			goodies[0] = false;
			
			goodies[0] = checkSkills(t);
			
			if (t instanceof Portal)
			{
				if (((Portal) t).isLadder()) {
					myDir += 180;
				}
				goodies[1] = true;
				goodies[2] = true;
								
				loc = ((Portal) t).getOutLocation();
				gr = ((Portal) t).getOutGrid();
			}
		}

		//updates Allenjin's location if moving (moving == goodies[0])
		if(goodies[0])
			loc = next;

		return goodies;
	}
	private boolean checkSkills(Thing t) {
		if (hasSkill("swim") && t.getName().equals("shoreline")) return true;
		if (hasSkill("treewalk") && t.getName().equals("tree")) return true;
		if (t.getName().equals("sekrit entrance")) return true;
		return false;
	}

	public Thing spicy()
	{
		Location next = loc.getAdjacentLocation(myDir);

		if(gr.isInBounds(next))
			return ((Thing) gr.get(next));
		else
			return null;
	}

	/* added by christine O: */

	public int getMaxHp(){
		return maxHp;
	}
	public int getHp(){
		return hp;
	}
	public void setMaxHp(int lol){
		maxHp = lol;
		hp = maxHp;
	}
	public void setExp(int lol) {
		exp = lol;
	}
	
	public void increaseHpBy(int inc){
		hp += inc;
	}
	public void decreaseHpBy(int dec){
		hp -= dec;
	}
	public boolean isDefeated(){
		return (hp <= 0);
	}
	public void recover(){
		hp = maxHp;
	}
	/* different from PokemonTrainer: */
	public void levelup(){
		exp++;
		maxHp = (int) Math.pow((exp*4),(7/5)); 	// increases maxHP according to exp. replace with better formula? xD
	}
	public int getExp(){
		return exp;
	}
	public int power() {
		int deviation = (int) (Math.random()*7) - 3;
		int power = maxHp/5 + deviation;

		if (power <= 0)
			return 0;
		else
			return power;
	}
}
