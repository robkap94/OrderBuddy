package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddOrderDialogController {

    // FXML Fields
    @FXML
    TextField titleTextField;
    @FXML
    ComboBox categoryComboBox;
    @FXML
    TextField priceTextField;
    @FXML
    TextArea descriptionTextArea;

    // Fields
    ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();
    OrderData orderData = OrderData.getInstance();
    Category selectedCategory;

    @FXML
    private void initialize() {
        categoryObservableList.setAll(orderData.getListOfCategories()); // observableArrayList is populated by observableArrayList from OrderData
        categoryComboBox.setItems(categoryObservableList);
        updateComboBoxCellFactory();
    }

    public Order processOrder() { // Processing order in window dialog
        String title = titleTextField.getText().trim();
        selectedCategory = (Category) categoryComboBox.getValue();
        String category = selectedCategory.getCategoryName();
        double price = Double.parseDouble(priceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();

        return new Order(title, category, price, description, "Some date of order", "Some date of delivery");

        /* TODO: PLEASE NOTICE TO REBUILD Dates in object creation:
        a) Date of Creation is CURRENT DATE in ISO_LOCAL_DATE_TIME
        b) Date of Delivery is NULL

        Ensure that these dates will be format into ISO8601*/
    }

    private void updateComboBoxCellFactory() { // Updating way of CellFactory's behaviour
        categoryComboBox.setCellFactory(listView -> new CellFactoryForComboBox());
        categoryComboBox.setButtonCell(new CellFactoryForComboBox());
    }

    private static class CellFactoryForComboBox extends ListCell<Category> { // Creating static class, which extends ListCell, to update items in the ComboBox
        @Override
        protected void updateItem(Category category, boolean b) {
            super.updateItem(category, b);
            if(category != null) {
                setText(category.getCategoryName().trim());
            } else {
                setText(null);
            }
        }
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }
}