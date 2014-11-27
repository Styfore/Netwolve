package tom.lib.netwolve.elements;

import java.util.Arrays;

import tom.lib.netwolve.interfaces.Transformation;

public class Antenne extends Transformation {

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
	
	@Override
	public float[] eval(float[] inputs) {
		float[] capteurs = new float[lengths.length];
		Arrays.fill(capteurs, 0);
		
		for (int i = 0; i < inputs.length; i++) {
			
		}
		
		return capteurs;
	}
	
}
