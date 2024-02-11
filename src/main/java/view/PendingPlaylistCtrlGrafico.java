package view;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import view.utils.DoubleButtonTableCell;
import view.utils.SceneController;
import view.utils.TableManager;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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
        TableManager.createTable(playlistTable, columns, nameColumns, playlistsPending);

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(param -> new DoubleButtonTableCell());
    }

    public static void handlePendingButton(PlaylistBean playlistBean, boolean approve) {
        // Logica per gestire l'approvazione o il rifiuto della playlist
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        if (approve) {
            System.out.println("Approvazione della playlist: " + playlistBean.getPlaylistName());

            // Approva Playlist
            pendingPlaylistCtrlApplicativo.approvePlaylist(playlistBean);
        } else {
            System.out.println("Rifiuto della playlist: " + playlistBean.getPlaylistName());
            // Implementa la logica per il rifiuto della playlist con notifica all'utente

            // Rifiuta Playlist
            pendingPlaylistCtrlApplicativo.rejectPlaylist(playlistBean);
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }
}