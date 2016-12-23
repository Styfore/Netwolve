package tom.lib.netwolve.ihm;

import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tom.lib.genetic.interfaces.Selectionnable;

public class NetwolveApplication extends Application {

	private static final String MY_DEFAULT_FONT = null;
	private static int size;
	private static Set<Selectionnable> population;
	
	public static void main(String[] args) {
		Application.launch(NetwolveApplication.class, args);
	}


	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Netwolve");
		Pane root = new Pane();
		Scene scene = new Scene(root, 500, 500);
		
		VBox configuration = new VBox();
		configuration.setAlignment(Pos.BASELINE_LEFT);
		configuration.setLayoutX(10);
		configuration.setLayoutY(10);

		Spinner<Integer> spinnerTaillePop = new Spinner<>(10, 500, 20);
		spinnerTaillePop.setEditable(true);
		spinnerTaillePop.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		Label taillePopLabel = new Label("Taille de la population", spinnerTaillePop);
		taillePopLabel.setAlignment(Pos.BASELINE_RIGHT);
		taillePopLabel.setContentDisplay(ContentDisplay.RIGHT);
		taillePopLabel.setMinWidth(300);
		configuration.getChildren().add(taillePopLabel);
		
		
		Spinner<Double> spinnerMutationRate = new Spinner<>(0.0, 1, 0.01, 0.01);
		spinnerTaillePop.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		spinnerMutationRate.setEditable(true);
		Label mutationRateLabel = new Label("Taux de mutation", spinnerMutationRate);
		mutationRateLabel.setAlignment(Pos.BASELINE_RIGHT);
		mutationRateLabel.setContentDisplay(ContentDisplay.RIGHT);
		mutationRateLabel.setMinWidth(300);
		configuration.getChildren().add(mutationRateLabel);
		mutationRateLabel.setPadding(new Insets(10, 0, 0, 0));
		root.getChildren().add(configuration);
		

		stage.setScene(scene);
		stage.show();

	}

	
	public static int getSize() {
		return size;
	}
	
	public static Set<Selectionnable> getPopulation() {
		return population;
	}
	
}

