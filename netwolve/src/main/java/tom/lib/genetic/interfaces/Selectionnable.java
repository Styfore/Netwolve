package tom.lib.genetic.interfaces;

import java.util.List;

import tom.lib.statistics.Collectable;


public interface Selectionnable {

	@Collectable(name="FITNESS")
	public Double getFitness();
	
	public void mute();
	public Selectionnable copy();
	
	public List<? extends Selectionnable> cross(Selectionnable s);
}
