package view;

import controller.applicativo.HomePageCtrlApplicativo;
import engineering.bean.*;
import engineering.others.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomePageCtrlGrafico implements Initializable {

    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> nameColumn;
    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, String> authorColumn;
    @FXML
    private TableColumn<PlaylistBean, String> linkColumn;
    @FXML
    private Button manager;
    @FXML
    private Button account;
    @FXML
    private Button addButton;

    private UserBean userBean;

    private List<PlaylistBean> playlistsApproved = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Recupera tutte le playlist
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistsApproved = homePageController.retrivePlaylistsApproved();

        // Collega i dati alle colonne della TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Aggiungi le playlist alla TableView
        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(playlistsApproved);
        playlistTable.setItems(playlistData);

        // Configura la colonna "Generi musicali"
        genreColumn.setCellFactory(col -> new TableCell<>() {
            final Button button = new Button("Dettagli");

            {
                button.setOnAction(event -> {
                    PlaylistBean playlistBean = getTableView().getItems().get(getIndex());
                    openPopup(playlistBean);
                });
            }

            @Override
            protected void updateItem(List<String> genres, boolean empty) {
                super.updateItem(genres, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

    }



    private void openPopup(PlaylistBean playlistBean) {
        // Implementa qui la logica per aprire il popup
        // Puoi usare FXMLLoader per caricare il file FXML del popup
        // e quindi mostrare il popup con un nuovo Stage
        // Puoi anche passare i dati della playlistBean al popup
    }

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato nel set user bean: " + userBean + " " + userBean.getEmail() + " " + userBean.isSupervisor());
        System.out.println();
        initialize();
    }


    public void initialize() {
        if(userBean == null){
            account.setText("Registrati");
            addButton.setVisible(false);
            manager.setVisible(false);
        } else {
            account.setText(userBean.getUsername());
            manager.setVisible(userBean.isSupervisor());
            addButton.setVisible(true);
        }
    }

    @FXML
    protected void onAccountClick(ActionEvent event) throws IOException {
        System.out.println("HCG userBean: " + userBean);
        if(userBean == null){ // Utente Guest
            SceneController.getInstance().goToScene(event, FxmlFileName.REGISTRATION_FXML);
        } else { // Utente registrato
            SceneController.getInstance().<AccountCtrlGrafico>goToScene(event, FxmlFileName.ACCOUNT_FXML, userBean);
        }
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<AddPlaylistCtrlGrafico>goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
    }

    public void onManagerClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlFileName.MANAGER_PLAYLIST_FXML);
    }


}
