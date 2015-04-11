package tom.lib.netwolve.services.selection;

import java.util.List;

import tom.lib.netwolve.interfaces.Selectionnable;

import com.google.common.collect.Lists;

public enum SelectionMethod {

	PROPORTIONATE_WHEEL {
		@Override
		public List<Double> getProbas(FitnessOrder fitnessOrder, List<Selectionnable> population, double fitnessSum) {
			List<Double> probas;
			int size = population.size();
			if (size > 1){
				population.sort((o1, o2) -> fitnessOrder.getOrder()*Double.compare(o1.getFitness(), o2.getFitness()));
				switch (fitnessOrder) {
				case ASC:
					probas = Lists.newArrayList(0.);
					population.stream().forEachOrdered(p -> probas.add(0, probas.get(0) + p.getFitness()/fitnessSum));
					probas.remove(probas.size() - 1);
					break;
				case DESC:
					double correction = (size - 1)*fitnessSum;
					probas = Lists.newArrayList(0.);
					population.stream().forEachOrdered(p -> probas.add(0, probas.get(0) + (fitnessSum - p.getFitness())/(correction)));
					probas.remove(probas.size() - 1);
					break;
				default:
					probas = Lists.newArrayList();
					break;
				}
			}
			else{
				probas = Lists.newArrayList(1.);
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
				probas = Lists.newArrayList();
				population.sort((o1, o2) -> fitnessOrder.getOrder()*Double.compare(o1.getFitness(), o2.getFitness()));
				double sum = size*(size + 1)/2;
				for (int i = size; i > 0 ; i--) {
					probas.add(i*(i+1)/(2*sum));
				}
			}
			else{
				probas = Lists.newArrayList(1.);
			}
			return probas;
		}
	};
	
	public abstract List<Double> getProbas(FitnessOrder fitnessOrder,List<Selectionnable> population, double fitnessSum);
	
}
