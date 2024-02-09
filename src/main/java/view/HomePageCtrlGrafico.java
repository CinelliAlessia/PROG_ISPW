package view;

import controller.applicativo.HomePageCtrlApplicativo;

import engineering.bean.*;
import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import model.Playlist;
import view.utils.*;

import java.net.URL;
import java.util.*;

/** Home page controller grafico rappresenta il Concrete Observer */
public class HomePageCtrlGrafico implements Initializable, Observer {

    @FXML
    private TextField searchText;
    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> playlistNameColumn;
    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, String> usernameColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;

    @FXML
    private Button manager;
    @FXML
    private Button account;
    @FXML
    private Button addButton;

    private UserBean userBean;
    private SceneController sceneController;


    /* OBSERVER */
    private PlaylistCollection playlistCollection; /* ISTANZA DEL MODEL (CONCRETE SUBJECT) */
    private List<Playlist> playlists = new ArrayList<>(); /** Observer state -> Model ma serve cosi per il pattern */
    private List<PlaylistBean> playlistsBean = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Recupera tutte le playlist
        System.out.println("GUI Home Page");

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn,genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");

        /* BYPASSIAMO MVC PER PATTERN OBSERVER */
        playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.attach(this);

        /* Metodo pull per ricevere i dati dal dao */
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsBean = homePageController.retrivePlaylistsApproved();                  // Recupera le playlist approvate
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsBean);   // Aggiorna i parametri della tabella

        linkColumn.setCellFactory(param -> new SingleButtonTableCell());
    }

    /** Viene utilizzata da sceneController per impostare lo userBean e l'istanza di Scene controller da utilizzare */
    public void setAttributes(UserBean user, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        this.sceneController = sceneController;

        initializeField();
        System.out.println("GUI HomePage setAttributes: " + userBean);
    }

    public void initializeField() {
        if(userBean == null){
            account.setText("Registrati");
            addButton.setVisible(false);
            manager.setVisible(false);
        } else {
            account.setText(userBean.getUsername());
            manager.setVisible(userBean.isSupervisor());
            addButton.setVisible(true);
        }
    }

    @FXML
    protected void onAccountClick(ActionEvent event) {
        if(userBean == null){ // Utente Guest
            sceneController.<RegistrazioneCtrlGrafico>goToScene(event, FxmlFileName.REGISTRATION_FXML,null);
        } else { // Utente registrato
            sceneController.<AccountCtrlGrafico>goToScene(event, FxmlFileName.ACCOUNT_FXML, userBean);
        }
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) {
        sceneController.<AddPlaylistCtrlGrafico>goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
    }

    @FXML
    public void onManagerClick(ActionEvent event) {
        sceneController.<PendingPlaylistCtrlGrafico>goToScene(event, FxmlFileName.MANAGER_PLAYLIST_FXML,null);
    }

    @FXML
    public void onSearchPlaylistClick() {
        PlaylistBean pB = new PlaylistBean();
        pB.setPlaylistName(searchText.getText());

        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsBean = homePageController.searchNamePlaylist(pB);                                    // Recupera le playlist approvate
        //TableManager.createTable(playlistTable,columns, nameColumns, playlists, genreColumn);   // Aggiorna i parametri della tabella
        TableManager.updateTable(playlistTable, playlistsBean);

        System.out.println("GUI home page: playlist trovate " + playlistsBean);
    }
    @FXML
    protected void onFilterClick(ActionEvent event) {
        sceneController.popUp(event, MessageString.ADDED_PLAYLIST,true);
    }


    /** UTILIZZATA PER IL PATTERN OBSERVER */
    @Override
    public void update() {

        try{
            playlists = playlistCollection.getState();
            for(Playlist p: playlists){
                playlistsBean.add(new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId()));
            }
            TableManager.updateTable(playlistTable, playlistsBean);
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }
    }
}
