package view;

import engineering.bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;

public class AccountCtrlGrafico {

    public Label usernameText;
    @FXML
    private Button back, addButton;

    private UserBean userBean;

    public void initialize() {
        // Deve avere un userBean per compilare tutte le informazioni
    }

    public void start(Stage stage, UserBean user) throws IOException {
        userBean = user;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/account.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("My Account");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void onSaveClick() {
    }

    @FXML
    public void onBackClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        HomePageCtrlGrafico home = new HomePageCtrlGrafico();
        home.start(stage,userBean);
    }


    @FXML
    public void addPlaylistClick() throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        AddPlaylistCtrlGrafico addPlaylist = new AddPlaylistCtrlGrafico();
        addPlaylist.start(stage,userBean);
    }
}
