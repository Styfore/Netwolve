package tom.lib.netwolve.elements;

import tom.lib.netwolve.MathUtils.Fun;
import tom.lib.netwolve.interfaces.Transformation;

public class Neural extends Transformation {

	private float[] weigths;
	private float bias = -0.5f;
	private Fun fun = Fun.SIGMOIDE;
	private float lambda = 1;
	
	@Override
	public float[] eval(float[] inputs) {
		int n = Math.min(inputs.length, weigths.length);
		float r = -bias;
		for (int i = 0; i < n; i++) {
			r = r + weigths[i]*inputs[i];
		}
		return new float[]{(float) fun.exec(lambda*r)};
	}

}
