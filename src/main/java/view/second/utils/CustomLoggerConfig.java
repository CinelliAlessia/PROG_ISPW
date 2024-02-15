package view.second.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class CustomLoggerConfig {

    private CustomLoggerConfig() {
        // Costruttore privato per evitare l'istanziazione della classe
    }

    public static Logger getLoggerWhite(Class<?> clazz) {
        return getLogger(clazz, "\u001B[97m");
    }

    private static Logger getLogger(Class<?> clazz, String color) {
        try{
            Logger logger = Logger.getLogger(clazz.getName());
            logger.setUseParentHandlers(false); // Rimuovi il gestore predefinito

            // Aggiungi il tuo gestore personalizzato
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new CustomFormatter(color));
            logger.addHandler(consoleHandler);

            return logger;
        } catch (SecurityException e){
            e.fillInStackTrace();
            return null;
        }

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
