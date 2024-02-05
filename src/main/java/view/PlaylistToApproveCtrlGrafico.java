package view;

import controller.applicativo.PlaylistToApproveCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.others.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistToApproveCtrlGrafico implements Initializable {

    @FXML
    private TableColumn<PlaylistBean, Boolean> approveColumn;
    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> nameColumn;
    @FXML
    private TableColumn<PlaylistBean, String> authorColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");

        // Recupera tutte le playlist
        PlaylistToApproveCtrlApplicativo allPlaylistController = new PlaylistToApproveCtrlApplicativo();
        List<PlaylistBean> allPlaylist = allPlaylistController.retriveAllPlaylist();

        // Collega i dati alle colonne della TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Aggiungi la colonna con bottoni "Approve"
        approveColumn.setCellFactory(param -> new TableCell<PlaylistBean, Boolean>() {
            private final Button approveButton = new Button("Approve");
            {
                approveButton.setOnAction(event -> {
                    PlaylistBean playlist = getTableView().getItems().get(getIndex());
                    handleApproveButton(playlist);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(approveButton);
                }
            }
        });

        // Aggiungi le playlist alla TableView
        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(allPlaylist);
        playlistTable.setItems(playlistData);
    }

    private void handleApproveButton(PlaylistBean playlist) {
        // Logica per gestire l'approvazione della playlist
        System.out.println("Approvazione della playlist: " + playlist.getPlaylistName());
        // Puoi implementare qui la logica per approvare la playlist
    }



    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }
}
