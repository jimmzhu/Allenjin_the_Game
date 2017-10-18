
import java.util.Map;
import java.util.HashMap;

public class Sign extends TalkativeObstruction {
	private boolean hasLongMsg;
	private boolean textOn = false;
	
	//public static final Map<Location, String> SIGNMAP = getSignMap();
	public Sign(String name) {
		super(name);
	}
	public Sign(String name, boolean hasLongMessage) {
		super(name);
		hasLongMsg = hasLongMessage;
	}
	public String nextText() {
		if (hasLongMsg) {
			return super.nextText();
		}
		
		textOn = !textOn;
		if (textOn)
			return getName();
		else
			return null;
	}
	public boolean isLengthy() {
		return hasLongMsg;
	}
}
