package start;

import view.first.utils.FxmlFileName;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication2 extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FxmlFileName.LOGIN_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource(FxmlFileName.LOGIN_FXML));
        Scene scene2 = new Scene(fxmlLoader2.load());
        stage2.setResizable(false);
        stage2.setScene(scene2);
        stage2.show();
    }

    public static void main(String[] args) {
        launch();
    }
}