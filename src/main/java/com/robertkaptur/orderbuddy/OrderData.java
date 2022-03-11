package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class OrderData {

    // Final fields
    private final static OrderData instance = new OrderData(); // Creating one, static, instance of OrderData
    private final static String dbDir = "db";
    private final static String dbName = "database.db";

    private final ObservableList<Order> listOfOrders = FXCollections.observableArrayList(); // Declaring list of orders as JavaFX's observableArrayList
    private final ObservableList<Category> listOfCategories = FXCollections.observableArrayList(); // Declaring list of categories as JavaFX's observableArrayList

    private final String dbLocation = dbDir + "/" + dbName; // This will go to resources -> db -> db file
    private final Path dbPath = Paths.get(dbLocation);
    private final String dbUrl = "jdbc:sqlite:" + dbPath.toString();

    // SQL
    private final static String QUERY_CREATE_TABLE_ORDERS = "CREATE TABLE \"orders\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"title\"\tTEXT NOT NULL,\n" +
            "\t\"id_category\"\tINTEGER NOT NULL,\n" +
            "\t\"price\"\tNUMERIC NOT NULL,\n" +
            "\t\"description\"\tTEXT NOT NULL,\n" +
            "\t\"date_of_order\"\tTEXT NOT NULL,\n" +
            "\t\"date_of_delivery\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ")";
    private final static String QUERY_CREATE_TABLE_CATEGORIES = "CREATE TABLE \"categories\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"category_name\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\")\n" +
            ")";
    private final static String QUERY_SELECT_ALL_ORDERS = "SELECT orders.id, orders.title, categories.category_name, orders.price, orders.description, orders.date_of_order, orders.date_of_delivery\n" +
            "FROM orders\n" +
            "LEFT JOIN categories ON orders.id_category=categories.id";
    private final static String QUERY_SELECT_ALL_CATEGORIES = "SELECT id, category_name\n" +
            "FROM categories";

    // Constructor

    private OrderData() { // Constructor (private) - For Singleton purposes, to ensure that no second OrderData instance will be created
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // TODO: Check how it can be utilized in the future (I don't remember idea behind it)

        createNewDatabase();
    }

    // Getters

    public static OrderData getInstance() {
        return instance;
    }

    public ObservableList<Order> getListOfOrders() {
        return listOfOrders;
    }

    public ObservableList<Category> getListOfCategories() {
        return listOfCategories;
    }

    // Operations on OrderData

    public void addOrder(Order order) {
        listOfOrders.add(order);
    }

    public void deleteOrder(Order order) {
        listOfOrders.remove(order);
    }

    public void addCategory(Category category) {
        listOfCategories.add(category);
    }

    public void deleteCategory(Category category) {
        listOfCategories.remove(category);
    }

    // SQL methods

    private void createNewDatabase() {
        // First - Checks whether database exists, if not - creates new one
        // Then - Checks whether tables exist, if not - creating new ones

        try (Connection connection = DriverManager.getConnection(dbUrl)){
            if (connection != null) {
                Statement statement = connection.createStatement();
                if(!checkExistingTable("orders", connection)) {
                    statement.execute(QUERY_CREATE_TABLE_ORDERS);
                    System.out.println("Missing table orders - Creating new one");
                } else {
                    System.out.println("Not creating table orders - It already exists");
                }
                if(!checkExistingTable("categories", connection)) {
                    statement.execute(QUERY_CREATE_TABLE_CATEGORIES);
                    System.out.println("Missing table categories - Creating new one");
                } else {
                    System.out.println("Not creating table categories - It already exists");
                }
                statement.close();
            } else {
                System.out.println("Error with loading SQL database");
            }
        } catch (SQLException e) {
            System.out.println("Error with db connection");
            e.printStackTrace();
        }
    }

    public void loadSqlDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet;
                if(checkExistingTable("orders", connection)) {
                    resultSet = statement.executeQuery(QUERY_SELECT_ALL_ORDERS);
                    while (resultSet.next()) {
                        Order importedOrder = new Order(resultSet.getString("title"),
                                resultSet.getString("category_name"), resultSet.getDouble("price"),
                                resultSet.getString("description"), resultSet.getString("date_of_order"),
                                resultSet.getString("date_of_delivery"), resultSet.getInt("id"));
                        addOrder(importedOrder);
                    }
                } else {
                    System.out.println("Missing table orders");
                }
                if(checkExistingTable("categories", connection)) {
                    resultSet = statement.executeQuery(QUERY_SELECT_ALL_CATEGORIES);
                    while (resultSet.next()) {
                        Category importedCategory = new Category(resultSet.getInt("id"), resultSet.getString("category_name"));
                        addCategory(importedCategory);
                    }
                    resultSet.close();
                } else {
                    System.out.println("Missing table categories");
                }
                statement.close();
            } else {
                System.out.println("Error with loading SQL database");
            }
        } catch (SQLException e) {
            System.out.println("Error with db connection");
            e.printStackTrace();
        }
    }

    public void addOrderToSqlDatabase(Order newOrder, Category selectedCategory) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryAddOrder = "INSERT into orders VALUES (" + newOrder.getId() + ", '" + newOrder.getTitle() + "', " +
                        selectedCategory.getId() + ", " + newOrder.getPrice() + ", '" + newOrder.getDescription() +
                        "', '" + newOrder.getDateOfOrder() + "', '" + newOrder.getDateOfDelivery() + "');";
                statement.executeUpdate(queryAddOrder);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    public void editOrderInSqlDatabase(Order changedOrder, Category selectedCategory) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryEditOrder = "UPDATE orders SET title = '" + changedOrder.getTitle() + "', id_category = " + selectedCategory.getId()
                        + ", price = " + changedOrder.getPrice() + ", description = '" + changedOrder.getDescription()
                        + "', date_of_order = '" + changedOrder.getDateOfOrder() + "', date_of_delivery = '"
                        + changedOrder.getDateOfDelivery() + "' WHERE id = " + changedOrder.getId() + ";";
                statement.executeUpdate(queryEditOrder);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    public void deleteOrderFromSqlDatabase(Order deletedOrder) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryDeleteOrder = "DELETE FROM orders WHERE orders.id = " + deletedOrder.getId();
                statement.executeUpdate(queryDeleteOrder);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    public void addCategoryToSqlDatabase(Category newCategory) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryAddCategory = "INSERT into categories VALUES (" + newCategory.getId() + ", '" + newCategory.getCategoryName() + "');";
                statement.executeUpdate(queryAddCategory);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    public void editCategoryInSqlDatabase(Category changedCategory) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryEditCategory = "UPDATE categories SET category_name = '" + changedCategory.getCategoryName() + "' WHERE id = " + changedCategory.getId() + ";";
                statement.executeUpdate(queryEditCategory);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    public void deleteCategoryFromSqlDatabase(Category deletedCategory) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                String queryDeleteCategory = "DELETE FROM categories WHERE categories.id = " + deletedCategory.getId();
                statement.executeUpdate(queryDeleteCategory);
                statement.close();

            } else {
                System.out.println("Cannot connect to sql db");
            }
        }
    }

    private boolean checkExistingTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);

        return resultSet.next();
    }
}
