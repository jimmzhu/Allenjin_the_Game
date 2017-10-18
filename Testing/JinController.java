package Testing;

import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.awt.Point;

public class JinController {
	
	private CerealFrame guiFrame;
	private CerealPanel guiDisplay;
	private boolean standing;
	
	
	public JinController() {
		guiDisplay = new CerealPanel(this);
		//allenWorld = new World();
	}
	public void updateAllen(int dir) {
		boolean moving = true;
		if (moving)
			guiDisplay.moveAllen(dir);
		else {
		}
	}
}
