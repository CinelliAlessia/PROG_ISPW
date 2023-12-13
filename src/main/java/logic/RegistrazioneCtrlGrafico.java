package logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrazioneCtrlGrafico {

    public  void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/registrazione.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Registrazione");
        stage.setScene(scene);
        stage.show();
    }

}
