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
 * @author Julie Zelenski
 * @author Cay Horstmann
 */

import java.awt.Component;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * An ImageDisplay displays an object as a tinted image from an image file whose
 * name matches the class name. <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */

public class ImageDisplay
{
    private static final String imageExtension = ".gif";

    /**
     * Constructs an object that knows how to display an image. Looks for the
     * named file first in the jar file, then in the current directory.
     * @param imageFilename name of file containing image
     */
    public static ImageIcon createImageIcon(Class c1) throws IOException
    {
        String imageFilename = c1.getName().replace('.', '/');
        URL url = c1.getClassLoader().getResource(imageFilename + imageExtension);

        if (url == null)
            throw new FileNotFoundException(imageFilename + imageExtension + " not found.");
        Image img = ImageIO.read(url);
        
        return new ImageIcon(img);
    }
}