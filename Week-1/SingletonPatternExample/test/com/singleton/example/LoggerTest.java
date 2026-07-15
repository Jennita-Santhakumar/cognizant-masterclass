package com.singleton.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    void getInstance_returnsSameObjectEveryTime() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2, "Singleton must return the same instance");
    }

    @Test
    void log_appendsMessageToSharedHistory() {
        Logger logger = Logger.getInstance();
        logger.log("test message");
        assertTrue(logger.getLogHistory().contains("test message"));
    }

    @Test
    void state_persistsAcrossReferences() {
        Logger a = Logger.getInstance();
        a.log("written via a");
        Logger b = Logger.getInstance();
        assertTrue(b.getLogHistory().contains("written via a"),
            "State written through one reference must be visible through another");
    }
}
