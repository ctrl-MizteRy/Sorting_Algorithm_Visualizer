package main.sorting_algorithm_visualizer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Visualizer_Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}