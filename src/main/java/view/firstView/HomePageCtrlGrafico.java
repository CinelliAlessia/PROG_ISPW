package view.firstView;

import controller.applicativo.HomePageCtrlApplicativo;

import engineering.bean.*;
import engineering.exceptions.*;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import model.Playlist;
import view.firstView.utils.*;

import java.net.URL;
import java.util.*;

/** Home page controller grafico rappresenta il Concrete Observer */
public class HomePageCtrlGrafico<T extends ClientBean> implements Initializable, Observer {

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

    private T clientBean;

    private SceneController sceneController;
    private final PlaylistBean filterPlaylist = new PlaylistBean(); // Contiene gli attributi secondo i quali filtrare le playlists

    /** OBSERVER */
    private PlaylistCollection playlistCollection; /** ISTANZA DEL MODEL (CONCRETE SUBJECT) */
    private List<Playlist> playlists = new ArrayList<>(); /** Observer state -> Model ma serve cosi per il pattern */
    private List<PlaylistBean> playlistsBean = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Recupera tutte le playlist
        System.out.println("GUI Home Page");

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");

        /* BYPASSIAMO MVC PER PATTERN OBSERVER */
        playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.attach(this);

        /* Metodo pull per ricevere i dati dal dao */
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsBean = homePageController.retrivePlaylistsApproved();                  // Recupera le playlist approvate

        TableManager.setColumnsTableView(columns, nameColumns);   // Aggiorna i parametri della tabella
        TableManager.updateTable(playlistTable,playlistsBean);
        linkColumn.setCellFactory(_ -> new SingleButtonTableCell());
    }

    /** Viene utilizzata da sceneController per impostare lo userBean e l'istanza di Scene controller da utilizzare */
    public void setAttributes(T clientBean, SceneController sceneController) {
        this.clientBean = clientBean;
        this.sceneController = sceneController;
        initializeField();

        System.out.println("GUI HomePage setAttributes: " + clientBean);
    }

    public void initializeField() {
        if(clientBean == null){
            System.out.println("GUI HomePage: Accesso come Guest");
            addButton.setVisible(false);
            manager.setVisible(false);
            account.setText("Registrati");
        } else { // UserBean o SupervisorBean
            System.out.println("GUI HomePage: Accesso come Client, Supervisor: " + clientBean.isSupervisor());
            addButton.setVisible(true);
            manager.setVisible(clientBean.isSupervisor());
            account.setText(clientBean.getUsername());
        }
    }
    @FXML
    protected void onAccountClick(ActionEvent event) {
        if(clientBean == null){ // Utente Guest
            sceneController.goToScene(event, FxmlFileName.REGISTRATION_FXML);
        } else { // Utente registrato
            sceneController.goToScene(event, FxmlFileName.ACCOUNT_FXML, clientBean);
        }
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) {
        PlaylistBean playlistBean = new PlaylistBean(); // ##############################################
        sceneController.goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, clientBean, playlistBean);
    }

    @FXML
    public void onManagerClick(ActionEvent event) {
        sceneController.goToScene(event, FxmlFileName.MANAGER_PLAYLIST_FXML);
    }

    @FXML
    public void onSearchPlaylistClick() {
        // Se sono stati impostati i filtri verranno presi
        filterPlaylist.setPlaylistName(searchText.getText());

        System.out.println(STR."GUI home page: \{filterPlaylist} nome: \{filterPlaylist.getPlaylistName()} genre: \{filterPlaylist.getPlaylistGenre()} emotional: \{filterPlaylist.getEmotional()}");

        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        //playlistsBean = homePageController.searchPlaylistByFilters(playlistBean);              // Recupera le playlist approvate

        playlistsBean = homePageController.searchPlaylistByName(filterPlaylist);                 // Recupera le playlist approvate
        //TableManager.createTable(playlistTable,columns, nameColumns, playlists, genreColumn);  // Aggiorna i parametri della tabella
        TableManager.updateTable(playlistTable, playlistsBean);


    }

    @FXML
    protected void onFilterClick(ActionEvent event) {
        sceneController.goToFilterPopUp(event, clientBean, filterPlaylist);
    }


    /** UTILIZZATA PER IL PATTERN OBSERVER */
    @Override
    public void update() {
        try{
            playlists = playlistCollection.getState();
            for(Playlist p: playlists){
                playlistsBean.add(new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional()));
            }
            TableManager.updateTable(playlistTable, playlistsBean);
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }
    }

    public void searchFilter(PlaylistBean playlistBean){
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsBean = homePageController.searchPlaylistByFilters(playlistBean);                 // Recupera le playlist approvate
        //TableManager.createTable(playlistTable,columns, nameColumns, playlists, genreColumn);   // Aggiorna i parametri della tabella
        TableManager.updateTable(playlistTable, playlistsBean);

        System.out.println("GUI home page: playlist trovate " + playlistsBean);

    }
}
