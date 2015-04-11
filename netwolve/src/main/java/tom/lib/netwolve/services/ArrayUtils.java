package tom.lib.netwolve.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayUtils {
	private ArrayUtils() {};
	
	public static List<Double> toList(double[] array){
		return Arrays.stream(array).boxed().collect(Collectors.toList());
	}
	
	public static List<Double> toList(Double[] array){
		return Arrays.stream(array).collect(Collectors.toList());
	}
	
	public static double[] toPrimitiveArray(List<Double> list){
		return list.stream().mapToDouble(Double::doubleValue).toArray();
	}
	
	public static Double[] toArray(List<Double> list){
		return list.stream().map(Double::new).toArray(Double[]::new);
	}
	
	public static Double[][] copy(Double[][] array){
		Double[][] copy = new Double[array.length][];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i].clone();
		}
		
		return copy;
	}
}
