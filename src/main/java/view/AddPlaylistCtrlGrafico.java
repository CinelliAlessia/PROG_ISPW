package view;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;

import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.PlaylistCollection;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import org.apache.commons.validator.routines.UrlValidator;
import view.utils.GenreManager;
import view.utils.MessageString;
import view.utils.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddPlaylistCtrlGrafico implements Initializable {

    @FXML
    private Label errorLabel;
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

    /** Click sul tasto carica Playlist*/
    @FXML
    public void onUploadClick(ActionEvent event) throws IOException {
        String linkPlaylist = link.getText();
        String titolo = title.getText();

        //Controllo sui campi vuoti

        if( !linkPlaylist.isEmpty() && !titolo.isEmpty() ){
            // Recupero generi della playlist
            List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

            // La playlist è approvata solo se l'utente è un supervisore
            boolean approved = userBean.isSupervisor();

            // Costruzione della playlistBean con i parametri per il Controller Applicativo
            PlaylistBean playlistBean = null;
            try {
                playlistBean = new PlaylistBean(userBean.getEmail(), userBean.getUsername(), titolo, linkPlaylist, genre, approved);

                // Invocazione metodo controller Applicativo che in teoria è static
                AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
                addPlaylistControllerApplicativo.insertPlaylist(playlistBean);



                // Mostro la pagina precedente dell'ingresso in "Aggiungi Playlist"
                SceneController.getInstance().goBack(event);
                if(approved){
                    SceneController.getInstance().popUp(event, MessageString.ADDED_PLAYLIST);
                }
                else{
                    SceneController.getInstance().popUp(event,MessageString.ADDED_PENDING_PLAYLIST);
                }
                System.out.println("PLAYLIST AGGIUNTA");

            } catch (LinkIsNotValid e){
                errorLabel.setText(e.getMessage());
                System.out.println(e.getMessage());
                e.fillInStackTrace();
            }


        } else {
            // campi vuoti
            errorLabel.setText("I campi sono vuoti!");
            System.out.println("PLAYLIST NON AGGIUNTA");
        }
    }



}
