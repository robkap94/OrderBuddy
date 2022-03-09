package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {

    @Override
    public void start(Stage managerStage) { // At the start of an app
        // Initialization of the windows
        launchWindows(managerStage);
    }

    public static void main(String[] args) {
        launch(); // launches windows
    }

    @Override
    public void init() { // During init of app, database is loaded
        try {
            OrderData.getInstance().loadSqlDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // During stop of app, database was saved. Now it is redundant as queryUpdate is going directly into db
        // TODO: Find use for this override method or delete it
    }

    // Current method to initialize windows
    // It takes first "Stage" container which is initially constructed by the platform, as a Manager Window
    // Then creates second stage, as a Queue Window
    public static void launchWindows(Stage managerStage) {
        try {
            // Initialization of Manager Window
            new Window(managerStage, "manager-view.fxml", 900, 600, 0.1,
                    "Order Buddy - Manager View", true, false);
            // Initialization of Queue Window
            Stage queueStage = new Stage();
            new Window(queueStage, "queue-view.fxml", 480, 640, 0.9,
                    "Order Buddy - Queue View", true, false);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}