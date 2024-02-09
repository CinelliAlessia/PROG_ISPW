package view;

import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.utils.GenreManager;
import view.utils.SceneController;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FilterCtrlGrafico implements Initializable {
    @FXML
    private Slider happySad;
    @FXML
    private Slider danceChill;
    @FXML
    private Slider electronicAcoustic;
    @FXML
    private Slider speakInstrumental;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField title;
    @FXML
    private TextField link;
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

    private PlaylistBean playlistBean;

    private List<CheckBox> checkBoxList;
    private SceneController sceneController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);

    }

    /** Viene utilizzata da sceneController per impostare lo userBean e l'istanza di Scene controller da utilizzare */
    public void setAttributes(UserBean user, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        this.sceneController = sceneController;

        GenreManager.setCheckList(userBean.getPreferences(),checkBoxList);

        System.out.println("Filter GUI setAttributes: " + userBean);
    }

    public void setPlaylistBean(PlaylistBean playlistBean) {
        this.playlistBean = playlistBean;
    }

    @FXML
    private void onApplyClick(ActionEvent event) {
        // Devo modificare i campi del PlaylistBean
        List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

        List<Double> sliderValues = Arrays.asList(
                electronicAcoustic.getValue(),
                speakInstrumental.getValue(),
                happySad.getValue(),
                danceChill.getValue()
        );
        playlistBean.setPlaylistGenre(genre);
        playlistBean.setEmotional(sliderValues);

        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onBackClick(ActionEvent actionEvent) {

    }
}
