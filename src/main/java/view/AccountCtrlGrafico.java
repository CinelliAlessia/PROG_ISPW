package view;

import controllerApplicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountCtrlGrafico{

    @FXML
    public Button saveButton;

    @FXML
    private Label usernameText, supervisorText, emailText;

    @FXML
    private Button back, addButton;

    @FXML
    private CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz,
            acoustic, reb, country, alternative;

    public UserBean userBean;

    public void setUserBean(UserBean user) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + userBean);
        initializeData(userBean);
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        System.out.println("ACG in inizializeData: " + userBean);

        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        String username = userBean.getUsername();

        System.out.println(usernameText.getText());

        usernameText.setText(username);

        supervisorText.setText("FALSE");
        emailText.setText(userBean.getEmail());

        ArrayList<String> preferences = userBean.getPreferences();

        pop.setSelected(preferences.contains("Pop"));
        indie.setSelected(preferences.contains("Indie"));
        classic.setSelected(preferences.contains("Classic"));
        rock.setSelected(preferences.contains("Rock"));
        electronic.setSelected(preferences.contains("Electronic"));
        house.setSelected(preferences.contains("House"));
        hipHop.setSelected(preferences.contains("Hip Hop"));
        jazz.setSelected(preferences.contains("Jazz"));
        acoustic.setSelected(preferences.contains("Acoustic"));
        reb.setSelected(preferences.contains("REB"));
        country.setSelected(preferences.contains("Country"));
        alternative.setSelected(preferences.contains("Alternative"));
    }

    public void retrivePlaylist() throws SQLException {

        ArrayList<PlaylistBean> playlistsBean = AddPlaylistCtrlApplicativo.retriveList();
    }

    @FXML
    public void onSaveClick(ActionEvent event) {

    }
    @FXML
    public void onBackClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goBack(event);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlName.UPLOAD_PLAYLIST_FXML, userBean);
        System.out.println("ACG userBean: " + userBean);
    }
}
