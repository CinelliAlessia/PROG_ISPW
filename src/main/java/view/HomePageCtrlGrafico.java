package view;

import engineering.bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;

public class HomePageCtrlGrafico {

    @FXML
    private Button account, addButton;

    private UserBean userBean;

    public void start(Stage stage, UserBean user) throws IOException {
        userBean = user;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Home Page");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void onAccountClick() throws IOException { // Non devo fa controlli
        Stage stage = (Stage) account.getScene().getWindow();
        AccountCtrlGrafico accountCGUI = new AccountCtrlGrafico();
        accountCGUI.start(stage, userBean);
    }

    @FXML
    public void addPlaylistClick() throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        AddPlaylistCtrlGrafico addPlaylist = new AddPlaylistCtrlGrafico();
        addPlaylist.start(stage, userBean);
    }
}
