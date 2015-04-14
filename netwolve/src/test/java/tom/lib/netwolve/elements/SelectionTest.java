package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import tom.lib.genetic.SelecteurUtils;
import tom.lib.genetic.enumerations.FitnessOrder;
import tom.lib.genetic.enumerations.SelectionMethod;
import tom.lib.genetic.interfaces.Selectionnable;
import tom.lib.netwolve.elements.objets.SelectionnableTest;
import tom.lib.netwolve.services.NetworkUtils;
import tom.lib.statistics.StatCollector;
import tom.lib.statistics.Statistique;

public class SelectionTest {

	@Test
	public void selectionTestSimple(){
		List<Selectionnable> pop = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			pop.add(new SelectionnableTest());
		}
		Statistique nbNeurone = new Statistique();
		Predicate<Statistique> arret  = s ->  {
				StatCollector<Selectionnable> collecteur = new StatCollector<>(SelectionnableTest.class);
				collecteur.collect(pop);
				nbNeurone.add(collecteur.get("NB_NEURONE").getAverage());
				return s.getMax() > 0.001;
		};
		
		StatCollector<Statistique> stat = SelecteurUtils.selectionOverGeneration(pop, 0.2, SelectionMethod.RANGE_WHEEL, FitnessOrder.DESC, arret);
		System.out.println(String.format("En %s tours pour un fitnesse min de %s et un nombre de neurone moyen de %s", stat.size(), stat.get("MIN").getMin(), nbNeurone.getAverage()));
		System.out.println(stat);
	}
	
	@Test
	public void selectionTest(){
		List<Selectionnable> pop = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			pop.add(new SelectionnableTest());
		}

		pop.parallelStream().map(s -> (SelectionnableTest) s).forEach(p -> p.eval());
		int i = 0;

		Statistique nbNeurone = new Statistique();

		StatCollector<Selectionnable> collecteur;
		StatCollector<Statistique> collecteurStat = new StatCollector<>(Statistique.class);
		Statistique stat = SelecteurUtils.selection(pop, 0.2, SelectionMethod.RANGE_WHEEL, FitnessOrder.DESC);
		System.out.println("max = " + stat.getMax());
		while (stat.getMin() > 0.0001) {
			collecteur = new StatCollector<>(SelectionnableTest.class);
			System.out.println(stat = SelecteurUtils.selection(pop, 0.2, SelectionMethod.RANGE_WHEEL, FitnessOrder.DESC, collecteur));
			System.out.println(collecteur.get("NB_NEURONE")+"\n");

			nbNeurone.add(collecteur.get("NB_NEURONE").getAverage());
			collecteurStat.collect(stat);
			i++;
		}

		pop.parallelStream().map(s -> (SelectionnableTest) s).forEach(p -> p.eval());
		pop.sort((o1, o2) -> Double.compare(o1.getFitness(), o2.getFitness()));

		SelectionnableTest best = (SelectionnableTest) pop.get(0);
		System.out.println("Il a fallu " + i + " génération pour avoir un fitnesse min  < 0.0001");
		System.out.println("Fitnesse = " + best.getFitness());
		System.out.println("STAT AVERAGE NB NEURONE = " + nbNeurone+"\n");
		System.out.println("STAT FITNESS = " + collecteurStat+"\n");

		for (int j = 0; j < 4; j++) {
			System.out.println(Arrays.toString(SelectionnableTest.inputs[j]) + " => " + best.network.eval(SelectionnableTest.inputs[j])[0]);
		}
		
		NetworkUtils.exportToCsv("target/test", best.network);
	}
	
	@Test
	public void moyenne(){
		List<Selectionnable> popProp = new ArrayList<>();
		List<Selectionnable> popRange = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			popProp.add(new SelectionnableTest());
			popRange.add(new SelectionnableTest());
		}

		Statistique statProp = new Statistique();
		Statistique statRange = new Statistique();
		
		Statistique statPropI = new Statistique();
		Statistique statRangeI = new Statistique();

		int propI = 0;
		int rangeI = 0;
		for (int j = 0; j < 100; j++) {
			while (statProp.getMin() > 0.0001 || statRange.getMin() > 0.0001) {
				if (statProp.getMin() > 0.0001){
					statProp = SelecteurUtils.selection(popProp, 0.2, SelectionMethod.PROPORTIONATE_WHEEL, FitnessOrder.DESC);
					propI++;
				}
				if (statRange.getMin() > 0.0001){
					statRange = SelecteurUtils.selection(popRange, 0.2, SelectionMethod.RANGE_WHEEL, FitnessOrder.DESC);
					rangeI++;
				}
			}
			statPropI.add(propI);
			statRangeI.add(rangeI);
			
			propI = 0;
			rangeI = 0;
			statProp = new Statistique();
			statRange = new Statistique();
			
			popProp.clear();
			popRange.clear();
			for (int i = 0; i < 20; i++) {
				popProp.add(new SelectionnableTest());
				popRange.add(new SelectionnableTest());
			}
		}
		
		System.out.println("PROP METHOD = " + statPropI);
		System.out.println("RANGE METHOD = " + statRangeI);
	}

}