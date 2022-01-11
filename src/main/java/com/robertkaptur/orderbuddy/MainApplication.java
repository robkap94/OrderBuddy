package com.robertkaptur.orderbuddy;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage managerStage) {
        try {
            // Initialization of Manager Window
            new ManagerStage(managerStage);
            // Initialization of Queue Window
            Stage queueStage = new Stage();
            new QueueStage(queueStage);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}