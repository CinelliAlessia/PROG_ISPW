package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;

public class AddPlaylistCtrlGrafico {


    @FXML
    private Button back, account;
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/caricaPlaylist.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Carica Playlist");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAccountClick() throws IOException { // Non devo fa controlli
        Stage stage = (Stage) account.getScene().getWindow();
        AccountCtrlGrafico accountCGUI = new AccountCtrlGrafico();
        accountCGUI.start(stage);
    }

    @FXML
    public void onBackClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        HomePageCtrlGrafico home = new HomePageCtrlGrafico();
        home.start(stage);
    }

    @FXML
    public void onUploadClick() {
    }
}
