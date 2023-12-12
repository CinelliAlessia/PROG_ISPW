package com.example.spotify;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrazioneCtrlGrafico {

    public  void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("registrazione.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1080, 700);
        stage.setTitle("Access Login");
        stage.setScene(scene);
        stage.show();
    }

}
