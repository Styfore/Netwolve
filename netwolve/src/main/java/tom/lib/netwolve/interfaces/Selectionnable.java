package tom.lib.netwolve.interfaces;

import java.util.List;

import tom.lib.netwolve.commun.Collectable;


public interface Selectionnable {

	@Collectable(name="FITNESS")
	public Double getFitness();
	
	public void mute();
	public Selectionnable copy();
	
	public List<? extends Selectionnable> cross(Selectionnable s);
}
