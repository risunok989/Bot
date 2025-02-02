package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    private static final Logger logger = LogManager.getLogger(LogTest.class);

    public static void main(String[] args) {
        logger.info("Тест логирования");
        logger.error("Тест ошибки", new RuntimeException("Test exception"));
    }
}