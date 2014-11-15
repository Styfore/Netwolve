package tom.lib.netwolve.elements;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class NeuralTest {

	@Test
	public void creationTest(){
		try {
			Neural neural = new Neural(2);
			System.out.println(Arrays.toString(neural.eval(new float[]{1f, 0f})));
			assertTrue(true);
		} catch (Exception e) {
			assertNotNull(null);
		}
	}
}
