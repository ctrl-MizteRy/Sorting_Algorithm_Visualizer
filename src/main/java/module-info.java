module main.sorting_algorithm_visualizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.sorting_algorithm_visualizer to javafx.fxml;
    exports main.sorting_algorithm_visualizer;
}