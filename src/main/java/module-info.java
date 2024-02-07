module logic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires commons.validator;

    exports controller.applicativo;
    opens controller.applicativo to javafx.fxml;

    exports view;
    opens view to javafx.fxml;

    exports engineering.dao;
    opens engineering.dao to javafx.fxml;

    exports engineering.exceptions;
    opens engineering.exceptions to javafx.fxml;

    exports start;
    opens start to javafx.fxml;

    exports engineering.bean;
    opens engineering.bean to javafx.fxml;

    exports model; // Esporta il pacchetto model per consentire l'accesso ai campi della classe User
    opens model to com.google.gson;

    exports engineering.pattern.observer;
    opens engineering.pattern.observer to com.google.gson;

    exports engineering.others;
    opens engineering.others to javafx.fxml;

    exports view.utils;
    opens view.utils to javafx.fxml; // Apri il pacchetto model al modulo com.google.gson

}