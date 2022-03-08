package com.robertkaptur.orderbuddy;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderDataTest {

    private Order order = new Order("Test", "Test-Category", 8.88, "Some test description", "Some test date of order", "Some test date of delivery");

    @Test
    void testInstance() {
        Assertions.assertNotEquals(null, OrderData.getInstance());
    }

    //TODO: To be changed into SQL one
//    @Test
//    void testDbFilename() {
//        Assertions.assertEquals("db/db.txt", OrderData.getDbFilename());
//    }

    @Test
    void testListOfOrders() {
        Assertions.assertEquals(FXCollections.observableArrayList().getClass(), OrderData.getInstance().getListOfOrders().getClass());
    }

    @Test
    void testAddOrder() {
        OrderData.getInstance().addOrder(order);
        Assertions.assertTrue(OrderData.getInstance().getListOfOrders().contains(order));
    }

    @Test
    void deleteOrder() {
        OrderData.getInstance().addOrder(order);
        Assertions.assertTrue(OrderData.getInstance().getListOfOrders().contains(order));
        OrderData.getInstance().deleteOrder(order);
        Assertions.assertFalse(OrderData.getInstance().getListOfOrders().contains(order));
    }

    // TODO: Adjust it for SQL test
//    @Test
//    void loadDatabase() {
//        Assertions.assertDoesNotThrow(() -> OrderData.getInstance().loadDatabase());
//    }

    // TODO: Adjust it for SQL test
//    @Test
//    void saveDatabase() {
//        Assertions.assertDoesNotThrow(() -> OrderData.getInstance().saveDatabase());
//    }
}