package com.robertkaptur.orderbuddy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.Optional;

public class CategoryManagerController {

    // FXML Fields for Category Manager Window
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
    Category currentlySelectedCategory;

    @FXML
    public void initialize() { // During init of ManagerController
        categoriesList.setAll(orderData.getListOfCategories()); // observableArrayList in this controller, is populated by observableArrayList from OrderData

        categoryManagerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Setting selection model
        categoryManagerListView.getSelectionModel().selectFirst(); // Will be selected first, existing, order
        categoryManagerListView.setItems(categoriesList); // Setting ListView for items from categoriesList (observableArrayList)
        updateListViewCellFactory(); // Setting up proper way of updating cells in ListView's CellFactory

        // Disabling buttons on init (will be handled, during app's lifecycle, by event listener

        editCategoryButton.setDisable(true);
        deleteCategoryButton.setDisable(true);

        // Listener for selected category

        categoryManagerListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observableValue, Category oldValue, Category newValue) {
                if(newValue == null) {
                    editCategoryButton.setDisable(true);
                    deleteCategoryButton.setDisable(true);
                } else {
                    editCategoryButton.setDisable(false);
                    deleteCategoryButton.setDisable(false);
                    currentlySelectedCategory = newValue;
                }
            }
        });
    }

    @FXML
    protected void onCreateButtonClicked() {
        showCreateCategoryDialog();
    }

    @FXML
    protected void onEditButtonClicked() {
        showEditCategoryDialog();
    }

    @FXML
    protected void onDeleteButtonClicked() {
        if(currentlySelectedCategory != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete category '" + currentlySelectedCategory.getCategoryName() + "'?", ButtonType.YES, ButtonType.NO);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {

                // Deleting category from SQL DB

                try {
                    orderData.deleteCategoryFromSqlDatabase(currentlySelectedCategory);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                orderData.deleteCategory(currentlySelectedCategory); // Removing, selected category, from OrderData instance
                categoriesList.remove(currentlySelectedCategory); // Removing from FXCollections' observableArrayList
            }
        }
    }

    @FXML
    protected void showCreateCategoryDialog() { // Opening Dialog to create category

        // Initializing, declaring and processing Dialog Window to create category

        DialogWindow dialogWindow = new DialogWindow(categoryManagerGridPane.getScene().getWindow(), "This is create category dialog",
                "Create new category", "fxml/addCategoryDialog-view.fxml");
        dialogWindow.prepareDialog(true, true);
        Optional<ButtonType> result = dialogWindow.launchDialogResult();
        FXMLLoader dialogLoader = dialogWindow.getDialogLoader();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            AddCategoryDialogController controller = dialogLoader.getController();
            Category newCategory = controller.processCategory();
            orderData.addCategory(newCategory); // Adding, newly created category, into OrderData instance
            categoriesList.add(newCategory); // Adding to FXCollections' observableArrayList
            categoryManagerListView.getSelectionModel().select(newCategory); // Selecting, on ListView, newly created category

            // Adding category into SQL DB

            try {
                orderData.addCategoryToSqlDatabase(newCategory);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void showEditCategoryDialog() { // Opening Dialog to edit category

        // Initializing, declaring and processing Dialog Window to edit category

        DialogWindow dialogWindow = new DialogWindow(categoryManagerGridPane.getScene().getWindow(), "This is edit category dialog",
                "Change name of category", "fxml/editCategoryDialog-view.fxml");
        dialogWindow.prepareDialog(true, true);
        EditCategoryDialogController controller = dialogWindow.getDialogLoader().getController();
        controller.processOldName(currentlySelectedCategory);
        Optional<ButtonType> result = dialogWindow.launchDialogResult();
        FXMLLoader dialogLoader = dialogWindow.getDialogLoader();

        // Processing results of dialog window

        if((result.isPresent()) && (result.get() == ButtonType.OK)) {
            String newCategoryName = controller.processNewCategoryName();
            currentlySelectedCategory.setCategoryName(newCategoryName);
            categoryManagerListView.refresh(); // Refreshing ListView after change
            categoryManagerListView.getSelectionModel().select(currentlySelectedCategory); // Selecting, on ListView, changed category

            // Changing category in SQL DB

            try {
                orderData.editCategoryInSqlDatabase(currentlySelectedCategory);
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

}
