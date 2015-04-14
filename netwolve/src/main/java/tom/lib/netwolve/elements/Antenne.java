package tom.lib.netwolve.elements;

import java.util.Arrays;

public class Antenne  {

	private float x;
	private float y;
	private float orientation;
	
	private float[] lengths;
	
	public Antenne(float[] lengths) {
		this.lengths = lengths;
		// SOmmes pondérées
	}

	public Antenne(int length) {
		lengths = new float[length];
		Arrays.fill(lengths, 1f/length);
	}
	
	
	private void capte(double x, double y, double level){
		// On calcul l'angle d'arrivé
		double theta = Math.atan2(y - this.y, x - this.x) - orientation;
		double r = Math.sqrt((x - this.x)*(x - this.x) + (y - this.y)*(y - this.y));
		
		
	}

}
