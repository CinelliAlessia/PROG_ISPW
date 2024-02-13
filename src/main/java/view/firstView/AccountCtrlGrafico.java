package view.firstView;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import view.firstView.utils.*;

import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

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
    private List<PlaylistBean> userPlaylists = null;
    private ObservableList<PlaylistBean> observableList;
    private SceneController sceneController;

    private static final Logger logger = Logger.getLogger(AccountCtrlGrafico.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }
    public void showUserInfo(){
        usernameText.setText(clientBean.getUsername());
        emailText.setText(clientBean.getEmail());
        List<String> preferences = clientBean.getPreferences();

        // Imposta le CheckBox in base alle preferenze del client
        GenreManager.setCheckList(preferences,checkBoxList);
    }

    public void setAttributes(T clientBean, SceneController sceneController) { // T è una classe che estende ClientBean -> UserBean o SupervisorBean
        this.clientBean = clientBean;
        this.sceneController = sceneController;

        logger.info(STR."GUI Account setAttributes: \{clientBean}");

        // Inizializza i dati nella GUI
        showUserInfo();
        // Recupera e visualizza le playlist dell'utente
        retrivePlaylist();
    }

    /** Recupera tutte le playlist dell'utente */
    public void retrivePlaylist() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Recupera le playlist dell'utente
        userPlaylists = accountCtrlApplicativo.retrivePlaylists(clientBean);

        // Imposto la struttura delle colonne della Table View
        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);
        linkColumn.setCellFactory(_ -> new SingleButtonTableCell());

        observableList = FXCollections.observableArrayList(userPlaylists);
        playlistTable.setItems(observableList);

        // ######################
        // Configura e popola la TableView con le colonne appropriate
        /*
        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);
        TableManager.updateTable(playlistTable, userPlaylists);
        */
        // Aggiungi la colonna con bottoni "Approve" o "Reject" e immagini dinamiche
    }

    /** Gestisce il click sul pulsante Salva */
    @FXML
    public void onSaveClick(ActionEvent event) {
        // Recupera le preferenze aggiornate dalle CheckBox
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);
        System.out.println(STR."GUI ACCOUNT Hai premuto salva \{preferences}");

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
        // Passa alla schermata di caricamento della playlist, passando il bean del client
        //TODO Aggiustare sta cosa che se la playlist bean non viene completata, viene visualizzato campo vuoto
        PlaylistBean playlistBean = new PlaylistBean();
        playlistBean.setEmail(clientBean.getEmail());

        observableList.add(playlistBean);

        sceneController.goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, clientBean, playlistBean);
    }


}
