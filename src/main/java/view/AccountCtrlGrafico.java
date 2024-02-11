package view;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import view.utils.*;

import java.net.URL;
import java.util.*;

public class AccountCtrlGrafico<T extends ClientBean> implements Initializable {

    @FXML
    public Button saveButton;
    @FXML
    private Label usernameText;
    @FXML
    private Label emailText;

    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> playlistNameColumn;
    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, Boolean> approveColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;

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
    private List<PlaylistBean> playlistsUser = null;
    private SceneController sceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    public void setAttributes(T clientBean, SceneController sceneController) { // T è una classe che estende ClientBean -> UserBean o SupervisorBean
        this.clientBean = clientBean;
        this.sceneController = sceneController;

        System.out.println("GUI Account setAttributes: " + clientBean);
        System.out.println(clientBean.getEmail()+ " " + clientBean.getUsername() +" "+clientBean.getPreferences());

        // Inizializza i dati nella GUI
        initializeData();
        // Recupera e visualizza le playlist dell'utente
        retrivePlaylist();
    }

    /** Inizializza i dati della GUI con le informazioni del client */
    public void initializeData(){
        usernameText.setText(clientBean.getUsername());
        emailText.setText(clientBean.getEmail());
        List<String> preferences = clientBean.getPreferences();
        // Imposta le CheckBox in base alle preferenze del client
        GenreManager.setCheckList(preferences,checkBoxList);
    }

    /** Recupera tutte le playlist dell'utente */
    public void retrivePlaylist() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Recupera le playlist dell'utente
        playlistsUser = accountCtrlApplicativo.retrivePlaylists(clientBean);

        // Configura e popola la TableView con le colonne appropriate
        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsUser);

        // Aggiungi la colonna con bottoni "Approve" o "Reject" e immagini dinamiche
    }

    /** Gestisce il click sul pulsante Salva */
    @FXML
    public void onSaveClick(ActionEvent event) {
        // Recupera le preferenze aggiornate dalle CheckBox
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);
        System.out.println("GUI ACCOUNT Hai premuto salva " + preferences);

        // Aggiorna le preferenze nel bean del cliente
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Aggiorna le preferenze nel backend
        accountCtrlApplicativo.updateGenreUser(clientBean);

        // Mostra una notifica pop-up
        sceneController.textPopUp(event, MessageString.UPDATED_PREFERNCES, false);
    }

    /** Gestisce il click sul pulsante Indietro */
    @FXML
    public void onBackClick(ActionEvent event) {
        // Torna alla schermata precedente
        sceneController.goBack(event);
    }

    /** Gestisce il click sul pulsante Aggiungi Playlist */
    @FXML
    public void addPlaylistClick(ActionEvent event) {
        // Passa alla schermata di caricamento della playlist, passando il bean del cliente
        sceneController.goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, clientBean);
    }


}
