package tom.lib.genetic.enumerations;

import java.util.ArrayList;
import java.util.List;

import tom.lib.genetic.interfaces.Selectionnable;

public enum SelectionMethod {

	PROPORTIONATE_WHEEL {
		@Override
		public List<Double> getProbas(FitnessOrder fitnessOrder, List<Selectionnable> population, double fitnessSum) {
			List<Double> probas;
			int size = population.size();
			if (size > 1){
				population.sort((o1, o2) -> fitnessOrder.getOrder()*Double.compare(o1.getFitness(), o2.getFitness()));
				final double correction;
				if (FitnessOrder.DESC.equals(fitnessOrder)){
					correction =  (size - 1)*fitnessSum;
				}
				else{
					correction = fitnessSum;
				}
				probas = new ArrayList<>();
				probas.add(0.);
				population.stream().forEachOrdered(p -> probas.add(probas.get(probas.size()-1) + (fitnessSum - p.getFitness())/(correction)));
				probas.remove(0);
			}
			else{
				probas = new ArrayList<>();
				probas.add(1.);
			}
			return probas;
		}
	},
	
	RANGE_WHEEL {
		@Override
		public List<Double> getProbas(FitnessOrder fitnessOrder, List<Selectionnable> population, double fitnessSum) {
			List<Double> probas;
			int size = population.size();
			if (size > 1){
				probas = new ArrayList<>();
				population.sort((o1, o2) -> fitnessOrder.getOrder()*Double.compare(o1.getFitness(), o2.getFitness()));

				double sum = size*(size + 1)/2;
				for (int i = 1; i <= size ; i++) {
					probas.add(i*(i+1)/(2*sum));
				}
			}
			else{
				probas = new ArrayList<>();
				probas.add(1.);
			}
			return probas;
		}
	};
	
	public abstract List<Double> getProbas(FitnessOrder fitnessOrder,List<Selectionnable> population, double fitnessSum);
	
}
