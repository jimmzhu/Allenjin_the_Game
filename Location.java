/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Alyce Brady
 * @author Chris Nevison
 * @author APCS Development Committee
 * @author Cay Horstmann
 */
import java.awt.Point;

public class Location implements Comparable
{
    private int row; // row location in grid
    private int col; // column location in grid

    public static final int LEFT = 270;
    public static final int RIGHT = 90;
    public static final int DOWN = 180;
    public static final int UP = 0;
    
    public static final int WEST = 270;
    public static final int EAST = 90;
    public static final int SOUTH = 180;
    public static final int NORTH = 0;
    
    public Location(int r, int c) {
        row = r;
        col = c;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public static int getDir(String m) {
    	
    	if (m.equals("left"))
    		return LEFT;
    	else if (m.equals("right"))
    		return RIGHT;
    	else if (m.equals("up"))
    		return UP;
    	else if (m.equals("down"))
    		return DOWN;
    	else
    		return DOWN;
    }
    public static String getString(int d) {
    	int dir = d % 360;
    	if (dir < 0)
    		dir += 360;
    	
    	if (dir == LEFT)
    		return "left";
    	else if (dir == RIGHT)
    		return "right";
    	else if (dir == UP)
    		return "up";
    	else if (dir == DOWN)
    		return "down";
    	else
    		return null;
    }
    public static Location adjustLocation(Location loc) {
    	int adjustedRow, adjustedCol;
    	adjustedRow = loc.getRow() % World.DEFAULT_GRID_H;
    	adjustedCol = loc.getCol() % World.DEFAULT_GRID_W;
    	
    	return new Location(adjustedRow, adjustedCol);
    }
    //Gets the adjacent location right, down, left, or up
    public Location getAdjacentLocation(int adjustedDirection)
    {
        // reduce mod 360 and round to closest multiple of 90
        /*int adjustedDirection = (direction + RIGHT / 2) % 360;
        if (adjustedDirection < 0)
            adjustedDirection += 360;

        adjustedDirection = (adjustedDirection / RIGHT) * RIGHT;*/
        int dc = 0;
        int dr = 0;
        if (adjustedDirection == RIGHT)
            dc = 1;
        else if (adjustedDirection == DOWN)
            dr = 1;
        else if (adjustedDirection == LEFT)
            dc = -1;
        else if (adjustedDirection == UP)
            dr = -1;
        
        return new Location(getRow() + dr, getCol() + dc);
    }
    public boolean equals(Object other) {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    public int hashCode() {
        return getRow() * 3737 + getCol();
    }
    //Locations are ordered in row-major order.
    public int compareTo(Object other) {
        Location otherLoc = (Location) other;
        if (getRow() == otherLoc.getRow())
            return getCol() - otherLoc.getCol();
        else
        	return getRow() - otherLoc.getRow();
    }
    public String toString() {
        return "(" + getRow() + ", " + getCol() + ")";
    }
}