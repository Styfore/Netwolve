package tom.lib.netwolve.services;


public abstract class Function{
	
	public final static Function SIGMOIDE = new Function() {
		
		@Override
		public double exec(double x) {
			return 1/(1 + Math.exp(-x));
		}
	}; 
	
	public abstract double exec(double x);
}
