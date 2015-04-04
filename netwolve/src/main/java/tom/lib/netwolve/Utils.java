package tom.lib.netwolve;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	private Utils() {};
	
	public static List<Double> toList(double[] array){
		List<Double> list = new ArrayList<Double>();
		for (double d : array) {
			list.add(d);
		}
		return list;
	}
	
	public static List<Double> toList(Double[] array){
		List<Double> list = new ArrayList<Double>();
		for (Double d : array) {
			list.add(d);
		}
		return list;
	}
	
	public static double[] toPrimitiveArray(List<Double> list){
		double[] array = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public static Double[] toArray(List<Double> list){
		Double[] array = new Double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
