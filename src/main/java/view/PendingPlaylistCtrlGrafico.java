package view;

import view.utils.*;
import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.*;

import javafx.event.*;
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

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn); // Tutte le colonne "semplici"
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username", "playlistGenre");
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsPending, genreColumn);

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(param -> new ButtonTableCell());
    }

    public static void handlePendingButton(PlaylistBean playlist, boolean approve) {
        // Logica per gestire l'approvazione o il rifiuto della playlist
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        if (approve) {
            System.out.println("Approvazione della playlist: " + playlist.getPlaylistName());
            // Implementa la logica per l'approvazione della playlist

            // Approva Playlist
            pendingPlaylistCtrlApplicativo.approvePlaylist(playlist);
        } else {
            System.out.println("Rifiuto della playlist: " + playlist.getPlaylistName());
            // Implementa la logica per il rifiuto della playlist con notifica all'utente

            // Rifiuta Playlist
            pendingPlaylistCtrlApplicativo.rejectPlaylist(playlist);
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }


}