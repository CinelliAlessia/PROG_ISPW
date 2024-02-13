package view;

import engineering.bean.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.utils.*;

import java.net.URL;
import java.util.*;

public class FilterCtrlGrafico<T extends ClientBean> implements Initializable {

    @FXML
    private Slider happySad;
    @FXML
    private Slider danceChill;
    @FXML
    private Slider electronicAcoustic;
    @FXML
    private Slider speakInstrumental;

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

    private PlaylistBean playlistBean;

    private List<CheckBox> checkBoxList;
    private SceneController sceneController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    /** Viene utilizzata da sceneController per impostare lo userBean e l'istanza di Scene controller da utilizzare */
    public void setAttributes(PlaylistBean playlistBean, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
        this.playlistBean = playlistBean;
        setData();
    }

    @FXML
    private void onApplyClick(ActionEvent event) {
        // Devo modificare i campi del PlaylistBean
        List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

        List<Double> sliderValues = Arrays.asList(
                happySad.getValue(),
                danceChill.getValue(),
                electronicAcoustic.getValue(),
                speakInstrumental.getValue());

        playlistBean.setPlaylistGenre(genre);
        playlistBean.setEmotional(sliderValues);

        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setData(){
        List<String> preferences = playlistBean.getPlaylistGenre();
        List<Double> emotional = playlistBean.getEmotional();

        System.out.println("Filter GUI setData: " + playlistBean.getPlaylistGenre() + " " + playlistBean.getEmotional());

        if(preferences != null){
            GenreManager.setCheckList(preferences,checkBoxList);
        }

        if(emotional != null){
            happySad.setValue(playlistBean.getEmotional().get(0));
            danceChill.setValue(playlistBean.getEmotional().get(1));
            electronicAcoustic.setValue(playlistBean.getEmotional().get(2));
            speakInstrumental.setValue(playlistBean.getEmotional().get(3));
        } else {
            happySad.setValue(0.0);
            danceChill.setValue(0.0);
            electronicAcoustic.setValue(0.0);
            speakInstrumental.setValue(0.0);
        }


    }
    @FXML
    private void onBackClick(ActionEvent event) {
        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onResetClick() {
        List<String> genre = new ArrayList<>();
        List<Double> emotional = new ArrayList<>(4);

        // Aggiungi valori alla lista
        emotional.add(0.0);
        emotional.add(0.0);
        emotional.add(0.0);
        emotional.add(0.0);

        playlistBean.setEmotional(emotional);
        playlistBean.setPlaylistGenre(genre);

        setData();
    }

}
