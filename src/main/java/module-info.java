module logic {
    requires mongo.java.driver;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports controllerApplicativo;
    opens controllerApplicativo to javafx.fxml;

    exports view;
    opens view to javafx.fxml;

    exports engineering.dao;
    opens engineering.dao to javafx.fxml;

    exports start;
    opens start to javafx.fxml;

    exports engineering.bean;
    opens engineering.bean to javafx.fxml;

    exports model; // Esporta il pacchetto model per consentire l'accesso ai campi della classe User
    opens model to com.google.gson; // Apri il pacchetto model al modulo com.google.gson

}