import java.util.ArrayList;

public class TalkativeObstruction extends Obstruction {
	protected ArrayList<String> myText;
	private int txtIndex = 0;
	
	public TalkativeObstruction(String name) {
		super(name);
	}
	public String nextText() {
		if (myText == null)
			return super.nextText();
		
		String current = myText.get(txtIndex);
		txtIndex++;
		return current;
	}
	public boolean hasMoreText() {
		return txtIndex < myText.size();
	}
	public void setText(ArrayList<String> text) {
		myText = text;
	}
	public void resetText() {
		txtIndex = 0;
	}
}
