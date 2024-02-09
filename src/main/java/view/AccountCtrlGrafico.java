package view;

import controller.applicativo.*;
import engineering.bean.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import view.utils.*;

import java.util.*;
import java.net.URL;
public class AccountCtrlGrafico implements Initializable {

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

    private UserBean userBean;

    private List<CheckBox> checkBoxList;
    private List<PlaylistBean> playlistsUser = null;

    private SceneController sceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    public void setAttributes(UserBean user, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        this.sceneController = sceneController;

        System.out.println("GUI Account setAttributes: " + userBean);
        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        initializeData(userBean);
        retrivePlaylist();
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        usernameText.setText(userBean.getUsername());
        emailText.setText(userBean.getEmail());
        List<String> preferences = userBean.getPreferences();

        GenreManager.setCheckList(preferences,checkBoxList);
    }

    /** Recupera tutte le playlist dell'utente */
    public void retrivePlaylist() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        playlistsUser = accountCtrlApplicativo.retrivePlaylists(userBean);

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsUser);

        // Aggiungi la colonna con bottoni "Approve" o "Reject" e immagini dinamiche
    }

    @FXML
    public void onSaveClick(ActionEvent event) {

        // Recupero preferenze aggiornate
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);
        System.out.println("GUI ACCOUNT Hai premuto salva " + preferences);

        // Imposto le preferenze sullo user bean
        userBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        accountCtrlApplicativo.updateGenreUser(userBean);

        // Devo aggiornare il bean? Di tutto lo stack però,
        // non va bene, tutti dovrebbero recuperare informazioni per il bean dalla persistenza
        // ##### Mostro pop-up ######

        sceneController.textPopUp(event, MessageString.UPDATED_PREFERNCES,false);

    }
    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) {
        sceneController.goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
    }


}
