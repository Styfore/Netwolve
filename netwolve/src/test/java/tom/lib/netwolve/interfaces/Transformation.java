package tom.lib.netwolve.interfaces;

public abstract class Transformation {

	protected float[] lastEval;
	public abstract float[] eval(float[] inputs);
	
	public void setLastEval(float[] lastEval) {
		this.lastEval = lastEval;
	}
	
	public float[] getLastEval() {
		return lastEval;
	}
}