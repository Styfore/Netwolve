package tom.lib.netwolve.elements;

import tom.lib.netwolve.MathUtils;
import tom.lib.netwolve.MathUtils.Fun;
import tom.lib.netwolve.interfaces.Provider;

public class Neuron implements Provider {
	private float[] weights;
	private float bias = -0.5f;
	private Fun fun = Fun.SIGMOIDE;
	private float lambda = 1;
	private float lastEval = 0f;
	
	public Neuron(int length) {
		weights = new float[length];
		for (int i = 0; i < length; i++) {
			weights[i] = (float) MathUtils.RANDOM.nextGaussian();
		}
	}
	
	public void eval(float[] inputs) {
		int n = Math.min(inputs.length, weights.length);
		float r = bias;
		for (int i = 0; i < n; i++) {
			r = r + weights[i]*inputs[i];
		}
		lastEval = (float) fun.exec(lambda*r);
	}

	@Override
	public float provide() {
		return lastEval;
	}

	public float[] getWeights() {
		return weights;
	}

	public void setWeights(float[] weights) {
		this.weights = weights;
	}

	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}

	public float getLambda() {
		return lambda;
	}

	public void setLambda(float lambda) {
		this.lambda = lambda;
	}

}
