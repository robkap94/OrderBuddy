package com.robertkaptur.orderbuddy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Manager View!");
    }
}