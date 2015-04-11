package tom.lib.netwolve.elements;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tom.lib.netwolve.commun.StatCollector;
import tom.lib.netwolve.commun.Statistique;
import tom.lib.netwolve.elements.objets.SelectionnableTest;
import tom.lib.netwolve.interfaces.Selectionnable;
import tom.lib.netwolve.services.NetworkUtils;
import tom.lib.netwolve.services.selection.FitnessOrder;
import tom.lib.netwolve.services.selection.SelecteurUtils;
import tom.lib.netwolve.services.selection.SelectionMethod;

import com.google.common.collect.Lists;

public class SelectionTest {

	@Test
	public void selectionTest(){
		List<Selectionnable> pop = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			pop.add(new SelectionnableTest());
		}

		pop.parallelStream().map(s -> (SelectionnableTest) s).forEach(p -> p.eval());
		int i = 0;

		Statistique nbNeurone = new Statistique();

		StatCollector<Selectionnable> collecteur;
		StatCollector<Statistique> collecteurStat = new StatCollector<>(Statistique.class);
		Statistique stat = SelecteurUtils.selection(pop, 0.2, SelectionMethod.PROPORTIONATE_WHEEL, FitnessOrder.DESC);
		System.out.println("max = " + stat.getMax());
		while (stat.getMin() > 0.0001) {
			collecteur = new StatCollector<>(SelectionnableTest.class);
			pop.parallelStream().map(s -> (SelectionnableTest) s).forEach(p -> p.eval());

			System.out.println(stat = SelecteurUtils.selection(pop, 0.2, SelectionMethod.PROPORTIONATE_WHEEL, FitnessOrder.DESC, collecteur));
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

}