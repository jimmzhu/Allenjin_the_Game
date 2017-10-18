
public abstract class Experiment {
	
	private int x;
	
	public Experiment() {
		x = 0;
	}
	public Experiment(int y) {
		x = y;
	}
	public abstract void doExperiment();
}
