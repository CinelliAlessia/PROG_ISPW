package view;

import controller.applicativo.PlaylistToApproveCtrlApplicativo;
import engineering.bean.*;
import engineering.others.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.*;

public class PlaylistToApproveCtrlGrafico implements Initializable {

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
    private List<PlaylistBean> playlistsPending = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");

        // Recupera tutte le playlist
        PlaylistToApproveCtrlApplicativo allPlaylistController = new PlaylistToApproveCtrlApplicativo();
        playlistsPending = allPlaylistController.retrievePlaylists();

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn,genreColumn); // Tutte le colonne "semplici"
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");
        TableManager.createTable(playlistTable,columns, nameColumns, playlistsPending, genreColumn);

        // Aggiungi la colonna con bottoni "Approve" o "Reject"
        approveColumn.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("V");
            private final Button rejectButton = new Button("X");

            {   approveButton.setOnAction(event -> {
                    PlaylistBean playlist = getTableView().getItems().get(getIndex());
                    handleApproveButton(playlist, true);
                });

                rejectButton.setOnAction(event -> {
                    PlaylistBean playlist = getTableView().getItems().get(getIndex());
                    handleApproveButton(playlist, false);
                });

                approveButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                        "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                        "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

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



    }

    private void handleApproveButton(PlaylistBean playlist, boolean approve) {
        // Logica per gestire l'approvazione o il rifiuto della playlist
        if (approve) {
            System.out.println("Approvazione della playlist: " + playlist.getPlaylistName());
            // Implementa la logica per l'approvazione della playlist

            // Approva Playlist
            PlaylistToApproveCtrlApplicativo playlistToApproveCtrlApplicativo = new PlaylistToApproveCtrlApplicativo();
            playlistToApproveCtrlApplicativo.approvePlaylist(playlist);
            playlistsPending.remove(playlist);

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
