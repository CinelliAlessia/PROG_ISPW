package view;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;
import engineering.others.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.net.URL;
import java.util.*;

public class AddPlaylistCtrlGrafico implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField link;
    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;
    private UserBean userBean;

    private List<CheckBox> checkBoxList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato user bean: " + userBean);
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }

    /** Click sul tasto add Playlist*/
    @FXML
    public void onUploadClick(ActionEvent event){
        String linkPlaylist = link.getText();
        String titolo = title.getText();



        // Recupero preferenze aggiornate
        List<String> preferences = GenreMenager.retrieveCheckList(checkBoxList);

        // Costruzione della playlistBean con i parametri per il Controller Applicativo
        PlaylistBean playlistBean = new PlaylistBean(userBean.getEmail(), userBean.getUsername(), titolo, linkPlaylist, preferences);

        if(userBean.isSupervisor()){
            playlistBean.setApproved();
        }
        // Invocazione metodo controller Applicativo che in teoria è static
        AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
        addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

        // Mostro la pagina precedente dell'ingresso in "Aggiungi Playlist"
        SceneController.getInstance().goBack(event);

    }

}
