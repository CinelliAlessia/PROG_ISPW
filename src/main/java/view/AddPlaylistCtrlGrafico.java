package view;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import view.utils.*;

import java.net.URL;
import java.util.*;

public class AddPlaylistCtrlGrafico<T extends ClientBean> implements Initializable {

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

    private T clientBean;

    private List<CheckBox> checkBoxList;
    private SceneController sceneController;

    public void setAttributes(T clientBean, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.clientBean = clientBean;
        this.sceneController = sceneController;

        System.out.println("ADD_CG setUserBean: " + clientBean);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }


    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }

    /** Click sul tasto carica Playlist*/
    @FXML
    public void onUploadClick(ActionEvent event) {

        try{
            PlaylistBean playlistBean = getDate();
            if(playlistBean != null){
                // Invocazione metodo controller Applicativo che in teoria è static
                AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
                addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

                if(clientBean.isSupervisor()){
                    sceneController.textPopUp(event, MessageString.ADDED_PLAYLIST,true);
                }
                else{
                    sceneController.textPopUp(event,MessageString.ADDED_PENDING_PLAYLIST,true);
                }
                System.out.println("ADD GUI PLAYLIST AGGIUNTA");
            }
        } catch (PlaylistLinkAlreadyInUse | LinkIsNotValid e){
            showError(e.getMessage());
        }
    }

    private PlaylistBean getDate() throws LinkIsNotValid {
        String linkPlaylist = link.getText();
        String titolo = title.getText();

        List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

        List<Double> sliderValues = Arrays.asList(
                happySad.getValue(),
                danceChill.getValue(),
                electronicAcoustic.getValue(),
                speakInstrumental.getValue()
        );

        PlaylistBean playlistBean = null;

        //Controllo sui campi vuoti
        if( linkPlaylist.isEmpty() || titolo.isEmpty() ){
            showError("I campi sono vuoti!");
        } else if(genre.isEmpty()) {
            showError("Inserisci almeno un genere musicale!");
        } else {
            playlistBean = new PlaylistBean(clientBean.getEmail(), clientBean.getUsername(), titolo, linkPlaylist, genre, clientBean.isSupervisor(), sliderValues);
            playlistBean.setId("");
        }
        return playlistBean;
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
