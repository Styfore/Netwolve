package tom.lib.netwolve.elements;

import javafx.geometry.Point2D;
import tom.lib.netwolve.ihm.NetwolveApplication;
import tom.lib.netwolve.utils.MathUtils;

public class Food {

	public Food() {
		coord = new Point2D(NetwolveApplication.getSize()*MathUtils.RANDOM.nextDouble(), NetwolveApplication.getSize()*MathUtils.RANDOM.nextDouble());
	}
	
	private Point2D coord;
	
	public Point2D getCoord() {
		return coord;
	}
}
