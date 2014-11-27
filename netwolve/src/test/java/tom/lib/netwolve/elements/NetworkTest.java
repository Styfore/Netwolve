package tom.lib.netwolve.elements;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import tom.lib.netwolve.interfaces.Transformation;

public class NetworkTest {

	@Test
	public void creationTest(){
		Transformation t = Mockito.mock(Transformation.class);
		Mockito.when(t.getLastEval()).thenReturn(new float[]{});
		// Création des neurones internes
		Transformation[] inputs = new Transformation[2];
		Transformation[] inners = new Transformation[10];
		for (int i = 0; i < inners.length; i++) {
			inners[i] = new Neural(3);
		}
		inputs[0] = inners[0];
		inputs[1] = inners[1];
		Network network = new Network(inputs, inners, 2, 0.5);

		System.out.println(Arrays.toString(network.eval(new float[]{0.1f, 0.2f})));
		for (int i = 0; i < 3; i++) {
			System.out.println(Arrays.toString(network.eval()));
		}
		assertTrue(true);
	}
}
