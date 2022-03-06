package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
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
    private final static String dbFile = "db.txt";
    private final static String dbName = "database.db";
    private final static String dbFilename = dbDir + "/" + dbFile; // This will go to resources -> db -> db file
    private final static Path path = Paths.get(dbFilename);

    private final DecimalFormat decimalFormatter = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US)); // Utilized during saving db
    private final ObservableList<Order> listOfOrders = FXCollections.observableArrayList(); // Declaring list of orders as JavaFX's observableArrayList

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

    private OrderData() { // Constructor (private) - For Singleton purposes, to ensure that no second OrderData instance will be created
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // TODO: Check how it can be utilized in the future (I don't remember idea behind it)

        createNewDatabase();
    }

    // Getters

    public static OrderData getInstance() {
        return instance;
    }

    public static String getDbFilename() {
        return dbFilename;
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

    public void loadDatabase() throws IOException { // Method to load db

        if (Files.exists(path)) { // Check whether db file exists



            try (BufferedReader bufferedReader = Files.newBufferedReader(path)) { // Creating Buffer by try-with-resources
                String orderInput;

                while ((orderInput = bufferedReader.readLine()) != null) { // Opening Buffer
                    String[] orderArgs = orderInput.split("\t"); // tab splits arguments of an object

                    int id = Integer.parseInt(orderArgs[0]);
                    String title = orderArgs[1];
                    String category = orderArgs[2];
                    double price = Double.parseDouble(orderArgs[3]);
                    String description = orderArgs[4];
                    String dateOfOrder = orderArgs[5];
                    String dateOfDelivery = orderArgs[6];

                    Order importedOrder = new Order(title, category, price, description, dateOfOrder, dateOfDelivery, id);
                    addOrder(importedOrder); // Adding order into OrderData's list (observableArrayList), which will be utilized by ListView
                }
            }

        } else {
            // TODO: Create error pop-up to inform that db file has not been found
        }

    }

    public void saveDatabase() throws IOException { // Method to save db

        try { // Check whether dir exists
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) { // Creating Buffer by try-with-resources
            for (Order order : listOfOrders) {
                bufferedWriter.write(String.format("%d\t%s\t%s\t%s\t%s\t%s\t%s", // Opening Buffer + String format of saving file in db
                        order.getId(),
                        order.getTitle(),
                        order.getCategory(),
                        decimalFormatter.format(order.getPrice()), // price is saved, as a string, with decFormatter (X.XX)
                        order.getDescription(),
                        // TODO: Ensure to change later these two below %s into actual date-format (Maybe it can be also saved like string but with format?
                        order.getDateOfOrder(),
                        order.getDateOfDelivery()));
                bufferedWriter.newLine(); // Ending line, for particular object, and starting line for new object found by iterator in list of orders
            }
        }
    }

    private void createNewDatabase() {
        // Checks if database exists, if not - creates new one

        String dbLocation = dbDir + "/" + dbName; // This will go to resources -> db -> db file
        Path dbPath = Paths.get(dbLocation);
        String dbUrl = "jdbc:sqlite:" + dbPath.toString();

        System.out.println(dbUrl); // TODO: Delete after tests
        // Checking whether tables exist, if not - creating new ones

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
                    System.out.println("Not creating table orders - It already exists");
                }
            } else {
                System.out.println("Error with loading SQL database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkExistingTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);

        return resultSet.next();
    }
}
