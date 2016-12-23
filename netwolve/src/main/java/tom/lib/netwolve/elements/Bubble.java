package tom.lib.netwolve.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import tom.lib.genetic.interfaces.Evaluable;
import tom.lib.genetic.interfaces.Selectionnable;
import tom.lib.netwolve.commun.Constantes;
import tom.lib.netwolve.commun.Function;
import tom.lib.netwolve.ihm.NetwolveApplication;
import tom.lib.netwolve.services.NetworkServices;
import tom.lib.netwolve.suppliers.AntenneSupplier;
import tom.lib.netwolve.utils.ArrayUtils;
import tom.lib.netwolve.utils.MathUtils;

public class Bubble extends Observable implements Evaluable {

	private Network network;
	private Set<Antenne> antennes;	
	private boolean vitesseSensor;
	
	private Point2D coord;
	private Color color;

	
	private int score = 0;

	private static final double SIGMA = 1;
	private static final double RATE = 0.1;
	private static final double GEOMETRIC_PARAM = 0.5;
		
	public Bubble(Bubble bubble) {
		this.network = new Network(bubble.network);
		this.vitesseSensor = bubble.vitesseSensor;
		
		this.coord = new Point2D(bubble.coord.getX(), bubble.coord.getY());
		this.color = new Color(bubble.color.getRed(), bubble.color.getGreen(), bubble.color.getBlue(), 1);
		
		this.antennes = new HashSet<>();
		for (Antenne antenne : bubble.antennes) {
			Antenne copiedAntenne = new Antenne(antenne);
			antennes.add(copiedAntenne);
			this.addObserver(copiedAntenne);
		}
		
	}
	
	public Bubble(int nbNeurons, double connexionRate) {
		network = new Network(nbNeurons, 16, 2, connexionRate, Function.SIGMOIDE);
		vitesseSensor = MathUtils.RANDOM.nextBoolean();
		coord = new Point2D(NetwolveApplication.getSize()*MathUtils.RANDOM.nextDouble(), NetwolveApplication.getSize()*MathUtils.RANDOM.nextDouble());
		color = new Color(MathUtils.RANDOM.nextDouble(), MathUtils.RANDOM.nextDouble(), MathUtils.RANDOM.nextDouble(), 1);
		
		AntenneSupplier antenneSupplier = new AntenneSupplier(coord, 3);
		this.antennes = new HashSet<>();
		while (MathUtils.hasard(GEOMETRIC_PARAM)) {
			Antenne antenne = antenneSupplier.get();
			antennes.add(antenne);
			this.addObserver(antenne);
		}
		
	}
	
	public Point2D getCoord() {
		return coord;
	}
	
	public Color getColor() {
		return color;
	}
	
	
	public void eval(){
		antennes.parallelStream().forEach(antenne -> antenne.clear());
		for (Food food : NetwolveApplication.getFoods()) {
			antennes.parallelStream().forEach(antenne -> antenne.capte(Constantes.DEGREE_FACTOR*getOrientation(), food.getCoord(), 1));
		}
		
		// Construction de l'entree
		List<Double> input = new ArrayList<>();
		if (vitesseSensor){
			input.add(getVitesse());
		}
		antennes.stream().forEach(antenne -> input.addAll(ArrayUtils.toList(antenne.getLevels())));

		network.eval(ArrayUtils.toPrimitiveArray(input));
		
		move();
	}
	
	
	private double getVitesse(){
		return network.getLastEval()[0];
	}
	
	private double getOrientation(){
		return network.getLastEval()[1];
	}
	
	private void move(){

		double x = coord.getX() + Math.cos(Constantes.RADIAN_FACTOR*getOrientation())*getVitesse();
		double y = coord.getY() + Math.sin(Constantes.RADIAN_FACTOR*getOrientation())*getVitesse();
		
		coord = new Point2D(x, y);

		// On notifie les éléments qu'on s'ets déplacé.
		setChanged();
		notifyObservers(Constantes.DEGREE_FACTOR*getOrientation());
	}
	
	
	@Override
	public Double getFitness() {
		return (double) score;
	}

	@Override
	public void mute() {
		NetworkServices.mute(network, SIGMA, RATE, RATE, RATE, RATE, RATE);
//		antennes.parallelStream().forEach(antenne -> AntenneUtils.mute(antenne, RATE));
		
		if (MathUtils.hasard(RATE)){
			vitesseSensor = !vitesseSensor;
		}
		
		double r, g, b;
		if (MathUtils.hasard(RATE)){
			r = color.getRed() + MathUtils.RANDOM.nextGaussian()*SIGMA;
		}
		else{
			r = color.getRed();
		}
		if (MathUtils.hasard(RATE)){
			g = color.getGreen() + MathUtils.RANDOM.nextGaussian()*SIGMA;
		}
		else{
			g = color.getGreen();
		}
		if (MathUtils.hasard(RATE)){
			b = color.getBlue() + MathUtils.RANDOM.nextGaussian()*SIGMA;
		}
		else{
			b = color.getBlue();
		}
		
		color = new Color(r, g, b, 1);
	}

}