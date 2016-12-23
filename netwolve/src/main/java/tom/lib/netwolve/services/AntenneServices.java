package tom.lib.netwolve.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tom.lib.netwolve.elements.Antenne;
import tom.lib.netwolve.utils.MathUtils;

public class AntenneServices {

	public void mute(Antenne antenne, double globalMutationRate){
		List<Boolean> cells = new ArrayList<>();
		if (MathUtils.hasard(0.5)){
			cells.add(MathUtils.RANDOM.nextBoolean());
		}
		
		if (antenne.size() > 0){
			double singleMutationRate = globalMutationRate/antenne.size();
			for (Iterator<Boolean> iterator = antenne.getCells().iterator(); iterator.hasNext();) {
				boolean b = (Boolean) iterator.next();
				if (MathUtils.hasard(singleMutationRate)){
					// On ajoute un nouveau, sinon ça supprime (ne rajoute pas)
					if (MathUtils.hasard(0.5)){
						cells.add(b);
						cells.add(MathUtils.RANDOM.nextBoolean());
					}
				}
				else{
					cells.add(b);
				}
			}
		}
		antenne.setCells(cells);
	}
	
}
