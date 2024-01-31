package view;

import controllerApplicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccountCtrlGrafico{

    @FXML
    public Button saveButton;

    @FXML
    private Label usernameText;
    @FXML
    private Label supervisorText;
    @FXML
    private Label emailText;
    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;

    private UserBean userBean;

    public void setUserBean(UserBean user) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + userBean);
        initializeData(userBean);
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        System.out.println("ACG in initializeData: " + userBean);

        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        String username = userBean.getUsername();

        System.out.println(usernameText.getText());

        usernameText.setText(username);

        supervisorText.setText("FALSE");
        emailText.setText(userBean.getEmail());

        List<String> preferences = userBean.getPreferences();

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

    public void retrivePlaylist() throws SQLException, IOException {
        List<PlaylistBean> playlistsBean = AddPlaylistCtrlApplicativo.retriveList();
        // TODO document why this method is empty
    }

    @FXML
    public void onSaveClick(ActionEvent event) {
        // TODO document why this method is empty
    }
    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlName.UPLOAD_PLAYLIST_FXML, userBean);
        System.out.println("ACG userBean: " + userBean);
    }
}
