package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tom.lib.netwolve.MathUtils;
import tom.lib.netwolve.interfaces.Provider;

public class Network {

	private Provider[] inputs;
	private HashMap<Neuron, Provider[]> nodes;
	private Neuron[] outputs;
	
	public Network(Provider[] inputs, Neuron[] inners, int nbOutput, double txCon) {
		assert(nbOutput <= inners.length);
		this.inputs = inputs;
		nodes = new HashMap<Neuron, Provider[]>();
		for (Neuron neurone : inners) {
			ArrayList<Provider> liste = new ArrayList<Provider>();
			for (Neuron lien : inners) {
				if (MathUtils.hasard(txCon))
					liste.add(lien);
			}
			for (Provider lien : this.inputs) {
				if (MathUtils.hasard(txCon))
					liste.add(lien);
			}
			nodes.put(neurone, liste.toArray(new Neuron[]{}));
		}
		outputs = new Neuron[nbOutput];
		System.arraycopy(inners, 0, outputs, 0, nbOutput);
	}

	public float[] eval() {
		// Récupération des entrées de chaques neurones
		Map<Neuron, float[]> toEval = new HashMap<>();
		for (Entry<Neuron, Provider[]> entry : nodes.entrySet()) {
			Neuron neuron = entry.getKey();
			Provider[] providers = entry.getValue();
			
			float[] neuronInput = new float[providers.length];
			for (int i = 0; i < neuronInput.length; i++) {
				neuronInput[i] = providers[i].provide();
			}
			
			toEval.put(neuron, neuronInput);
		}
		
		// Evaluation des neurones
		for (Entry<Neuron, float[]> entry : toEval.entrySet()) {
			Neuron neural = entry.getKey();
			float[] value = entry.getValue();
			neural.eval(value);
		}
		
		float[] networkOutput = new float[outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			networkOutput[i] = outputs[i].provide();
		}
		
		return networkOutput;
	}
	
	public Provider[] getInputs() {
		return inputs;
	}
	
	public HashMap<Neuron, Provider[]> getNodes() {
		return nodes;
	}
	
	public Neuron[] getOutputs() {
		return outputs;
	}
	
	public void setInputs(Provider[] inputs) {
		this.inputs = inputs;
	}
	
	public void setNodes(HashMap<Neuron, Provider[]> nodes) {
		this.nodes = nodes;
	}
	
	public void setOutputs(Neuron[] outputs) {
		this.outputs = outputs;
	}

}
