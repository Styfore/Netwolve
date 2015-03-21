package tom.lib.netwolve.elements;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import tom.lib.netwolve.interfaces.Provider;

public class NetworkTest {

	@Test
	public void creationTest(){
		Provider t = Mockito.mock(Provider.class);
		Mockito.when(t.provide()).thenReturn(1f);
		// Création des neurones internes
		Provider[] inputs = new Provider[2];
		Neuron[] inners = new Neuron[10];
		for (int i = 0; i < inners.length; i++) {
			inners[i] = new Neuron(3);
		}
		inputs[0] = inners[0];
		inputs[1] = inners[1];
		Network network = new Network(inputs, inners, 2, 0.5);

		for (int i = 0; i < 5; i++) {
			System.out.println(Arrays.toString(network.eval()));
		}
		assertTrue(true);
	}
}
