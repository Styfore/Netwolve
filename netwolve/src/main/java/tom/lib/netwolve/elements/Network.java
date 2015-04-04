package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tom.lib.netwolve.services.ArrayUtils;
import tom.lib.netwolve.services.Function;
import tom.lib.netwolve.services.MathUtils;

public class Network {

	private Function activationFunction;
	
	private int nbInput;
	private int nbOutput;

	private double[] biases;
	private double[] lambdas;

	private Double[][] inputWeights;
	private Double[][] weights;

	private double[] lastEval;
	
	private List<Set<Integer>> layerOrder = null;

	public Network(int nbNeurons, int nbInput, int nbOutput, double connexionRate, Function activationFunction) {
		assert(nbOutput <= nbNeurons);
		assert(activationFunction != null);

		this.activationFunction = activationFunction;
		this.biases = new double[nbNeurons];
		this.lambdas = new double[nbNeurons];

		this.weights = new Double[nbNeurons][nbNeurons];
		this.inputWeights = new Double[nbNeurons][nbInput];

		this.lastEval = new double[nbNeurons];
		
		this.nbOutput = nbOutput;
		this.nbInput = nbInput;
		
		for (int i = 0; i < nbNeurons; i++) {
			biases[i] = MathUtils.RANDOM.nextGaussian();
			lambdas[i] = MathUtils.RANDOM.nextGaussian();

			for (int j = 0; j < weights.length; j++) {
				if (MathUtils.hasard(connexionRate)){
					weights[i][j] = MathUtils.RANDOM.nextGaussian();
				}
			}
			
			for (int j = 0; j < inputWeights[i].length; j++) {
				if (MathUtils.hasard(connexionRate)){
					inputWeights[i][j] = MathUtils.RANDOM.nextGaussian();
				}
			}
		}
		
		calculateLayerOrder();
	}
	
	public Network(Network network){
		this.activationFunction = network.activationFunction;
		this.biases = network.biases.clone();
		this.lambdas = network.lambdas.clone();

		this.weights = ArrayUtils.copy(network.weights);
		this.inputWeights = ArrayUtils.copy(network.inputWeights);

		this.lastEval = network.lastEval.clone();
		
		this.nbOutput = network.nbOutput;
		this.nbInput = network.nbInput;
		
		this.layerOrder = new ArrayList<>();
		for (int i = 0; i < network.layerOrder.size(); i++) {
			Set<Integer> set = new HashSet<>();
			set.addAll(network.layerOrder.get(i));
			this.layerOrder.add(set);
		}
	}
	
	public void calculateLayerOrder(){
		int networkSize = weights.length;
		layerOrder = new ArrayList<>();
		Set<Integer> handledNeurons = new HashSet<>();
		
		Set<Integer> inputs = new HashSet<>();
		// Récupération des entrées, qui sont les premiers neurones à évaluer
		for (int i = 0; i < inputWeights.length; i++) {
			boolean isInput = false;
			int j = 0;
			while (!isInput && j < inputWeights[i].length) {
				isInput = inputWeights[i][j] != null;
				j++;
			}
			if (isInput){
				inputs.add(i);
			}
		}
		
		layerOrder.add(inputs);
		handledNeurons.addAll(inputs);
		while (handledNeurons.size() < networkSize && layerOrder.get(layerOrder.size()-1).size() > 0) {
			// On va chercher les sorties de la première couche
			Set<Integer> previousLayer = layerOrder.get(layerOrder.size()-1);
			
			Set<Integer> layer = new HashSet<>();
			for (int i = 0; i < networkSize; i++) {
				boolean isConnectedWithPrec = false;
				Iterator<Integer> iterator = previousLayer.iterator();
				while (!isConnectedWithPrec && iterator.hasNext()) {
					isConnectedWithPrec = weights[i][iterator.next()] != null;
				}
				if(isConnectedWithPrec){
					layer.add(i);
				}
			}
			layer.removeAll(handledNeurons);
			handledNeurons.addAll(layer);
			layerOrder.add(layer);
		}
		
		// On met tous les neurones non connectés qui ne reçoivent pas le flux dans la dernière couches
		if (layerOrder.get(layerOrder.size()-1).size() == 0){
			for (int i = 0; i < networkSize; i++) {
				if (!handledNeurons.contains(i)){
					layerOrder.get(layerOrder.size()-1).add(i);
				}
			}
		}
	}

	public double[] eval(double[] inputValue){
		int nbNeuron = biases.length;
		
		if (layerOrder == null){
			calculateLayerOrder();
		}
		
		double[] input = new double[inputWeights[0].length];
		System.arraycopy(inputValue, 0, input, 0, Math.min(inputValue.length, input.length));
		
		double[] currentEval = new double[lastEval.length];
		System.arraycopy(lastEval, 0, currentEval, 0, currentEval.length);
		
		for (Set<Integer> layer : layerOrder) {
			for (Integer neuron : layer) {
				double value = - biases[neuron];
				for (int i = 0; i < nbInput; i++) {
					value = value + multiply(input[i], inputWeights[neuron][i]);
				}
				for (int i = 0; i < nbNeuron; i++) {
					value = value + multiply(lastEval[i], weights[neuron][i]);
				}
				currentEval[neuron] = activationFunction.exec(value*lambdas[neuron]);
			}
			
			// On propage le signal dans la prochaine couche en mettant à jour lastEval
			for (Integer neuron : layer) {
				lastEval[neuron] = currentEval[neuron];
			}
		}		
		
		// On récupère les sorties du réseau (qui sont les nbOutput premier neurones dans l'ordre des arrays)
		double[] output = new double[nbOutput];
		System.arraycopy(lastEval, 0, output, 0, nbOutput);

		return output;
	}

	private double multiply(double x, Double weight){
		if (weight == null){
			return 0;
		}
		else{
			return x*weight;
		}
	}

	public int getNbOutput() {
		return nbOutput;
	}

	public int getNbInput() {
		return nbInput;
	}
	
	public double[] getBiases() {
		return biases;
	}

	public double[] getLambdas() {
		return lambdas;
	}

	public Double[][] getWeights() {
		return weights;
	}

	public Double[][] getInputWeights() {
		return inputWeights;
	}

	public double[] getLastEval() {
		return lastEval;
	}

	public List<Set<Integer>> getLayerOrder() {
		return layerOrder;
	}

	public void setBiases(double[] biases) {
		this.biases = biases;
	}

	public void setLambdas(double[] lambdas) {
		this.lambdas = lambdas;
	}

	public void setWeights(Double[][] weights) {
		this.weights = weights;
	}

	public void setInputWeights(Double[][] inputWeights) {
		this.inputWeights = inputWeights;
	}

	public void setLastEval(double[] lastEval) {
		this.lastEval = lastEval;
	}
	
}
