package start;

import engineering.others.FxmlName;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import view.LoginCtrlGrafico;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FxmlName.LOGIN_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Access Login");
        //stage.setResizable(false); //#######################
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}