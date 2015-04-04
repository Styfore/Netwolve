package tom.lib.netwolve;

import java.util.Random;

public class MathUtils {

	private MathUtils() {};
	
	public static final Random RANDOM = new Random();
	
	public static boolean hasard(double p){
		return RANDOM.nextDouble() <= p;
	}
	
}