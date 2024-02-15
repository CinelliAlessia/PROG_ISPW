package view.second.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLoggerConfig {

    private CustomLoggerConfig() {
        // Costruttore privato per evitare l'istanziazione della classe
    }

    public static Logger getLoggerWhite(Class<?> clazz) {
        return getLogger(clazz, "\u001B[97m");
    }

    public static Logger getLoggerRed(Class<?> clazz) {
        return getLogger(clazz, "\u001B[31m");
    }

    private static Logger getLogger(Class<?> clazz, String color) throws SecurityException {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter(color));
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.INFO);

        return logger;
    }

    static class CustomFormatter extends java.util.logging.Formatter {
        private final String color;

        public CustomFormatter(String color) {
            this.color = color;
        }

        @Override
        public String format(java.util.logging.LogRecord logRecord) {
            // Personalizza il formato del log come desideri
            // Usa il colore corrente
            return color + logRecord.getMessage() + "\u001B[0m\n";
        }
    }
}
