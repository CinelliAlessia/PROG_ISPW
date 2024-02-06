package view;

import controller.applicativo.HomePageCtrlApplicativo;
import engineering.bean.*;
import engineering.others.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomePageCtrlGrafico implements Initializable {

    @FXML
    private TableView<PlaylistBean> playlistTable;
    @FXML
    private TableColumn<PlaylistBean, String> playlistNameColumn;
    @FXML
    private TableColumn<PlaylistBean, List<String>> genreColumn;
    @FXML
    private TableColumn<PlaylistBean, String> usernameColumn;
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

        List<TableColumn<PlaylistBean, ?>> columns = Arrays.asList(playlistNameColumn, linkColumn, usernameColumn, genreColumn);
        List<String> nameColumns = Arrays.asList("playlistName", "link", "username","playlistGenre");
        TableManager.createTable(playlistTable,columns, nameColumns, playlistsApproved, genreColumn);
    }



    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato nel set user bean: " + userBean + " " + userBean.getEmail() + " " + userBean.isSupervisor());
        System.out.println();
        initialize();
    }
    //########## Aggiungere un pop-up che chiede di effettuare la registrazione o login ########<

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
