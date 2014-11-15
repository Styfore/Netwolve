package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.primitives.Floats;

import tom.lib.netwolve.interfaces.Transformation;

public class Network extends Transformation {

	private Transformation[] inputs;
	private HashMap<Transformation, Transformation[]> nodes;
	private Transformation[] outputs;
	
	public float[] eval() {
		HashMap<Transformation, float[]> majsNodes = new HashMap<>();
		for (Entry<Transformation, Transformation[]> entry : nodes.entrySet()) {
			Transformation key = entry.getKey();
			Transformation[] value = entry.getValue();
			
			ArrayList<Float> inputList = new ArrayList<>();
			for (Transformation transformation : value) {
				inputList.addAll(Floats.asList(transformation.getLastEval()));
			}
			majsNodes.put(key, key.eval(Floats.toArray(inputList)));
		}
		
		for (Entry<Transformation, float[]> entry : majsNodes.entrySet()) {
			Transformation key = entry.getKey();
			float[] value = entry.getValue();
			key.setLastEval(value);
		}

		ArrayList<Float> outputList = new ArrayList<>();
		for (Transformation transformation : this.outputs) {
			outputList.addAll(Floats.asList(transformation.getLastEval()));
		}
		
		return Floats.toArray(outputList);
	}
	
	@Override
	public float[] eval(float[] inputs) {
		for (Transformation t : this.inputs) {
			t.setLastEval(inputs);
		}
		
		return eval();
	}

}
