package tom.lib.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;


public class Statistique {

	private List<Double> elements;
	private double average;
	private double standardDeviation;
	private double min;
	private double max;
	private double sum;
	
	private boolean change;
	
	
	public void calculerStat(){
		if (change){
			DoubleSummaryStatistics statistics = elements.stream().parallel().mapToDouble(Double::doubleValue).summaryStatistics();
			min = statistics.getMin();
			max = statistics.getMax();
			sum = statistics.getSum();
			average = statistics.getAverage();
			standardDeviation = elements.stream().parallel().mapToDouble(Double::doubleValue).map(p -> (p - average) * (p - average)).sum();
			standardDeviation = Math.sqrt(standardDeviation/elements.size());
			change = false;
		}
	}
	
	public Statistique() {
		elements = new ArrayList<>();
		this.change = true;
	}

	public void add(Double d){
		elements.add(d);
		change = true;
	}
	
	public void add(double ... array){
		Arrays.stream(array).forEach(d -> add(d));
	}
	
	@Collectable(name="AVERAGE")
	public double getAverage() {
		calculerStat();
		return average;
	}
	
	@Collectable(name="SUM")
	public double getSum() {
		calculerStat();
		return sum;
	}
	
	@Collectable(name="SD")
	public double getStandardDeviation() {
		calculerStat();
		return standardDeviation;
	}
	
	@Collectable(name="MIN")
	public double getMin() {
		calculerStat();
		return min;
	}
	
	@Collectable(name="MAX")
	public double getMax() {
		calculerStat();
		return max;
	}
	
	public int size(){
		return elements.size();
	}
	
	public List<Double> getElements() {
		return new ArrayList<>(elements);
	}
	
	@Override
	public String toString() {
		return "[average=" + getAverage() + ", standardDeviation="
				+ getStandardDeviation() + ", max=" + getMax()  + ", min=" + getMin() + ", sum=" + getSum() +  ", elements="
				+ elements + "]";
	}

}
