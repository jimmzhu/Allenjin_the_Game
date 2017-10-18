import javax.swing.*;

public class TestSprites {
	
	/**
	 * Method main
	 *
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		ImageIcon icon = new ImageIcon("test_rightstanding.gif");
        System.out.println("height: " + icon.getIconHeight() + " width: " + icon.getIconWidth());
        
		ImageIcon fish = new ImageIcon("fish.gif");
        System.out.println("height: " + fish.getIconHeight() + " width: " + fish.getIconWidth());
	}	
}
