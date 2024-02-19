package view.first;

import engineering.bean.NoticeBean;
import engineering.others.Printer;
import javafx.collections.ObservableList;
import view.first.utils.*;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
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
    private ObservableList<PlaylistBean> observableList;

    public void setAttributes(SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Printer.logPrint("GUI PendingPlaylist: Inizio gestione playlist: ");

        // Recupera tutte le playlist pending, metodo pull
        PendingPlaylistCtrlApplicativo allPlaylistController = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = allPlaylistController.retrievePlaylists(); // Vengono recuperate tutte le playlist pending

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn); // Tutte le colonne della table view
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");
        TableManager.setColumnsTableView(columns, nameColumns);

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(button -> new DoubleButtonTableCell(this));
        linkColumn.setCellFactory(button -> new SingleButtonTableCell());

        TableManager tableManager = new TableManager();
        observableList = tableManager.handler(playlistTable,playlistsPending);
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }

    /** Public perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public void handlerButton(PlaylistBean playlistBean, boolean approve) {

        // Logica per gestire l'approvazione o il rifiuto della playlist
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();

        String title;
        String body;

        if (approve) {
            Printer.logPrint(String.format("Approvazione della playlist: %s", playlistBean.getPlaylistName()));

            title = "Approved";
            body = String.format("Your playlist %s is approved!", playlistBean.getPlaylistName());

            // Approva Playlist
            pendingPlaylistCtrlApplicativo.approvePlaylist(playlistBean);
        } else {
            Printer.logPrint(String.format("Rifiuto della playlist: %s", playlistBean.getPlaylistName()));

            title = "Rejected";
            body = String.format("Your playlist %s is rejected!", playlistBean.getPlaylistName());

            // Rifiuta Playlist
            pendingPlaylistCtrlApplicativo.rejectPlaylist(playlistBean);

        }

        NoticeBean noticeBean = new NoticeBean(title, body, playlistBean.getUsername());
        pendingPlaylistCtrlApplicativo.sendNotification(noticeBean);
        observableList.remove(playlistBean);
    }
}