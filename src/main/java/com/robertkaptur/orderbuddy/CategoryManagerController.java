package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.sql.SQLException;
import java.util.Optional;

public class CategoryManagerController {

    // FXML Fields
    @FXML
    GridPane categoryManagerGridPane;
    @FXML
    ListView<Category> categoryManagerListView = new ListView<>();
    @FXML
    Button addCategoryButton;
    @FXML
    Button editCategoryButton;
    @FXML
    Button deleteCategoryButton;

    // Fields
    ObservableList<Category> categoriesList = FXCollections.observableArrayList();
    OrderData orderData = OrderData.getInstance();

    @FXML
    public void initialize() { // During init of ManagerController
        categoriesList.setAll(orderData.getListOfCategories()); // observableArrayList in this controller, is populated by observableArrayList from OrderData

        categoryManagerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Setting selection model
        categoryManagerListView.getSelectionModel().selectFirst(); // Will be selected first, existing, order
        categoryManagerListView.setItems(categoriesList); // Setting ListView for items from categoriesList (observableArrayList)
        updateListViewCellFactory(); // Setting up proper way of updating cells in ListView's CellFactory
    }

    @FXML
    protected void showCreateCategoryDialog() { // Opening Dialog to create order (with all fields to be filled)

        // Initializing, declaring and processing Dialog Window to create Order

        DialogWindow dialogWindow = new DialogWindow(categoryManagerGridPane.getScene().getWindow(), "This is create category dialog",
                "Create new category", "fxml/addCategoryDialog-view.fxml");
        dialogWindow.prepareDialog(true, true);
        Optional<ButtonType> result = dialogWindow.launchDialogResult();
        FXMLLoader dialogLoader = dialogWindow.getDialogLoader();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            AddCategoryDialogController controller = dialogLoader.getController();
            Category newCategory = controller.processCategory();
            categoriesList.add(newCategory); // Adding to FXCollections' observableArrayList
            categoryManagerListView.setItems(categoriesList); // Setting ListView to items which are in observableArrayList
            categoryManagerListView.getSelectionModel().select(newCategory); // Selecting, on ListView, newly created category
            orderData.addCategory(newCategory); // Adding, newly created category, into OrderData instance

            // Adding order into SQL DB

            try {
                orderData.addCategoryToSqlDatabase(newCategory);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateListViewCellFactory() { // Updating way of CellFactory's behaviour
        categoryManagerListView.setCellFactory(param -> new ListCell<>() { // Utilizing Cell Factory to ensure ListView is named as id + name
            @Override
            protected void updateItem(Category category, boolean b) {
                super.updateItem(category, b);

                if(b || category == null || category.getCategoryName() == null) {
                    setText(null);
                } else {
                    setText("id " + category.getId() + ": " + category.getCategoryName()); // Setting up pattern of category's name in the ListView
                }
            }
        });
    }

    // TODO: Add Category Manager window's functionality here

}
