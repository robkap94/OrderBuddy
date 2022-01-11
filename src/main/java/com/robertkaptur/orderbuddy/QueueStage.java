package com.robertkaptur.orderbuddy;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class QueueStage extends Stage {

    public QueueStage(Stage queueStage) throws IOException {
        // Loading of the Manager View's window
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("queue-view.fxml"));
        Scene managerView = new Scene(fxmlLoader.load(), 480, 640);
        queueStage.setTitle("Order Buddy - Manager View");
        queueStage.setScene(managerView);
        queueStage.show();
        // Positioning Manager View's window
        Rectangle2D managerViewBounds = Screen.getPrimary().getVisualBounds();
        double managerPosX = managerViewBounds.getMinX() + (managerViewBounds.getWidth() - managerView.getWidth()) * 0.9;
        queueStage.setX(managerPosX);
    }
}
