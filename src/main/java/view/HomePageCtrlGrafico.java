package view;

import engineering.bean.UserBean;
import engineering.others.FxmlFileName;
import engineering.others.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class HomePageCtrlGrafico{

    @FXML
    private Button manager;
    @FXML
    private Button account;
    @FXML
    private Button addButton;

    private UserBean userBean;

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato nel set user bean: " + userBean + " " + userBean.getEmail() + " " + userBean.isSupervisor());
        System.out.println();
        initialize();
    }

    public void initialize() {
        if(userBean == null){
            account.setText("Registrati");
            addButton.setVisible(false);
            manager.setVisible(false);
        } else {
            account.setText(userBean.getUsername());
            manager.setVisible(userBean.isSupervisor());
            addButton.setVisible(true);
        }
    }

    @FXML
    protected void onAccountClick(ActionEvent event) throws IOException {
        System.out.println("HCG userBean: " + userBean);
        if(userBean == null){ // Utente Guest
            SceneController.getInstance().goToScene(event, FxmlFileName.REGISTRATION_FXML);
        } else { // Utente registrato
            SceneController.getInstance().<AccountCtrlGrafico>goToScene(event, FxmlFileName.ACCOUNT_FXML, userBean);
        }
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<AddPlaylistCtrlGrafico>goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
    }

    public void onManagerClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlFileName.MANAGER_PLAYLIST_FXML);
    }
}
