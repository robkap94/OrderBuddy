package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage managerStage) { // At the start of an app
        // Initialization of the windows
        launchWindows(managerStage);
    }

    public static void main(String[] args) { // main method, just to launch windows (at the moment)
        launch();
    }

    @Override
    public void init() { // During init of app, database is loaded
        try {
            OrderData.getInstance().loadDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() { // During stop of app, database is saved
        try {
            OrderData.getInstance().saveDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Current method to initialize windows
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
}