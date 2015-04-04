package tom.lib.netwolve.services;

import java.util.ArrayList;
import java.util.List;

import tom.lib.netwolve.MathUtils;
import tom.lib.netwolve.Utils;
import tom.lib.netwolve.elements.Network;

public class NetworkFactory {

	public static void mute(Network network, double gaussianSd, double weightMutationRate, double newLinkRate, double deleteLinkRate, double newNeuronRate, double deleteNeuronRate){
		List<Double> lastEval = Utils.toList(network.getLastEval());
		
		List<Double> biases = Utils.toList(network.getBiases());
		List<Double> lambdas = Utils.toList(network.getLambdas());
		
		List<List<Double>> inputWeights = new ArrayList<>();
		for (Double[] array : network.getInputWeights()) {
			inputWeights.add(Utils.toList(array));
		}
		List<List<Double>> weights = new ArrayList<>();
		for (Double[] array : network.getWeights()) {
			weights.add(Utils.toList(array));
		}
		
		// Suppression des neurones
		List<Integer> deleted = new ArrayList<>();
		// Les output du réseau ne peuvent pas être supprimée
		for (int i = network.getNbOutput(); i < network.getBiases().length; i++) {
			if (MathUtils.hasard(deleteNeuronRate)){
				deleted.add(i);
			}
		}

		for (int i = deleted.size() - 1; i >= 0; i--) {
			lastEval.remove(deleted.get(i).intValue());
			biases.remove(deleted.get(i).intValue());
			lambdas.remove(deleted.get(i).intValue());
			inputWeights.remove(deleted.get(i).intValue());
			weights.remove(deleted.get(i).intValue());
			for (List<Double>  weightList: weights) {
				weightList.remove(deleted.get(i).intValue());
			}
		}
		
		// Création de nouveaux neurones
		int maxNewNeurone = (int) (network.getBiases().length * 0.1 + 1); // On rajoute au maximum 10% de la taille du réseau + 1 nouveau neurone
		int nbNew = 0;
		while (MathUtils.hasard(newNeuronRate) && nbNew < maxNewNeurone) {
			lastEval.add(0.);
			biases.add(MathUtils.RANDOM.nextGaussian());
			lambdas.add(MathUtils.RANDOM.nextGaussian());
			
			List<Double> inputWeightList = new ArrayList<Double>();
			for (int i = 0; i < network.getNbInput(); i++) {
				inputWeightList.add(null);
			}
			inputWeights.add(inputWeightList);
			
			List<Double> weightList = new ArrayList<Double>();
			for (int i = 0; i < weights.size() + 1; i++) {
				weightList.add(null);
			}
			weights.add(weightList);
			
			nbNew++;
		}
		
		// On applique les changements des différentes supression/création dans le réseau
		network.setLastEval(Utils.toPrimitiveArray(lastEval));
		network.setBiases(Utils.toPrimitiveArray(biases));
		network.setLambdas(Utils.toPrimitiveArray(lambdas));
		
		Double[][] inputWeightsArray = new Double[inputWeights.size()][network.getNbInput()];
		for (int i = 0; i < inputWeightsArray.length; i++) {
			inputWeightsArray[i] = Utils.toArray(inputWeights.get(i));
		}
		network.setInputWeights(inputWeightsArray);
		
		Double[][] weightsArray = new Double[weights.size()][weights.size()];
		for (int i = 0; i < weightsArray.length; i++) {
			weightsArray[i] = Utils.toArray(weights.get(i));
		}
		network.setWeights(weightsArray);
		
		// Mutation des biais
		muterArray(network.getBiases(), gaussianSd, weightMutationRate);
		
		// Mutation des lambda
		muterArray(network.getLambdas(), gaussianSd, weightMutationRate);
		
		// Mutation des poids d'entrée
		muterArray(network.getInputWeights(), gaussianSd, weightMutationRate, newLinkRate, deleteLinkRate);
		
		// Mutation des poids
		muterArray(network.getWeights(), gaussianSd, weightMutationRate, newLinkRate, deleteLinkRate);
		
		// On reclacule les couche du reseau
		network.calculateLayerOrder();
	}

	
	private static void muterArray(Double[][] array, double gaussianSd, double weightMutationRate, double newLinkRate, double deleteLinkRate){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == null){
					if(MathUtils.hasard(newLinkRate)){
						array[i][j] = MathUtils.RANDOM.nextGaussian()*gaussianSd;
					}
				}
				else{
					if (MathUtils.hasard(weightMutationRate)){
						if (MathUtils.hasard(deleteLinkRate)){
							array[i][j] = null;
						}
						else{
							array[i][j] = array[i][j] + MathUtils.RANDOM.nextGaussian()*gaussianSd; 
						}
					}
				}
			}
		}
	}
	
	private static void muterArray(double[] array, double gaussianSd, double weightMutationRate){
		for (int i = 0; i < array.length; i++) {
			if (MathUtils.hasard(weightMutationRate)){
				array[i] = array[i] + MathUtils.RANDOM.nextGaussian()*gaussianSd; 
			}
		}
	}
	
}
