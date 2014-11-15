package tom.lib.netwolve;

import java.util.Random;

public class MathUtils {

	public static final Random RANDOM = new Random();
	
	public enum Fun {
		
		SIGMOIDE {
			@Override
			public double exec(double x) {
				return 1/(1 + Math.exp(-x));
			}
		};
		
		public abstract double exec(double x);
		
	}
	
}
