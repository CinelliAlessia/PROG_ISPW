package view;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import engineering.others.GenreMenager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class AddPlaylistCtrlGrafico {

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

        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(pop);
        checkBoxList.add(indie);
        checkBoxList.add(classic);
        checkBoxList.add(rock);
        checkBoxList.add(electronic);
        checkBoxList.add(house);
        checkBoxList.add(hipHop);
        checkBoxList.add(jazz);
        checkBoxList.add(acoustic);
        checkBoxList.add(reb);
        checkBoxList.add(country);
        checkBoxList.add(alternative);

        // Recupero preferenze aggiornate
        ArrayList<String> preferences = GenreMenager.retrieveCheckList(checkBoxList);

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
