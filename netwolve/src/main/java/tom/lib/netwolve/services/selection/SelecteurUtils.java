package tom.lib.netwolve.services.selection;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tom.lib.netwolve.commun.StatCollector;
import tom.lib.netwolve.commun.Statistique;
import tom.lib.netwolve.interfaces.Selectionnable;

import com.google.common.collect.Lists;

public class SelecteurUtils {

	private SelecteurUtils() {}
	
	public static Statistique selection(List<Selectionnable> population, double crossoverRate, double conservationRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder){
		Statistique stat = new Statistique();
		stat.add(population.stream().mapToDouble(p -> p.getFitness()).toArray());

		// On calcule la proba pour chaque élement d'être sélectionné
		List<Double> probas = selectionMethod.getProbas(fitnessOrder, population, stat.getSum());
		SelectionnableSupplier supplier = new SelectionnableSupplier(population, probas);
		
		int nbKeeped = (int) (conservationRate*population.size());
		int nbCrossover = (int) (crossoverRate*(population.size() - nbKeeped));
		
		// clones
		List<Selectionnable> newPopulation = Lists.newArrayList();
		newPopulation.addAll(supplier.makeList(population.size() - nbKeeped - nbCrossover));
		
		// crossovers
		List<Selectionnable> crossoverPopulation = Lists.newArrayList();
		while (crossoverPopulation.size() < nbCrossover){
			crossoverPopulation.addAll(supplier.get().cross(supplier.get()));
		}
		newPopulation.addAll(crossoverPopulation.stream().limit(nbCrossover).collect(Collectors.toList()));
		
		// mutations
		newPopulation.parallelStream().forEach(p -> p.mute());
		
		// On garde les nbKeeped meilleurs
		newPopulation.addAll(population.stream().skip(population.size() - nbKeeped).collect(Collectors.toList()));
		
		population.clear();
		population.addAll(newPopulation);
		return stat;
	}
	
	
	public static Statistique selection(List<Selectionnable> population, double conservationRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder){
		return selection(population, 0., conservationRate, selectionMethod, fitnessOrder);
	}
	
	
	public static Statistique selection(List<Selectionnable> population, double conservationRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder, StatCollector<Selectionnable> collecteur){
		collecteur.collect(population);
		return selection(population, conservationRate, selectionMethod, fitnessOrder);
	}
	
	public static Statistique selection(List<Selectionnable> population, double crossoverRate, double conservationRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder, StatCollector<Selectionnable> collecteur){
		collecteur.collect(population);
		return selection(population, crossoverRate, conservationRate, selectionMethod, fitnessOrder);
	}
	
	public static StatCollector<Statistique> selection(List<Selectionnable> population, double crossoverRate, double conservationRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder, Predicate<Statistique> stopCondition){
		Statistique stat = new Statistique();
		StatCollector<Statistique> metastat = new StatCollector<>(Statistique.class);
		while (stopCondition.test(stat)) {
			stat = selection(population, crossoverRate, conservationRate, selectionMethod, fitnessOrder);
			metastat.collect(stat);
		}
		return metastat;
	}
	
	public static StatCollector<Statistique> selection(List<Selectionnable> population, double crossoverRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder, Predicate<Statistique> stopCondition){
		Statistique stat = new Statistique();
		StatCollector<Statistique> metastat = new StatCollector<>(Statistique.class);
		while (stopCondition.test(stat) || stat.getElements().size() == 0) {
			stat = selection(population, crossoverRate, selectionMethod, fitnessOrder);
			metastat.collect(stat);
		}
		return metastat;
	}
	
	public static StatCollector<Statistique> selection(List<Selectionnable> population, double crossoverRate, SelectionMethod selectionMethod, FitnessOrder fitnessOrder, Predicate<Statistique> stopCondition, int maxGeneration){
		Statistique stat = new Statistique();
		StatCollector<Statistique> metastat = new StatCollector<>(Statistique.class);
		while (stopCondition.test(stat) && metastat.size() < maxGeneration || stat.getElements().size() == 0) {
			stat = selection(population, crossoverRate, selectionMethod, fitnessOrder);
			metastat.collect(stat);
		}
		return metastat;
	}
}
