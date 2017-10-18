import java.util.ArrayList;
public class PokemonTrainer extends Organism {
	private int hp;
	private int maxHp;
	private String pokemon, attack;
	private boolean hasBeenDefeated = false;
	//private ArrayList<String> backupText2 = new ArrayList<String>(2);
	//private boolean battleMode;
	
	
	public PokemonTrainer(String name, Location loc, int dir, Grid gr) {
		super(name, loc, dir, gr);
	}
	public boolean isLeet4() {
		return (getName().equals("neuhaus") || getName().equals("hester") ||
			getName().equals("halander") || getName().equals("hubschmitt") || getName().equals("noahtye"));
	}
	public boolean hasBeenDefeated() {
		return hasBeenDefeated;
	}
	
	public String getPokemon(){
		return pokemon;
	}
	public void setPokemon(String poke) {
		pokemon = poke;
	}
	public String getAttack() {
		return attack;
	}
	public void setAttack(String attack) {
		this.attack = attack.replace('_',' ');
	}
	
	public int getMaxHp(){
		return maxHp;
	}
	public int getHp(){		// i have no idea when you'd use this method.
		return hp;
	}
	public void setHp(int lol){		// or this one. oh well 8D
		maxHp = lol;
		hp = maxHp;
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
	public void recover(){		// lol recover is an actual pokemon attack which isn't actually as effective as this method xD
		hp = maxHp;
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
