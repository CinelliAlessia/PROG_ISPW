package view;

import controllerApplicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import engineering.exceptions.PlaylistNameAlreadyInUse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    @FXML
    public void onUploadClick(ActionEvent event) throws SQLException, PlaylistNameAlreadyInUse, IOException {
        String linkPlaylist = link.getText();
        String titolo = title.getText();

        ArrayList<String> playlistGenre = retriveCheckList();

        // Verifica che i campi non sono vuoti ###############
        System.out.println("AddP: "+userBean.getEmail()+ " " + userBean.getUsername()+ " " + titolo + " " + linkPlaylist + " " + playlistGenre);
        // Costruzione della playlistBean con i parametri per il Controller Applicativo
        PlaylistBean playlistBean = new PlaylistBean(userBean.getEmail(), userBean.getUsername(), titolo, linkPlaylist, playlistGenre);
        // Invocazione metodo controller Applicativo
        AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
        addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

        // Mostro la pagina precedente dell'ingresso in "Aggiungi Playlist"
        SceneController.getInstance().goBack(event);

    }
    private ArrayList<String> retriveCheckList(){
        // Inizializza la lista dei generi musicali selezionati
        ArrayList<String> preferences = new ArrayList<>();

        // Aggiungi i generi musicali selezionati alla lista
        if (pop.isSelected()) {
            preferences.add("Pop");
        }
        if (indie.isSelected()) {
            preferences.add("Indie");
        }
        if (classic.isSelected()) {
            preferences.add("Classic");
        }
        if (rock.isSelected()) {
            preferences.add("Rock");
        }
        if (electronic.isSelected()) {
            preferences.add("Electronic");
        }
        if (house.isSelected()) {
            preferences.add("House");
        }
        if (hipHop.isSelected()) {
            preferences.add("HipHop");
        }
        if (jazz.isSelected()) {
            preferences.add("Jazz");
        }
        if (acoustic.isSelected()) {
            preferences.add("Acoustic");
        }
        if (reb.isSelected()) {
            preferences.add("REB");
        }
        if (country.isSelected()) {
            preferences.add("Country");
        }
        if (alternative.isSelected()) {
            preferences.add("Alternative");
        }
        return preferences;
    }

}
