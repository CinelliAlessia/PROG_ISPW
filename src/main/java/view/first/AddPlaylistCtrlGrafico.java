package view.first;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import view.first.utils.*;

import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

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
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    private List<CheckBox> checkBoxList;

    private PlaylistBean playlistBean;
    private T clientBean;
    private SceneController sceneController;

    private static final Logger logger = Logger.getLogger(AddPlaylistCtrlGrafico.class.getName());


    public void setAttributes(T clientBean, PlaylistBean playlistBean, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.clientBean = clientBean;
        this.sceneController = sceneController;
        this.playlistBean = playlistBean;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        playlistBean = null;
        sceneController.goBack(event);
    }

    /** Click sul tasto carica Playlist*/
    @FXML
    public void onUploadClick(ActionEvent event) {

        try{
            getDate();

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
                logger.info("ADD GUI: PLAYLIST AGGIUNTA");
            }
        } catch (PlaylistLinkAlreadyInUse | LinkIsNotValid e){
            showError(e.getMessage());
        }
    }

    private void getDate() throws LinkIsNotValid {
        String linkPlaylist = link.getText();
        String titolo = title.getText();

        List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

        List<Integer> sliderValues = List.of(
                (int) happySad.getValue(),
                (int) danceChill.getValue(),
                (int) electronicAcoustic.getValue(),
                (int) speakInstrumental.getValue()
        );

        //Controllo sui campi vuoti
        if( linkPlaylist.isEmpty() || titolo.isEmpty() ){
            showError("I campi sono vuoti!");
        } else if(genre.isEmpty()) {
            showError("Inserisci almeno un genere musicale!");
        } else {
            playlistBean.setEmail(clientBean.getEmail());
            playlistBean.setUsername(clientBean.getUsername());
            playlistBean.setPlaylistName(titolo);
            playlistBean.setLink(linkPlaylist);
            playlistBean.setPlaylistGenre(genre);
            playlistBean.setApproved(clientBean.isSupervisor());
            playlistBean.setEmotional(sliderValues);
            playlistBean.setId("");
        }
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
