package view;

import controller.applicativo.PlaylistToApproveCtrlApplicativo;
import engineering.bean.PlaylistBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistToApproveCtrlGrafico implements Initializable {

    @FXML
    private TableView playlistTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn authorColumn;
    @FXML
    private TableColumn linkColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inizio gestione playlist: ");
        // Recupera tutte le playlist
        PlaylistToApproveCtrlApplicativo allPlaylistController = new PlaylistToApproveCtrlApplicativo();
        List<PlaylistBean> all_playlist = allPlaylistController.retriveAllPlaylist();

        // Collega i dati alle colonne della TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomePlaylist"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("autore"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Aggiungi le playlist alla TableView
        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(all_playlist);
        playlistTable.setItems(playlistData);
    }


    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }

}
