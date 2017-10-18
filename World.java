import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.Iterator;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class World {
	
	private static final int WORLD_HEIGHT = 6;
	private static final int WORLD_WIDTH = 6;
	public static final int DEFAULT_GRID_H = 17;
	public static final int DEFAULT_GRID_W = 17;
	private final String FILE_PREFIX = "outside/grid_";
	
	protected int gridHeight;
	protected int gridWidth;
	protected int locationOffset = 0;
	
	//private static final int HOME_ROW = 1;
	//private static final int HOME_COL = 0;
	
	//private JFrame frame;
	private ImageDatabase images;
	private CerealPanel display;
	
	private Grid[][] cerealWorld;
	private HashMap<String,Grid> gMap = new HashMap<String,Grid>();
	private LinkedList<Grid> liveGrids = new LinkedList<Grid>();
	
	public World(CerealPanel cp) {
		this(cp, WORLD_HEIGHT, WORLD_WIDTH);
	}
	public World(CerealPanel cp, int height, int width) {
		this(cp, height, width, DEFAULT_GRID_H, DEFAULT_GRID_W);
	}
	
	public World(CerealPanel cp, int height, int width, int gHeight, int gWidth) {
		gridHeight = gHeight;
		gridWidth = gWidth;
		display = cp;
		
		cerealWorld = new Grid[height][width];
		images = new ImageDatabase();
		System.out.println();
		System.out.println("outside world:    height = " + height + "    width = " + width);
		
		
		//loads all outside grids stored in world and their respective background images
		int r,c;
		String fileName;
		Grid gr;
		for (r = 0; r < height; r++) {
			for (c = 0; c < width; c++) {
				fileName = FILE_PREFIX + r + '.' + c;
				gr = loadGrid("Grid", fileName);
				gr.setLocInWorld(new Location(r,c));
				cerealWorld[r][c] = gr;
				images.addImage(fileName);
			}
		}
		images.loadFile("misc/", "misc/miscImgs.txt");
		loadInsideGrids("inside/insideGrids.txt");	// loads inside grids, accessible only through portals
		loadPortals("portals.txt");					// loads portals
		loadOrganisms("organisms/organisms.txt");	// loads people & pokemon trainers
		loadSpices("spices.txt");					// loads special properties, aka "spices"
		
		System.out.println("finished loop");
	}
	public Grid[][] getArray() {
		return cerealWorld;
	}
	public ImageDatabase getImages() {
		return images;
	}
	public LinkedList<Grid> getLiveGrids() {
		return liveGrids;
	}
	public Grid getGrid(int row, int col) {
		return cerealWorld[row][col];
	}
	public Grid getGrid(String name) {
		return gMap.get(name);
	}
	
	public Grid getAdjacentGrid(Grid gr, int d) {
    	Location loc = gr.getLocInWorld();
    	if (loc == null) 
    		return null;
    	int row = loc.getRow();
    	int col = loc.getCol();
    	
    	int dir = d % 360;
    	if (dir < 0)
    		dir += 360;
    	
    	
		switch (dir) {
			case Location.LEFT:
				col--; break;
			case Location.RIGHT:
				col++; break;
			case Location.UP:
				row--; break;
			case Location.DOWN:
				row++; break;
		}
		boolean inBounds = (0 <= row && row< WORLD_HEIGHT
                && 0 <= col && col < WORLD_WIDTH);
		if (!inBounds)
			return null;
		
		return cerealWorld[row][col];
	}
	
	/*
	 * loads information for each Grid contained in World
	 * along with corresponding images of each Grid and 
	 * each Organism (Things that can move)
	 *
	 */
	private Grid loadGrid(String cName, String fName) {
		String fileName = fName + ".txt";
		Grid gr = new Grid(fName, DEFAULT_GRID_H, DEFAULT_GRID_W);
		gr.setName(fName);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		
		try {
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			StringTokenizer stringtok;
			String inputString;
			String className;
			Obstruction ob;
			
			Location loc;
			do {
				inputString = buffread.readLine();
				stringtok = new StringTokenizer(inputString);
				
				while (stringtok.hasMoreTokens()) {
					loc = readLoc(stringtok);
					className = stringtok.nextToken();
					ob = readObstruction(stringtok, className);
					ob.putSelfInGrid(gr, loc);
				}
			} while (inputString != null);
			
		}
		catch(Exception e) {}
		
		return gr;
		
	}
	private void loadInsideGrids(String fileName) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		String imgName;
		
		try {
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			String gridFileName;
			Grid gr;
			do {
				gridFileName = buffread.readLine();
				gr = loadGrid("Grid","inside/" + gridFileName);
				gMap.put(gridFileName,gr);
				images.addImage(gr.getName());
			} while (gridFileName != null);
		}
		catch(Exception e) {}
	}
	private void loadPortals(String fileName) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		
		try {
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			StringTokenizer stringtok;
			String inputString;
			Grid home;
			Location loc;
			Portal po;
			String className;
			
			do {
				inputString = buffread.readLine();
				stringtok = new StringTokenizer(inputString);
				
				while (stringtok.hasMoreTokens()) {
					home = readGrid(stringtok);
					loc = readLoc(stringtok);
					className = stringtok.nextToken();
					po = readPortal(stringtok, className);
					po.putSelfInGrid(home, loc);
				}
			} while (inputString != null);
		}
		catch(Exception e) {}
	}
	private void loadSpices(String fileName) {
/**/	System.out.println("Ohai, I is loading up some spices");

		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		
		try {
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			StringTokenizer stringtok;
			String inputString;
			
			Grid gr;
			Location loc;
			String trigger, action;
			Spice spice;
			Object[] parameters;
			Thing t;
			
			do {
				inputString = buffread.readLine();
				stringtok = new StringTokenizer(inputString);
				
				while (stringtok.hasMoreTokens()) {
					gr = readGrid(stringtok);
					loc = readLoc(stringtok);
					trigger = stringtok.nextToken();
					action = stringtok.nextToken();
					
					spice = new Spice(trigger, action);
					if (action.equals("learn")) {
						parameters = new Object[1];
						parameters[0] = stringtok.nextToken(); //parameters[0] = skill
					}
					else if (action.equals("move")) {
						parameters = new Object[4];
						parameters[0] = readGrid(stringtok);	//parameters[0] = startGrid
						parameters[1] = readLoc(stringtok);		//parameters[1] = startLoc
						parameters[2] = readGrid(stringtok);	//parameters[2] = endLoc
						parameters[3] = readLoc(stringtok);		//parameters[3] = endGrid
					}
					else if (action.equals("transform")) {
						parameters = new Object[3];
						
						parameters[0] = readGrid(stringtok);	//parameters[0] = targetGrid
						parameters[1] = readLoc(stringtok);		//parameters[1] = targetLocation
						
						//parameters[2] = targetProduct
						String product = stringtok.nextToken();
						if (product.equals("nothing")) {
							parameters[2] = null;
						}
						else if (product.equals("Portal") || product.equals("Ladder")) {
							parameters[2] = readPortal(stringtok, product);
						}
						else if (product.equals("Organism") || product.equals("PokemonTrainer")) {
							Organism oo = readOrganism(stringtok, product, gr, loc);
							if (oo instanceof PokemonTrainer)
								readPokeStats(stringtok, oo);
							parameters[2] = oo;
						}
						else {
							parameters[2] = readObstruction(stringtok, product);
						}
					}
					else {
						parameters = new Object[0];
					}
					
					spice.setParameters(parameters);
					
					t = gr.get(loc);
					if (t != null)
						t.addSpice(spice);
					
					
					
				}
			}
			while (inputString != null);
		}
		catch(Exception e) {}
	}
	/*
	 * loadOrganisms should load in a text file containing every organism that
	 * needs to be included in the World. Information for Organisms will be read
	 * in the following order: className, name, direction, grid, location.
	 * 
	 * Organism text will be loaded from separate text files that match the name 
	 * of the Organism. This will be done via the loadOrganism method.
	 *
	 **/
	
	private void loadOrganisms(String fileName) {
/**/	System.out.println("Ohai, I is loading up some organisms");
		
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		
		try {
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			StringTokenizer stringtok;
			String inputString;
			
			Grid gr;
			Location loc;
			String className;
			Organism organism;
			
			
			do {
				inputString = buffread.readLine();
				stringtok = new StringTokenizer(inputString);
				
				while (stringtok.hasMoreTokens()) {
					
					gr = readGrid(stringtok);
					loc = readLoc(stringtok);
					className = stringtok.nextToken();
					organism = readOrganism(stringtok, className, gr, loc);
					
					if (organism instanceof PokemonTrainer) {
						readPokeStats(stringtok, organism);
						liveGrids.add(gr);
					}
					
					for (int i = Location.UP; i <= Location.LEFT; i+=90)
						images.addImage("organisms/" + organism.getName() + "_" + Location.getString(i));
					
/**/				System.out.println(organism.getName() + " exists?  " + (organism != null));
				}
			}
			while (inputString != null);
		}
		catch(Exception e) {}
		 
	}
	private Organism loadOrganism(Grid gr, Location loc, String className, String name, int dir) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource("organisms/" + name + ".txt");
		BufferedReader buffread = null;
		
		Organism organism;
		if (className.equals("PokemonTrainer")) {
			organism = new PokemonTrainer(name, loc, dir, gr);
		} else if (className.equals("Fatty")) {
			organism = new Fatty(name, loc, dir, gr);
		} else if (className.equals("FattyMover")) {
			organism = new FattyMover(name, loc, dir, gr);
		} else {
			organism = new Organism(name, loc, dir, gr);
		}
		
		ArrayList<String> oText = new ArrayList<String>();
		ArrayList<String> oTextBackup = new ArrayList<String>();
		
		
		try{
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			String inputString;
			do
			{
				inputString = buffread.readLine();
				if (!inputString.equals("")) 
				{
					oText.add(inputString);
				} 
				else 
				{
					organism.setText(oText);
					oText = new ArrayList<String>();
				}
			} while (inputString != null);		
		}
		catch(Exception e){}
		organism.setSecondaryText(oText);
		
		
		return organism;
	}
	private ArrayList<String> loadText(String name) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource("organisms/" + name + ".txt");
		BufferedReader buffread = null;
		ArrayList<String> textArray = new ArrayList<String>();
		if (file == null)
			return null;
		try{
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			String inputString;
			do
			{
				inputString = buffread.readLine();
				if (!inputString.equals(""))
					textArray.add(inputString);
			} while (inputString != null);
			// code that happens after reading ?close file?		
		}
		catch(Exception e){}

		return textArray;
	}
	private ArrayList<String> loadText2(String name) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource("organisms/" + name + "2.txt");
		BufferedReader buffread = null;
		ArrayList<String> textArray = new ArrayList<String>();
		if (file == null)
			return null;
		try{
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			String inputString;
			do
			{
				inputString = buffread.readLine();
				if (!inputString.equals(""))
					textArray.add(inputString);
			} while (inputString != null);		
		}
		catch(Exception e){}

		return textArray;
	}
	public Location scrollLoc(Location loc) {
		int r = loc.getRow();
		int c = loc.getCol();
		
		if(r < 0)
			r += 17;
		else
			r = r % 17;
		
		if(c < 0)
			c += 17;
		else
			c = c % 17;
			
		System.out.print("current loc: " + loc);
		System.out.println("   desired loc: " + new Location(r,c));
		return new Location(r,c);
	}
	private Grid readGrid(StringTokenizer stringtok) {
		String side = stringtok.nextToken();
		if (side.equals("outside")) {
			int gRow = new Integer(stringtok.nextToken());
			int gCol = new Integer(stringtok.nextToken());
			return cerealWorld[gRow][gCol];
		}
		else {
			return gMap.get(stringtok.nextToken());
		}
	}
	private Location readLoc(StringTokenizer stringtok) {
		int row = new Integer(stringtok.nextToken());
		int col = new Integer(stringtok.nextToken());
		return new Location(row, col);
	}
	private Obstruction readObstruction(StringTokenizer stringtok, String className) {
		Obstruction ob;
		boolean viz = false;
		int xOffset = 0, yOffset = 0;
		String name = stringtok.nextToken();
		if (name.equals("viz")) {
			viz = true;
			name = stringtok.nextToken();
			
			if (name.equals("xOffset")) {
				xOffset = new Integer(stringtok.nextToken());
				name = stringtok.nextToken();
			} if (name.equals("yOffset")) {
				yOffset = new Integer(stringtok.nextToken());
				name = stringtok.nextToken();
			}
		}
		
		//The Sign class no longer exists; this is just for Obstructions that don't say "Ohai, I am a..."
		if (className.equals("Sign")) {
			ob = new Obstruction("sign", name);
		}
		else if (className.equals("Rambler")) {
			ob = new TalkativeObstruction(name);
			((TalkativeObstruction) ob).setText(loadText(name));
		}
		else {
			ob = new Obstruction(name);
		}
		
		if (viz) {
			ob.setVisible();
			ob.setXOffset(xOffset);
			ob.setYOffset(yOffset);
			images.addImage("misc/" + ob.getName());
		}
		
		return ob;
	}
	private Portal readPortal(StringTokenizer stringtok, String className) {
		Grid destiny = readGrid(stringtok);
		Location outLoc = readLoc(stringtok);
		boolean viz = false;
		String name = stringtok.nextToken();
		if (name.equals("viz")) {
			viz = true;
			name = stringtok.nextToken();
		}
		
		Portal portal = new Portal(name, destiny, outLoc);
		if (className.contains("Ladder"))
			portal.setLadder();
		
		if (viz) {
			portal.setVisible();
			images.addImage("misc/" + name);
		}
		return portal;
	}
	private Organism readOrganism(StringTokenizer stringtok, String className, Grid gr, Location loc) {
		String name = stringtok.nextToken();
		int myDir = Location.getDir(stringtok.nextToken());
		return loadOrganism(gr, loc, className, name, myDir);
	}
	private void readPokeStats(StringTokenizer stringtok, Organism oo) {
		((PokemonTrainer) oo).setPokemon(stringtok.nextToken());
		((PokemonTrainer) oo).setAttack(stringtok.nextToken());
		((PokemonTrainer) oo).setHp(new Integer(stringtok.nextToken()));
	}
}
