package tom.lib.netwolve.services.selection;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tom.lib.netwolve.interfaces.Selectionnable;
import tom.lib.netwolve.services.MathUtils;

public class SelectionnableSupplier implements Supplier<Selectionnable> {
	
	private List<Selectionnable> population;
	private List<Double> probas;
	
	public SelectionnableSupplier(List<Selectionnable> population, List<Double> probas) {
		this.population = population;
		this.probas = probas;
	}
	
	@Override
	/**
	 * Renvoie aléatoirement un éléments de la populations (selon sa probabilité)
	 */
	public Selectionnable get() {
		double r = MathUtils.RANDOM.nextDouble();
		int i;
		for (i = 0; i < probas.size() ; i++) {
			if (r <= probas.get(i)){
				break;
			}
		}
		
		i = Math.min(i, probas.size() - 1);
		return population.get(i).copy();
	}
	
	public Stream<Selectionnable> makeStream(){
		return Stream.generate(this);
	}
	
	public Stream<Selectionnable> makeStream(int numSelect){
		return Stream.generate(this).limit(numSelect);
	}
	
	public List<Selectionnable> makeList(int numSelect){
		return Stream.generate(this).limit(numSelect).collect(Collectors.toList());
	}
}