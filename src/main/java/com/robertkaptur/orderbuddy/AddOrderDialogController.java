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

    public Order processOrder() { // Processing order in window dialog
        String title = titleTextField.getText().trim();
        // TODO: \/ Should be dropdown list to choose categories, should be implemented in #52. Temporarily provide here only category id (number/int) when testing
        String category = categoryTextField.getText().trim();
        double price = Double.parseDouble(priceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();

        return new Order(title, category, price, description, "Some date of order", "Some date of delivery");

        /* TODO: PLEASE NOTICE TO REBUILD Dates in object creation:
        a) Date of Creation is CURRENT DATE in ISO_LOCAL_DATE_TIME
        b) Date of Delivery is NULL

        Ensure that these dates will be format into ISO8601*/
    }
}