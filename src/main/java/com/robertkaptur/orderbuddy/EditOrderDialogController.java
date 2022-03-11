package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditOrderDialogController {

    // FXML fields
    @FXML
    private TextField oldTitleTextField;
    @FXML
    private TextField oldCategoryTextField;
    @FXML
    private TextField oldPriceTextField;
    @FXML
    private TextArea oldDescriptionTextArea;
    @FXML
    private TextField newTitleTextField;
    @FXML
    private ComboBox<Category> newCategoryComboBox;
    @FXML
    private TextField newPriceTextField;
    @FXML
    private TextArea newDescriptionTextArea;

    // Fields
    private ObservableList<Category> categoriesList = FXCollections.observableArrayList();
    private OrderData orderData = OrderData.getInstance();
    private Category selectedCategory;

    private String title;
    private String category;
    private double price;
    private String description;

    @FXML
    public void initialize() { // During init of ManagerController
        categoriesList.setAll(orderData.getListOfCategories()); // observableArrayList is populated by observableArrayList from OrderData
        newCategoryComboBox.setItems(categoriesList);
        updateComboBoxCellFactory();
    }

    public void processOrder(Order order) {
        oldTitleTextField.setText(order.getTitle());
        oldCategoryTextField.setText(order.getCategory());
        oldPriceTextField.setText(String.valueOf(order.getPrice()));
        oldDescriptionTextArea.setText(order.getDescription());
    }

    public void modifyOrder() {
        title = newTitleTextField.getText().trim();
        selectedCategory = newCategoryComboBox.getValue();
        category = selectedCategory.getCategoryName();
        price = Double.parseDouble(newPriceTextField.getText().trim());
        description = newDescriptionTextArea.getText().trim();

        /* TODO: PLEASE NOTICE TO REBUILD Dates in object modify:
        a) Date of Creation is CURRENT DATE in ISO_LOCAL_DATE_TIME
        b) Date of Delivery is NULL

        Ensure that these dates will be format into ISO8601*/
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public String getNewTitle() {
        return title;
    }

    public String getNewCategory() {
        return category;
    }

    public double getNewPrice() {
        return price;
    }

    public String getNewDescription() {
        return description;
    }

    private void updateComboBoxCellFactory() { // Updating way of CellFactory's behaviour
        newCategoryComboBox.setCellFactory(listView -> new CellFactoryForComboBox());
        newCategoryComboBox.setButtonCell(new CellFactoryForComboBox());
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
}
