package view;

import controller.applicativo.PlaylistToApproveCtrlApplicativo;
import engineering.bean.*;
import engineering.others.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.*;

public class PlaylistToApproveCtrlGrafico implements Initializable {

    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> nameColumn;
    @FXML
    private TableColumn<PlaylistBean, String> authorColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;
    @FXML
    private TableColumn<PlaylistBean, Boolean> approveColumn;

    private List<PlaylistBean> allPlaylist = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button rejectButton = new Button("Reject");

            {
                approveButton.setOnAction(event -> {
                    PlaylistBean playlist = getTableView().getItems().get(getIndex());
                    handleApproveButton(playlist, true);
                });

                rejectButton.setOnAction(event -> {
                    PlaylistBean playlist = getTableView().getItems().get(getIndex());
                    handleApproveButton(playlist, false);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(createButtonBox(approveButton, rejectButton));
                }
            }
        });

        // Recupera tutte le playlist
        PlaylistToApproveCtrlApplicativo allPlaylistController = new PlaylistToApproveCtrlApplicativo();
        allPlaylist = allPlaylistController.retrievePlaylists();

        // Collega i dati alle colonne della TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Aggiungi le playlist alla TableView
        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(allPlaylist);
        playlistTable.setItems(playlistData);
    }

    private void handleApproveButton(PlaylistBean playlist, boolean approve) {
        // Logica per gestire l'approvazione o il rifiuto della playlist
        if (approve) {
            System.out.println("Approvazione della playlist: " + playlist.getPlaylistName());
            // Implementa la logica per l'approvazione della playlist

            // Approva Playlist
            PlaylistToApproveCtrlApplicativo playlistToApproveCtrlApplicativo = new PlaylistToApproveCtrlApplicativo();
            playlistToApproveCtrlApplicativo.approvePlaylist(playlist);
            allPlaylist.remove(playlist);

        } else {
            System.out.println("Rifiuto della playlist: " + playlist.getPlaylistName());
            // Implementa la logica per il rifiuto della playlist con notifica all'utente
        }
    }



    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }
    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }
}
