package tom.lib.netwolve.services;

import java.util.List;
import java.util.stream.Collectors;

import tom.lib.netwolve.commun.StatCollector;
import tom.lib.netwolve.commun.Statistique;
import tom.lib.netwolve.interfaces.Selectionnable;

import com.google.common.collect.Lists;

public class SelecteurUtils {

	private SelecteurUtils() {}
	
	
	public static Statistique selection(List<Selectionnable> population, double conservation){
		Statistique stat = new Statistique();
		stat.add(population.stream().mapToDouble(p -> p.getFitness()).toArray());

		population.sort((o1, o2) -> Double.compare(o1.getFitness(), o2.getFitness()));
		
		List<Double> probas = Lists.newArrayList(0.);
		population.stream().forEachOrdered(p -> probas.add(0, probas.get(0) + p.getFitness()/stat.getSum()));
		probas.remove(probas.size() - 1);
		
		int nbKeeped = (int) (conservation*population.size());
		SelectionnableSupplier supplier = new SelectionnableSupplier(population, probas);
		
		List<Selectionnable> newPopulation = Lists.newArrayList();
		newPopulation.addAll(supplier.makeList(population.size() - nbKeeped));
		newPopulation.parallelStream().forEach(p -> p.mute());
		newPopulation.addAll(population.stream().skip(population.size() - nbKeeped).collect(Collectors.toList()));
		
		population.clear();
		population.addAll(newPopulation);
		
		return stat;
	}
	
	public static Statistique selection(List<Selectionnable> population, double conservation, StatCollector<Selectionnable> collecteur){
		collecteur.collect(population);
		return selection(population, conservation);
	}
}
