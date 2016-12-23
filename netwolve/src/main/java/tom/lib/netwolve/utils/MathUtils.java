package tom.lib.netwolve.utils;

import java.util.Random;

public class MathUtils {

	private MathUtils() {};
	
	public static final Random RANDOM = new Random();
	
	public synchronized static boolean hasard(double p){
		return RANDOM.nextDouble() <= p;
	}
	
}