package tom.lib.netwolve.elements.objets;

import java.util.Arrays;
import java.util.List;

import tom.lib.genetic.interfaces.Selectionnable;
import tom.lib.netwolve.commun.Function;
import tom.lib.netwolve.elements.Network;
import tom.lib.netwolve.services.MathUtils;
import tom.lib.netwolve.services.NetworkUtils;
import tom.lib.statistics.Collectable;

public class SelectionnableTest implements Selectionnable{

	public Network network;
	double score;
	public static double[][] inputs = new double[][]{new double[]{0., 0.}, new double[]{1., 0.}, new double[]{0., 1.}, new double[]{1., 1.}};
	public static double[] output = new double[]{0., 1., 1., 0.};
	boolean isEval = false;
	
	public SelectionnableTest() {
		network = new Network(5, 2, 1, MathUtils.RANDOM.nextDouble(), Function.SIGMOIDE);
	}

	public SelectionnableTest(Network network) {
		this.network = new Network(network);
	}


	public void eval(){
		score = 0.;
		for (int i = 0; i < inputs.length; i++) {
			score = score + Math.pow(output[i] - network.eval(inputs[i])[0], 2);
		}
		isEval = true;
	}

	@Collectable(name="NB_NEURONE")
	public double getNombreNeurone(){
		return network.getLambdas().length;
	}

	@Override
	public Double getFitness() {
		if (!isEval){
			eval();
		}
		return score;
	}

	@Override
	public void mute() {
		NetworkUtils.mute(network, 1, 0.1, 0.1, 0.1, 0.1, 0.1);
		isEval = false;
	}

	@Override
	public Selectionnable copy() {
		isEval = false;
		return new SelectionnableTest(network);
	}

	@Override
	public List<? extends Selectionnable> cross(Selectionnable s) {
		isEval = false;
		return Arrays.asList(new SelectionnableTest[]{this, this, this});
	}
}