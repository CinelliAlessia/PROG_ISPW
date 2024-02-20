package view.first;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;

import engineering.others.Printer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import view.first.utils.*;

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

    private ObservableList<PlaylistBean> observableList;
    private T clientBean;
    private PlaylistBean playlistBean;
    private SceneController sceneController;

    public void setAttributes(T clientBean, ObservableList<PlaylistBean> observableList, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.clientBean = clientBean;
        this.sceneController = sceneController;
        this.observableList = observableList;
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
            getDate();

            if(playlistBean != null){
                Printer.logPrint(String.format("GUI AddPlaylist: upload Playlist: %s, nome: %s, genre: %s, emotional: %s", playlistBean, playlistBean.getPlaylistName(), playlistBean.getPlaylistGenre(), playlistBean.getEmotional()));

                // Invocazione metodo controller Applicativo che in teoria è static
                AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
                addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

                if(clientBean.isSupervisor()){
                    sceneController.textPopUp(event, MessageString.ADDED_PLAYLIST,true);
                }
                else{
                    sceneController.textPopUp(event,MessageString.ADDED_PENDING_PLAYLIST,true);
                }
                Printer.logPrint("GUI AddPlaylist: Playlist Aggiunta");
            }
        } catch (PlaylistLinkAlreadyInUseException | LinkIsNotValidException | PlaylistNameAlreadyInUseException e){
            showError(e.getMessage());
        }
    }

    private void getDate() throws LinkIsNotValidException {

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
            showError("The fields are empty!");
        } else if(genre.isEmpty()) {
            showError("Enter at least one musical genre!");
        } else {
            playlistBean = new PlaylistBean(clientBean.getEmail(), clientBean.getUsername(), titolo, linkPlaylist, genre, clientBean.isSupervisor(), sliderValues);
            playlistBean.setId("");

            // Utilizzato esclusivamente per aggiornare la tabella nell'account utente
            if(observableList != null){ // è null solo se l'utente ha cliccato add Button da Home Page
                observableList.add(playlistBean); //Aggiungi
            }
        }
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
