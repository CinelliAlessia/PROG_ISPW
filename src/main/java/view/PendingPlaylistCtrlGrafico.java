package view;

import engineering.bean.SupervisorBean;
import view.utils.*;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class PendingPlaylistCtrlGrafico implements Initializable {

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
    private TableColumn<PlaylistBean, Boolean> usernameColumn;

    private SceneController sceneController;

    public void setAttributes(SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");

        // Recupera tutte le playlist
        PendingPlaylistCtrlApplicativo allPlaylistController = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = allPlaylistController.retrievePlaylists(); // Vengono recuperate tutte le playlist pending

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn); // Tutte le colonne della table view
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);
        TableManager.updateTable(playlistTable,playlistsPending);
        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(_ -> new DoubleButtonTableCell());
    }

    /** Static perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone */
    public static void handlePendingButton(PlaylistBean playlistBean, boolean approve, TableView<PlaylistBean> tableView) {

        // Logica per gestire l'approvazione o il rifiuto della playlist
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();

        if (approve) {
            System.out.println("Approvazione della playlist: " + playlistBean.getPlaylistName());

            // Approva Playlist
            pendingPlaylistCtrlApplicativo.approvePlaylist(playlistBean);
        } else {
            System.out.println("Rifiuto della playlist: " + playlistBean.getPlaylistName());

            // Rifiuta Playlist
            pendingPlaylistCtrlApplicativo.rejectPlaylist(playlistBean);
        }

        // Vengono recuperate tutte le playlist pending
        List<PlaylistBean> playlistsPending = pendingPlaylistCtrlApplicativo.retrievePlaylists();
        TableManager.updateTable(tableView, playlistsPending);
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }
}