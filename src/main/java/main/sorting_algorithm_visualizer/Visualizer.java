package main.sorting_algorithm_visualizer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

/**
 * The program is trying to use JavaFx to show the visualization of a sorting algorithm with Bubble Sort, Selection Sort, Insertion Sort, and Merge Sort
 * @author : MizteRy
 */

public class Visualizer extends Application {
    private final VBox vbox = new VBox(10);
    private final GridPane pane = new GridPane();
    private Timeline timeline;
    private List<Integer> heights;
    private boolean unsorted = false;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(true);

        HBox topButtons = Create_Top_Buttons();
        VBox bottomSection = Create_Bottom_Buttons();

        vbox.getChildren().addAll(topButtons, bottomSection);
        Color color = new Color(128/255.0, 128/255.0, 128.0/255.0, 0.7);
        vbox.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vbox, 1400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sorting Algorithm Visualizer");
        primaryStage.centerOnScreen();
        primaryStage.show();

    }

    /**
     * This method create the top 5 buttons of sortings and pause.
     * Each button will have its own event handling for each
     * @return : HBox Object
     */
    private HBox Create_Top_Buttons(){
        HBox topButtons = new HBox(10);
        topButtons.setAlignment(Pos.TOP_CENTER);
        topButtons.setSpacing(10);
        topButtons.setPadding(new Insets(10, 10, 10, 10));
        Button bubble_sort = new Button("Bubble Sort");
        Button selection_sort = new Button("Selection Sort");
        Button merge_sort = new Button("Merge Sort");
        Button insertion_sort = new Button("Insertion Sort");
        Button pause = new Button("Pause");
        bubble_sort.setOnAction(e -> {
            if (unsorted) {
                unsorted = false;
                Bubble_Sort();
            }
        });

        insertion_sort.setOnAction(e -> {
            if (unsorted) {
                unsorted = false;
                Insertion_Sort();
            }
        });

        selection_sort.setOnAction(e -> {
            if (unsorted) {
                unsorted = false;
                Selection_Sort();
            }
        });

        merge_sort.setOnAction(e -> {
            if (unsorted) {
                unsorted = false;
                Merge_Sort();
            }
        });

        pause.setOnAction(e -> {
            if (timeline != null) {
                if (timeline.getStatus() == Animation.Status.RUNNING) {
                    timeline.pause();
                }
                else{
                    timeline.play();
                }
            }
        });
        topButtons.getChildren().addAll(bubble_sort, selection_sort, insertion_sort, merge_sort, pause);
        return topButtons;
    }

    /**
     * The method create the 2 buttons on the bottoms, along with the 100 different length graph
     * @return : VBox Object
     */
    private VBox Create_Bottom_Buttons(){
        pane.setAlignment(Pos.BOTTOM_CENTER);
        pane.setVgap(5);
        Sorted_Graph();
        Button randomize = new Button("Randomize");
        Button reset = new Button("Reset");
        randomize.setOnAction(event -> {
            unsorted = true;
            Randomize_Graph();
        });
        reset.setOnAction(event -> {
            if (timeline != null) {
                if (timeline.getStatus() == Animation.Status.RUNNING || timeline.getStatus() == Animation.Status.PAUSED) {
                    timeline.stop();
                }
            }
            unsorted = false;
            timeline = null;
            Sorted_Graph();
        });
        pane.add(randomize, 0, 0);
        pane.add(reset, 1, 0);

        VBox bottomSection = new VBox(10);
        bottomSection.setAlignment(Pos.BOTTOM_CENTER);
        bottomSection.getChildren().add(pane);

        return bottomSection;

    }

    /**
     * Generate the graph with randomize even numbers from 2 to 200 in an array list
     */
    private void Randomize_Graph(){
        Remove_Stack(pane);
        HBox hbox = new HBox(2);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        heights = new ArrayList<>();
        Random ran = new Random();
        for (int i = 1; i <= 100; i++){
            while(true) {
                int height = 2 * ran.nextInt(1, 101);
                if (!heights.contains(height)) {
                    heights.add(height);
                    Rectangle rectangle = new Rectangle(10, height, Color.LIGHTGREEN);
                    hbox.getChildren().add(rectangle);
                    break;
                }
            }
        }
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(hbox);
        GridPane.setRowIndex(stackPane, 5);
        pane.getChildren().add(stackPane);
    }

    /**
     * Generate the sorted graph of even numbers from 2 to 200, this will be the first graph you will see when starting the program
     */
    private void Sorted_Graph(){
        Remove_Stack(pane);
        unsorted = false;
        HBox hbox = new HBox(2);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        for (int i = 1; i <= 100; i++){
            Rectangle rectangle = new Rectangle(10, 2*i, Color.TEAL);
            hbox.getChildren().add(rectangle);
        }
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(hbox);
        GridPane.setRowIndex(stackPane, 5);
        pane.getChildren().add(stackPane);
    }

    /**
     * This will run after bubble sort button is clicked, it will use JavaFx animation TimeLine method to show each of the sorting algorithm with 75ms of delay between each frame
     */
    private void Bubble_Sort(){
        Integer[] arr = heights.toArray(new Integer[0]);
        int n = arr.length;
        int[] i = {0};
        int[] j = {0};
        timeline = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    if (i[0] < n) {
                        if (j[0] < n - i[0] - 1) {
                            Create_Graph(arr, arr[j[0]]);
                            if (arr[j[0]] > arr[j[0] + 1]) {
                                int temp = arr[j[0]];
                                arr[j[0]] = arr[j[0] + 1];
                                arr[j[0] + 1] = temp;
                            }
                            j[0]++;
                        } else {
                            j[0] = 0;
                            i[0]++;
                        }
                    }
                    else{
                        timeline.stop();
                        Sorted_Graph();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * This will run after the insertion sort is clicked, it will use JavaFx animation to show the algorithm with 75ms of delay per frame
     */
    private void Insertion_Sort(){
        Integer[] arr = heights.toArray(new Integer[0]);
        int n = arr.length;
        int[] i = {1};
        int[] j = {0};
        int[] key = {1};
        timeline = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    if (i[0] < n) {
                        if (j[0] >= 0 && arr[j[0]] > arr[key[0]]){
                            Create_Graph(arr, arr[key[0]], arr[j[0]]);
                            int temp = arr[key[0]];
                            arr[key[0]] = arr[j[0]];
                            arr[j[0]] = temp;
                            key[0] = j[0];
                            j[0]--;
                        }
                        else {
                            i[0]++;
                            j[0] = i[0] - 1;
                            if (i[0] < n){
                                key[0] = i[0];
                            }
                        }
                    }
                    else{
                        timeline.stop();
                        Sorted_Graph();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * This will run after the selection sort button is clicked, it will then use JavaFx to run the algorithm
     */
    private void Selection_Sort(){
        Integer[] arr = heights.toArray(new Integer[0]);
        int n = arr.length;
        int[] i = {0};
        int[] j = {1};
        int[] min_indx = {0};
        int[] correct_pos = {-1};
        timeline = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    if (i[0] < n-1) {
                        if (j[0] < n){
                            if(arr[j[0]] < arr[min_indx[0]]){
                                min_indx[0] = j[0];
                            }
                            j[0]++;
                        }
                        else{
                            int temp = arr[i[0]];
                            arr[i[0]] = arr[min_indx[0]];
                            arr[min_indx[0]] = temp;
                            correct_pos[0] = i[0];
                            if (i[0] < n-2) {
                                i[0]++;
                                j[0] = i[0] + 1;
                                min_indx[0] = i[0];
                            }
                        }
                        if (j[0] < n){
                            Create_Graph(arr, arr[min_indx[0]], arr[j[0]]);
                        }
                    }
                    else{
                        timeline.stop();
                        Sorted_Graph();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * This will run after the merge sort button is clicked, it will then use JavaFx to run the algorithm
     */
    private void Merge_Sort(){
        Integer[] arr = heights.toArray(new Integer[0]);
        ArrayList<int[]> steps_reverse = new ArrayList<>();
        ArrayList<int[]> steps = new ArrayList<>();
        Get_Steps(steps, 0, arr.length - 1);
        Get_Steps_Reverse(steps_reverse, 0, arr.length - 1);
        int[] current_step = {0};
        int[] reverse_total_step = {steps_reverse.size()};
        int[] step = {0};
        int[] i = {steps.get(0)[0]};
        int[] j = {steps.get(0)[1]};
        int[] start = {i[0]};
        int[] end = {j[0]};
        ArrayList<Integer[]> copy_arr = new ArrayList<>();
        copy_arr.add(Arrays.copyOfRange(arr, 0, 1));
        int[] sorted_array_start = {0};
        Merged(copy_arr);
        timeline = new Timeline(
                new KeyFrame(Duration.millis(75), event -> {
                    if (reverse_total_step[0] > 0) {
                        Create_Merge_Graph(arr, steps_reverse.get(step[0])[0], steps_reverse.get(step[0])[1]);
                        reverse_total_step[0] = reverse_total_step[0]/2;
                        step[0]++;
                    }
                    else if (current_step[0] < steps.size()) {
                        if (i[0] <= j[0]){
                            Create_Merge_Graph(arr, copy_arr, sorted_array_start[0], j[0], i[0]);
                            i[0]++;
                        }
                        else {
                            int[] part = steps.get(current_step[0]++);
                            start[0] = part[0];
                            end[0] = part[1];
                            if (current_step[0] < steps.size()) {
                                part = steps.get(current_step[0]);
                                i[0] = part[0];
                                j[0] = part[1];
                                sorted_array_start[0] = i[0];
                                Merged(arr, start[0], end[0]);
                                Create_Merge_Graph(arr, start[0], end[0]);
                                copy_arr.remove(0);
                                copy_arr.add(Arrays.copyOfRange(arr, i[0], j[0] + 1));
                                Merged(copy_arr);
                            }
                        }
                    }
                    else{
                        timeline.stop();
                        Sorted_Graph();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     *
     */
    private void Get_Steps(ArrayList<int[]> steps, int start, int end){
        int mid = (start + end)/2;
        if (end - start == 0){
            steps.add(new int[]{start, end});
        }
        else {
            Get_Steps(steps, start, mid);
            Get_Steps(steps, mid + 1, end);
            steps.add(new int[]{start, end});
        }
    }

    private void Get_Steps_Reverse(ArrayList<int[]> steps, int start, int end){
        int mid = (start + end)/2;
        if (end - start == 0){
            steps.add(new int[]{start, end});
        }
        else {
            steps.add(new int[]{start, end});
            Get_Steps_Reverse(steps, start, mid);
            Get_Steps_Reverse(steps, mid + 1, end);
        }
    }

    private void Merged(ArrayList<Integer[]> list){
        Integer[] arr = list.get(0);
        Merged(arr, 0, arr.length - 1);
    }

    private void Merged(Integer[] arr, int start, int end){
        for (int i = start; i <= end; i++){
            int key = arr[i];
            int j = i-1;
            while (j >= start && arr[j] > key){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    private void Create_Merge_Graph(Integer[] arr, ArrayList<Integer[]> list, int start, int end, int current_pos){
        Integer[] copy_arr_sorted = list.get(0);
        Remove_Stack(pane);
        HBox graph = new HBox(2);
        graph.setAlignment(Pos.BOTTOM_CENTER);
        int j = 0;
        for (int i = 0; i < arr.length; i++){
            Rectangle rec;
            if (i >= start && i <= current_pos){
                rec = new Rectangle(10, copy_arr_sorted[j++], Color.LIGHTGREEN);
            }
            else if (i >= start && i <= end){
                rec = new Rectangle(10, arr[i], Color.CRIMSON);
            }
            else{
                rec = new Rectangle(10, arr[i], Color.LIGHTGREEN);
            }
            graph.getChildren().add(rec);
        }
        Add_Graph(graph);
    }

    private void Create_Merge_Graph(Integer[] numbers, int start, int end){
        Remove_Stack(pane);
        HBox graph = new HBox(2);
        graph.setAlignment(Pos.BOTTOM_CENTER);
        for (int i = 0; i < numbers.length; i++){
            Rectangle rec;
            if (i >= start && i <= end){
                rec = new Rectangle(10, numbers[i], Color.CRIMSON);
            }
            else{
                rec = new Rectangle(10, numbers[i], Color.LIGHTGREEN);
            }
            graph.getChildren().add(rec);
        }
        Add_Graph(graph);
    }

    private void Create_Graph(Integer[] numbers, int target){
        Remove_Stack(pane);
        HBox graph = new HBox(2);
        graph.setAlignment(Pos.BOTTOM_CENTER);
        for (int i = 0; i < numbers.length; i++){
            Rectangle rec;
            if (2*(i+1) == numbers[i]){
                rec = new Rectangle(10, numbers[i], Color.TEAL);
            }
            else {
                rec = new Rectangle(10, numbers[i], (numbers[i] == target) ? Color.CRIMSON : Color.LIGHTGREEN);
            }
            graph.getChildren().add(rec);
        }
        Add_Graph(graph);
    }

    private void Create_Graph(Integer[] numbers , int switch1, int switch2){
        Remove_Stack(pane);
        HBox graph = new HBox(2);
        graph.setAlignment(Pos.BOTTOM_CENTER);
        for (int i = 0; i < numbers.length; i++){
            Rectangle rec;
            if (2*(i+1) == numbers[i]){
                rec = new Rectangle(10, numbers[i], Color.TEAL);
            }
            else if (numbers[i] == switch1 || numbers[i] == switch2){
                rec = new Rectangle(10, numbers[i], Color.CRIMSON);
            }
            else{
                rec = new Rectangle(10, numbers[i], Color.LIGHTGREEN);
            }
            graph.getChildren().add(rec);
        }
        Add_Graph(graph);
    }

    private void Add_Graph( HBox graph){
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(graph);
        GridPane.setRowIndex(stackPane, 5);
        pane.getChildren().add(stackPane);
    }

    private void Remove_Stack(GridPane pane){
        for (Node node : pane.getChildren()){
            if (node instanceof StackPane){
                pane.getChildren().remove(node);
                break;
            }
        }
    }

    public static void main(String[] args){Application.launch(args);}
}
