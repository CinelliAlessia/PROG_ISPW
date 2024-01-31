package view;

import engineering.bean.UserBean;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class HomePageCtrlGrafico {

    @FXML
    private Button searchButton;

    private UserBean userBean;

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato user bean: " + userBean);
    }

    @FXML
    protected void onAccountClick(ActionEvent event) throws IOException {
        System.out.println("HCG userBean: " + userBean);
        SceneController.getInstance().<AccountCtrlGrafico>goToScene(event, FxmlName.ACCOUNT_FXML, userBean);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<AddPlaylistCtrlGrafico>goToScene(event, FxmlName.UPLOAD_PLAYLIST_FXML, userBean);
    }
}
