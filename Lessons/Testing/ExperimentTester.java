
public class ExperimentTester {
	
	/**
	 * Method main
	 *
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		String m = "grid";
		String[] strArray = new String[10];
		
		for (int i = 0; i < strArray.length; i++) {
			strArray[i] = "grid" + (i+1);
		}
		for (String str : strArray) {
			System.out.println(str);
		}
	}	
}
