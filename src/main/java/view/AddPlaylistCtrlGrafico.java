package view;

import controllerApplicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.bean.UserBean;
import engineering.exceptions.PlaylistNameAlreadyInUse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddPlaylistCtrlGrafico {

    @FXML
    private TextField title, link;
    @FXML
    private Button back, account;
    @FXML
    private CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz, acoustic, reb, country, alternative;


    private UserBean userBean;
    public void start(Stage stage, UserBean user) throws IOException {
        userBean = user;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/caricaPlaylist.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Carica Playlist");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAccountClick() throws IOException { // Non devo fa controlli
        Stage stage = (Stage) account.getScene().getWindow();
        AccountCtrlGrafico accountCGUI = new AccountCtrlGrafico();
        accountCGUI.start(stage,userBean);
    }

    @FXML
    public void onBackClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        HomePageCtrlGrafico home = new HomePageCtrlGrafico();
        home.start(stage,userBean);
    }

    @FXML
    public void onUploadClick() throws SQLException, PlaylistNameAlreadyInUse {
        String link_playlist = link.getText();
        String titolo = title.getText();

        ArrayList<String> playlist_genre = retriveCheckList();

        // Verifica che i campi non sono vuoti ###############

        PlaylistBean playlistBean = new PlaylistBean(userBean.getEmail(), userBean.getUsername(), titolo, link_playlist, playlist_genre);
        AddPlaylistCtrlApplicativo addPlaylist = new AddPlaylistCtrlApplicativo();
        addPlaylist.insertPlaylist(playlistBean);
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
