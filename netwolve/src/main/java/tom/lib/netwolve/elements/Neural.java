package tom.lib.netwolve.elements;

import tom.lib.netwolve.MathUtils;
import tom.lib.netwolve.MathUtils.Fun;
import tom.lib.netwolve.interfaces.Transformation;

public class Neural extends Transformation {

	private float[] weights;
	private float bias = -0.5f;
	private Fun fun = Fun.SIGMOIDE;
	private float lambda = 1;
	
	public Neural(int length) {
		weights = new float[length];
		for (int i = 0; i < length; i++) {
			weights[i] = (float) MathUtils.RANDOM.nextGaussian();
		}
	}
	
	@Override
	public float[] eval(float[] inputs) {
		int n = Math.min(inputs.length, weights.length);
		float r = bias;
		for (int i = 0; i < n; i++) {
			r = r + weights[i]*inputs[i];
		}
		return new float[]{(float) fun.exec(lambda*r)};
	}

}
