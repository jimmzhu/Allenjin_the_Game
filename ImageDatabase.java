
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.StringTokenizer;
import java.net.*;
import javax.swing.ImageIcon;

public class ImageDatabase {
	private Map<String,ImageIcon> imageMap = new HashMap<String,ImageIcon>();

	private static final String IMG_EXTENSION = ".gif";

	public void loadFile(String prefix, String fileName) {		
		ClassLoader cldr = this.getClass().getClassLoader();
		URL file = cldr.getResource(fileName);
		BufferedReader buffread = null;
		String imgName;
		try
		{
			StringTokenizer stringtok;
			String inputString;
			buffread = new BufferedReader(new InputStreamReader(file.openStream()));
			do
			{
				inputString = buffread.readLine();
				stringtok = new StringTokenizer(inputString);
				while(stringtok.hasMoreTokens())
				{
					imgName = prefix + stringtok.nextToken();
					addImage(imgName);
				}
			} while (inputString != null);
		}
		catch(Exception e){}
		return;
	}
	public void addImage(String imgName) {
		ClassLoader cldr = this.getClass().getClassLoader();

		if (!imageMap.containsKey(imgName)) {
			String fullName = imgName + ".gif";
			ImageIcon image = new ImageIcon(cldr.getResource(fullName));
			imageMap.put(imgName, image);
		}
	}
	public ImageIcon getImageIcon(String imgName) {
		if (imageMap.containsKey(imgName))
			return imageMap.get(imgName);
		else
			return null;
	}


}
