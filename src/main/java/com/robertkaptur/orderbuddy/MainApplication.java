package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    // Fields
    ListView<Order> orderListView;
    ObservableList<Order> listOfOrders = FXCollections.observableArrayList(); // TODO - This should be ObservableList used to be populated by Order's Constructor

    @Override
    public void start(Stage managerStage) {
        // Initialization of the windows
        launchWindows(managerStage);
        // Initialization of ListView of Orders
        testOrders(); // Just created to populate a little of testing objects
        orderListView = new ListView<Order>(listOfOrders); // TODO - This will be used to code ListView
    }



    public static void main(String[] args) {
        launch();
    }

    // Method to initialize windows
    // It takes first "Stage" container which is initially constructed by the platform, as a Manager Window
    // Then creates second stage, as a Queue Window
    public static void launchWindows(Stage managerStage) {
        try {
            // Initialization of Manager Window
            new Window(managerStage, "manager-view.fxml", 900, 600, 0.1, "Order Buddy - Manager View");
            // Initialization of Queue Window
            Stage queueStage = new Stage();
            new Window(queueStage, "queue-view.fxml", 480, 640, 0.9, "Order Buddy - Queue View");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void testOrders() {
        Order first = new Order("Test first", "category 1", 21.37, "Najlepsze kremowki swiata", "17-03-2021", "18-03-2021");
        Order second = new Order("Test second", "category 2", 37.21, "Najlepsze kotki swiata", "22-03-2021", "23-03-2021");
        Order third = new Order("Test third", "category 3", 19.39, "Najlepsze krowki swiata", "11-03-2021", "12-03-2021");
    }
}