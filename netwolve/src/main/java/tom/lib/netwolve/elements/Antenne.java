package tom.lib.netwolve.elements;

import java.util.Arrays;

public class Antenne  {

	private float x;
	private float y;
	private float orientation;
	
	private float[] lengths;
	
	public Antenne(float[] lengths) {
		this.lengths = lengths;
	}

	public Antenne(int length) {
		lengths = new float[length];
		Arrays.fill(lengths, 1f/length);
	}
	
	
	private void capte(double x, double y, double level){
		
	}

}
