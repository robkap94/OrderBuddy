package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OrderData {

    // Final fields
    private final static OrderData instance = new OrderData(); // Creating one, static, instance of OrderData
    private final static String dbDir = "db";
    private final static String dbName = "database.db";

    private final DecimalFormat decimalFormatter = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US)); // Utilized during saving db
    private final ObservableList<Order> listOfOrders = FXCollections.observableArrayList(); // Declaring list of orders as JavaFX's observableArrayList

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

    // Operations on OrderData

    public void addOrder(Order order) {
        listOfOrders.add(order);
    }

    public void deleteOrder(Order order) {
        listOfOrders.remove(order);
    }

    // SQL methods

    private void createNewDatabase() {
        // Checks whether database exists, if not - creates new one
        // Checks whether tables exist, if not - creating new ones

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
                        // TODO: addCategory() method to add category into observable list (like in orders above) - Should be handled in feature #52
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

    public void addOrderToSqlDatabase(Order newOrder) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                // TODO: Change "category" int into String as soon as it will be changed into dropdown list (Temporary due to sql db structure (id_category)
                String queryAddOrder = "INSERT into orders VALUES (" + newOrder.getId() + ", '" + newOrder.getTitle() + "', " + Integer.parseInt(newOrder.getCategory())
                        + ", " + newOrder.getPrice() + ", '" + newOrder.getDescription() + "', '" + newOrder.getDateOfOrder() + "', '" + newOrder.getDateOfDelivery() + "');";
                statement.executeUpdate(queryAddOrder);
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
