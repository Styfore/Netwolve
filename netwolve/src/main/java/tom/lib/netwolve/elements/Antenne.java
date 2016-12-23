package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

public class Antenne implements Observer {

	private Point2D coord;
	
	private double[] lengths;
	private double[] levels;
	private List<Boolean> cells;
	
	public Antenne(Point2D coord, List<Boolean> cells) {
		this.coord = new Point2D(coord.getX(), coord.getY());
		if (cells == null ){
			this.cells = new ArrayList<>();
		}
		else{
			this.cells = new ArrayList<Boolean>(cells);
		}
		updateCells();
	}
	
	public Antenne(Antenne antenne) {
		this.coord = new Point2D(antenne.coord.getX(), antenne.coord.getY());
		cells = new ArrayList<Boolean>(antenne.cells);
		updateCells();
	}
	
	
	private void updateCells(){
		if (cells.isEmpty()){
			lengths = new double[0];
			levels = new double[0];
		}
		else{
			boolean state = cells.get(0);
			double cumul = 0;
			List<Double> lengths = new ArrayList<>();
			for (boolean b : cells) {
				if (b == state){
					cumul++;
				}
				else{
					lengths.add(cumul);
					cumul = 1;
					state = !state;
				}
			}
			lengths.add(cumul);
			
			this.lengths = new double[lengths.size()];
			this.levels = new double[lengths.size()];
			// Sommes pondérées
			double sum = lengths.parallelStream().mapToDouble(Double::doubleValue).sum();
			this.lengths[0] = lengths.get(0)/sum;
			for (int i = 1; i < this.lengths.length; i++) {
				this.lengths[i] = this.lengths[i-1] + lengths.get(i)/sum;	
			}
		}
	}
	
	public void capte(double orientation, Point2D element, double level){
		if (size() > 0){
			// On calcul l'angle d'arrivé
			double r = coord.distance(element);
			if (r > 0){
				double theta = Math.atan2(element.getY() - coord.getY(), element.getX() - coord.getX()) - orientation;
				theta=theta/(2*Math.PI);
				theta=theta<0?theta+1:theta;
				
				int i;
				for (i = 0; i < size(); i++) {
					if (theta<lengths[i]){
						break;
					}
				}
				
				i = Math.min(i, lengths.length - 1);
				
				levels[i] = levels[i] + level/(r*r); 
			}
		}
	}

	private void rotate(double angle, Point2D center){
		coord = Transform.rotate(angle, center.getX(), center.getY()).transform(coord);
	}
	

	public List<Boolean> getCells() {
		return new ArrayList<Boolean>(cells);
	}
	
	public void setCells(List<Boolean> cells) {
		this.cells = new ArrayList<Boolean>(cells);
		updateCells();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Point2D center = ((Bubble) o).getCoord();
		Double angle = (Double) arg;
		rotate(angle, center);
	}
	
	public void clear(){
		Arrays.fill(levels, 0);
	}
	
	public double[] getLevels() {
		return levels;
	}
	
	public Point2D getCoord() {
		return coord;
	}
	
	public int size(){
		return lengths.length;
	}

}
