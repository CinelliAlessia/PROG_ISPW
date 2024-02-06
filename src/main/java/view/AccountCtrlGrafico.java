package view;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.others.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.*;
import java.io.IOException;
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
    private TableColumn<PlaylistBean, String> approveColumn;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    public void setUserBean(UserBean user) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + userBean);
        initializeData(userBean);
        retrivePlaylist();
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        System.out.println("ACG in initializeData: " + userBean);
        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        usernameText.setText(userBean.getUsername());
        emailText.setText(userBean.getEmail());

        List<String> preferences = userBean.getPreferences();

        pop.setSelected(preferences.contains("Pop"));
        indie.setSelected(preferences.contains("Indie"));
        classic.setSelected(preferences.contains("Classic"));
        rock.setSelected(preferences.contains("Rock"));

        electronic.setSelected(preferences.contains("Electronic"));
        house.setSelected(preferences.contains("House"));
        hipHop.setSelected(preferences.contains("HipHop"));
        jazz.setSelected(preferences.contains("Jazz"));

        acoustic.setSelected(preferences.contains("Acoustic"));
        reb.setSelected(preferences.contains("REB"));
        country.setSelected(preferences.contains("Country"));
        alternative.setSelected(preferences.contains("Alternative"));
    }

    public void retrivePlaylist() {
        // Recupera tutte le playlist
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        playlistsUser = accountCtrlApplicativo.retrivePlaylists(userBean);

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, approveColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "approved", "playlistGenre");
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsUser, genreColumn);

    }

    @FXML
    public void onSaveClick(ActionEvent event) throws IOException {

        // Recupero preferenze aggiornate
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);
        System.out.println("Hai premuto salva " + preferences);

        // Imposto le preferenze sullo user bean
        userBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        accountCtrlApplicativo.updateGenreUser(userBean);

        // Devo aggiornare il bean? Di tutto lo stack però,
        // non va bene, tutti dovrebbero recuperare informazioni per il bean dalla persistenza
        // ##### Mostro pop-up ######

        SceneController.getInstance().popUp(event);

    }
    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
        System.out.println("ACG userBean: " + userBean);
    }


}
