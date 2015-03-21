package tom.lib.netwolve.elements;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NeuralTest {

	@Test
	public void creationTest(){
			Neuron neural = new Neuron(2);
			neural.eval(new float[]{1f, 0f});
			System.out.println(neural.provide());
			assertTrue(true);
	}
}
