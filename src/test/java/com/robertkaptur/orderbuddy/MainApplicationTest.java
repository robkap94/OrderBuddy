package com.robertkaptur.orderbuddy;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainApplicationTest {

    @Test
    void testLaunchWindows() {
        Stage testStage = new Stage();
        Assertions.assertDoesNotThrow(() -> MainApplication.launchWindows(testStage));
    }
}