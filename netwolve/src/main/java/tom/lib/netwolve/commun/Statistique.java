package tom.lib.netwolve.commun;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;


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
		elements = Lists.newArrayList();
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
	
	public List<Double> getElements() {
		return Lists.newArrayList(elements);
	}
	
	@Override
	public String toString() {
		return "[average=" + getAverage() + ", standardDeviation="
				+ getStandardDeviation() + ", max=" + getMax()  + ", min=" + getMin() + ", sum=" + getSum() +  ", elements="
				+ elements + "]";
	}

	public static void main(String[] args) {
		Statistique s = new Statistique();
		s.add(12, 20, 5, 2, 1);
		System.out.println(s);
		List<Double> l = s.getElements();
		System.out.println(l);
		l.sort((o1, o2) -> Double.compare(o1, o2));
		l.parallelStream().forEach(d -> d = d/s.getSum());
		l = l.parallelStream().mapToDouble(d -> d/s.getSum()).boxed().collect(Collectors.toList());
		System.out.println(l);
		
		List<Double> p = Lists.newArrayList(0.);
		l.stream().forEachOrdered(d -> p.add(0, p.get(0) + d));
		System.out.println(p);
	}
}
