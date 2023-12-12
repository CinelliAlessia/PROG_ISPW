package logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomePageCtrlGrafico {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/homePage.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }
}
