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


    @Test
    void loadDatabase() {
        Assertions.assertDoesNotThrow(() -> OrderData.getInstance().loadSqlDatabase());
    }


    @Test
    void saveDatabase() {
        Order testOrder = new Order("Test order", "1", 6.54, "test description, order created by jUnit", "some test date of start", "some test date of delivery");
        Assertions.assertDoesNotThrow(() -> OrderData.getInstance().addOrderToSqlDatabase(testOrder));
    }
}