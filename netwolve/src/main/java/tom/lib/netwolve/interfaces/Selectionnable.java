package tom.lib.netwolve.interfaces;

import java.util.List;

public interface Selectionnable {

	public Double getFitness();
	public void mute();
	
	public <S extends Selectionnable> List<S> cross(S s);
}
