package com.robertkaptur.orderbuddy;

import org.junit.jupiter.api.*;

class OrderTest {

    private Order order = new Order(
            "Test",
            "Test-Category",
            8.88,
            "Some test description",
            "Some test date of order",
            "Some test date of delivery");
    private Order orderImported = new Order(
            "Test-Imported",
            "Test-Category",
            18.88,
            "Some test description",
            "Some test date of order",
            "Some test date of delivery",
            17031994);

    @Test
    @DisplayName("Testing Order's class object creation")
    void testOrderSetup() {
        Assertions.assertNotEquals(null, order);
    }

    @Test
    @DisplayName("Testing Order's class object fields")
    void testOrderFieldsAfterCreation() {
        Assertions.assertFalse(order.getTitle().isEmpty());
        Assertions.assertFalse(order.getCategory().isEmpty());
        Assertions.assertEquals(8.88, order.getPrice());
        Assertions.assertFalse(order.getDescription().isEmpty());
        Assertions.assertFalse(order.getDateOfOrder().isEmpty());
        Assertions.assertFalse(order.getDateOfDelivery().isEmpty());
    }

    @Test
    @DisplayName("Testing Order's class imported object creation")
    void testImportedOrderSetup() {
        Assertions.assertNotEquals(null, orderImported);
    }

    @Test
    @DisplayName("Testing Order's class imported object fields")
    void testImportedOrderFieldsAfterCreation() {
        Assertions.assertFalse(orderImported.getTitle().isEmpty());
        Assertions.assertFalse(orderImported.getCategory().isEmpty());
        Assertions.assertEquals(18.88, orderImported.getPrice());
        Assertions.assertFalse(orderImported.getDescription().isEmpty());
        Assertions.assertFalse(orderImported.getDateOfOrder().isEmpty());
        Assertions.assertFalse(orderImported.getDateOfDelivery().isEmpty());
        Assertions.assertEquals(17031994, orderImported.getId());
    }

    @Test
    void testIdCounter() {
        Order.setIdCounter(100);
        Assertions.assertEquals(100, Order.getIdCounter());
    }

    @Test
    void testTitle() {
        order.setTitle("Test title here");
        Assertions.assertEquals("Test title here", order.getTitle());
    }

    @Test
    void testId() {
        Assertions.assertEquals(17031994, orderImported.getId());
    }

    @Test
    void testCategory() {
        order.setCategory("Some category");
        Assertions.assertEquals("Some category", order.getCategory());
    }

    @Test
    void testPrice() {
        order.setPrice(27.01);
        Assertions.assertEquals(27.01, order.getPrice());
    }

    @Test
    void testDescription() {
        order.setDescription("Some test description");
        Assertions.assertEquals("Some test description", order.getDescription());
    }

    @Test
    void testDateOfOrder() {
        order.setDateOfOrder("Some test DOO");
        Assertions.assertEquals("Some test DOO", order.getDateOfOrder());
    }

    @Test
    void testDateOfDelivery() {
        order.setDateOfDelivery("Some test DOD");
        Assertions.assertEquals("Some test DOD", order.getDateOfDelivery());
    }
}