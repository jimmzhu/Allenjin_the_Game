package Testing;

import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.awt.Point;

public class CerealFrame extends JFrame {
	
	private JinController control;
	private CerealPanel display;
	private JTextArea messageArea;
		
	public CerealFrame() {
		super("Pokémon: AllenJin version :D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//Create and set up the content pane.
		JComponent guiFrame = new CerealPanel(new JinController());
		guiFrame.setOpaque(true); //content panes must be opaque
		
		setContentPane(guiFrame);
		addKeyListener((KeyListener) guiFrame);
	
		//Display the window.
		pack();
		setVisible(true);

	}
	public static void main(String[] args) {
		CerealFrame application = new CerealFrame();
	}
}
