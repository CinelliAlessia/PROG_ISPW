package view.first;

import engineering.bean.NoticeBean;
import javafx.collections.ObservableList;
import view.first.utils.*;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class PendingPlaylistCtrlGrafico implements Initializable {

    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, Boolean> approveColumn;
    @FXML
    private TableColumn<PlaylistBean, String> playlistNameColumn;
    @FXML
    private TableColumn<PlaylistBean, Boolean> usernameColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;
    @FXML
    private TableView<PlaylistBean> playlistTable;

    private SceneController sceneController;
    private static ObservableList<PlaylistBean> observableList;
    private static final Logger logger = Logger.getLogger(PendingPlaylistCtrlGrafico.class.getName());


    public void setAttributes(SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");

        // Recupera tutte le playlist pending, metodo pull
        PendingPlaylistCtrlApplicativo allPlaylistController = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = allPlaylistController.retrievePlaylists(); // Vengono recuperate tutte le playlist pending

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn); // Tutte le colonne della table view
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);
        //TableManager.updateTable(playlistTable,playlistsPending);

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(_ -> new DoubleButtonTableCell());

        TableManager tableManager = new TableManager();
        observableList = tableManager.collegamento(playlistTable,playlistsPending);
    }

    /** Static perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public static void handlePendingButton(PlaylistBean playlistBean, boolean approve, TableView<PlaylistBean> tableView) {

        // Logica per gestire l'approvazione o il rifiuto della playlist
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();

        String title;
        String body;

        if (approve) {
            logger.info("Approvazione della playlist: " + playlistBean.getPlaylistName());

            title = "Approved";
            body = String.format("Your playlist %s is approved!",playlistBean.getPlaylistName());

            // Approva Playlist
            pendingPlaylistCtrlApplicativo.approvePlaylist(playlistBean);
        } else {
            logger.info("Rifiuto della playlist: " + playlistBean.getPlaylistName());

            title = "Rejected";
            body = String.format("Your playlist %s is rejected!",playlistBean.getPlaylistName());

            // Rifiuta Playlist
            pendingPlaylistCtrlApplicativo.rejectPlaylist(playlistBean);

        }

        NoticeBean noticeBean = new NoticeBean(title, body, playlistBean.getUsername());
        pendingPlaylistCtrlApplicativo.sendNotification(noticeBean);
        observableList.remove(playlistBean);
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }
}