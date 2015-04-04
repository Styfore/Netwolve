package tom.lib.netwolve.elements;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import tom.lib.netwolve.services.Function;
import tom.lib.netwolve.services.MathUtils;
import tom.lib.netwolve.services.NetworkUtils;

public class NetworkTest {

	@Test
	public void creationTest(){
		Network network = new Network(5, 2, 1, 0.25, Function.SIGMOIDE);

		NetworkUtils.exportToCsv("target/network.csv", network);
		for (int i = 0; i < 5; i++) {
			double[] in = new double[]{MathUtils.RANDOM.nextGaussian(), MathUtils.RANDOM.nextGaussian()};
			System.out.print(Arrays.toString(in)  + " =>" );
			System.out.println(Arrays.toString(network.eval(in)));
		}
		System.out.println(network.getLayerOrder());
		assertTrue(true);
	}
	
	@Test
	public void mutationTest(){
		Network network = new Network(6, 2, 3, 0.5, Function.SIGMOIDE);
		NetworkUtils.mute(network, 1, 1, 0, 0, 0, 0);
	}
	
	@Test
	public void creationLienTest(){
		Network network = new Network(6, 2, 3, 0., Function.SIGMOIDE);
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				assertNull(network.getWeights()[i][j]);
			}
		}
		NetworkUtils.mute(network, 1, 0, 1, 0, 0, 0);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				assertNotNull(network.getWeights()[i][j]);
			}
		}
	}

	@Test
	public void suppressionLienTest(){
		Network network = new Network(6, 2, 3, 1., Function.SIGMOIDE);
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				assertNotNull(network.getWeights()[i][j]);
			}
		}
		NetworkUtils.mute(network, 1, 1, 0, 1, 0, 0);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				assertNull(network.getWeights()[i][j]);
			}
		}
	}
	
	@Test
	public void creationNeuroneTest(){
		Network network = new Network(100, 2, 3, 1., Function.SIGMOIDE);
		assertEquals(100, network.getBiases().length);
		NetworkUtils.mute(network, 1, 0, 0, 0, 1, 0);
		assertEquals(111, network.getBiases().length);
	}
	
	@Test
	public void deleteNeuroneTest(){
		Network network = new Network(100, 2, 3, 1., Function.SIGMOIDE);
		assertEquals(100, network.getBiases().length);
		NetworkUtils.mute(network, 1, 0, 0, 0, 0, 1);
		assertEquals(3, network.getBiases().length);
	}
}
