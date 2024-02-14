package view.first;

import controller.applicativo.HomePageCtrlApplicativo;

import engineering.bean.*;
import engineering.exceptions.*;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import model.Playlist;
import view.first.utils.*;

import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/** Home page controller grafico rappresenta il Concrete Observer */
public class HomePageCtrlGrafico<T extends ClientBean> implements Initializable, Observer {


    public ContextMenu contextMenu;
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
    public Button menu;

    private static final Logger logger = Logger.getLogger(HomePageCtrlApplicativo.class.getName());

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
        logger.info("GUI Home Page");

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

        logger.info(STR."GUI HomePage setAttributes: \{clientBean}");
    }

    public void initializeField() {
        if(clientBean == null){
            logger.info("GUI HomePage: Accesso come Guest");

            menu.setVisible(false);
            addButton.setVisible(false);
            manager.setVisible(false);
            account.setText("Registrati");

        } else { // UserBean o SupervisorBean
            logger.info(STR."GUI HomePage: Accesso come Client, Supervisor: \{clientBean.isSupervisor()}");

            addButton.setVisible(true);
            manager.setVisible(clientBean.isSupervisor());
            account.setText(clientBean.getUsername());
            menu.setVisible(!clientBean.isSupervisor());
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
        filterPlaylist.setPlaylistName(searchText.getText());

        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsBean = homePageController.searchPlaylistByFilters(filterPlaylist);        // Recupera le playlist approvate

        //playlistsBean = homePageController.searchPlaylistByName(filterPlaylist);         // Recupera le playlist cercando per nome
        TableManager.updateTable(playlistTable, playlistsBean);

        logger.info(STR."GUI home page search click: \{filterPlaylist} nome: \{filterPlaylist.getPlaylistName()} genre: \{filterPlaylist.getPlaylistGenre()} emotional: \{filterPlaylist.getEmotional()}");
    }

    @FXML
    public void onFilterClick(ActionEvent event) {
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


    @FXML
    public void showContextMenu(ActionEvent event) {

        contextMenu.getItems().clear(); // Rimuove tutti gli elementi dal ContextMenu

        System.out.println("BOTTONE PREMUTO");
        UserBean userBean = (UserBean) clientBean;

        // Ottenere la lista di NoticeBean dal tuo clientBean (assumendo che clientBean sia accessibile)
        for (NoticeBean noticeBean : userBean.getNotices()) {
            System.out.println("BOTTONE PREMUTO -> NOTIFICA");

            // Creare un MenuItem per ciascun NoticeBean
            MenuItem menuItem = new MenuItem(STR."\{noticeBean.getTitle()}\n\{noticeBean.getBody()}");

            // Aggiungere un gestore di eventi per ciascun MenuItem, se necessario
            menuItem.setOnAction(e -> handleNoticeSelection(noticeBean));

            // Aggiungi il MenuItem al ContextMenu
            contextMenu.getItems().add(menuItem);
        }

        // Converti le coordinate locali del menu in coordinate dello schermo
        double screenX = menu.localToScreen(menu.getBoundsInLocal()).getMinX();
        double screenY = menu.localToScreen(menu.getBoundsInLocal()).getMinY();

        // Mostra il ContextMenu accanto al bottone
        contextMenu.show(menu, screenX, screenY);
    }

    private void handleNoticeSelection(NoticeBean noticeBean) {
        List<NoticeBean> noticeBeanList = ((UserBean)clientBean).getNotices();
        noticeBeanList.remove(noticeBean);
        ((UserBean)clientBean).setNotices(noticeBeanList);
    }
}
