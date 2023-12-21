module logic {
    requires mongo.java.driver;
    requires javafx.controls;
    requires javafx.fxml;


    exports controllerApplicativo;
    opens controllerApplicativo to javafx.fxml;
    exports engineering;
    opens engineering to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
    exports start;
    opens start to javafx.fxml;
}