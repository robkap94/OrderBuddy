package com.robertkaptur.orderbuddy;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddOrderDialogController {

    // FXML Fields
    @FXML
    TextField titleTextField;
    @FXML
    TextField categoryTextField;
    @FXML
    TextField priceTextField;
    @FXML
    TextArea descriptionTextArea;

    public Order processOrder() {
        String title = titleTextField.getText().trim();
        String category = categoryTextField.getText().trim();
        double price = Double.parseDouble(priceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();

        Order newOrder = new Order(title, category, price, description, "Some date of order", "Some date of delivery");

        return newOrder;
    }
}