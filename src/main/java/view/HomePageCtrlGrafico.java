package view;

import engineering.bean.UserBean;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageCtrlGrafico {

    @FXML
    private Button account, addButton;

    private UserBean userBean;

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato user bean: " + userBean);
    }

    @FXML
    protected void onAccountClick(ActionEvent event) throws IOException {

        System.out.println("HCG userBean: " + userBean);

        SceneController.getInstance().<AccountCtrlGrafico>goToScene(event, FxmlName.ACCOUNT_FXML,userBean);

    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<AddPlaylistCtrlGrafico>goToScene(event, FxmlName.UPLOAD_PLAYLIST_FXML,userBean);
    }
}
