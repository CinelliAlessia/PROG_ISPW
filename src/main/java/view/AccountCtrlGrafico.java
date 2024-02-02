package view;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.UserBean;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.dao.UserDAO;
import engineering.others.FxmlFileName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AccountCtrlGrafico{

    @FXML
    public Button saveButton;
    @FXML
    private Label usernameText;
    @FXML
    private Label supervisorText;
    @FXML
    private Label emailText;
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
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + userBean);
        initializeData(userBean);
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        System.out.println("ACG in initializeData: " + userBean);

        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        String username = userBean.getUsername();

        System.out.println(usernameText.getText());

        usernameText.setText(username);

        supervisorText.setText("FALSE");
        emailText.setText(userBean.getEmail());

        List<String> preferences = userBean.getPreferences();

        pop.setSelected(preferences.contains("Pop"));
        indie.setSelected(preferences.contains("Indie"));
        classic.setSelected(preferences.contains("Classic"));
        rock.setSelected(preferences.contains("Rock"));
        electronic.setSelected(preferences.contains("Electronic"));
        house.setSelected(preferences.contains("House"));
        hipHop.setSelected(preferences.contains("HipHop"));
        jazz.setSelected(preferences.contains("Jazz"));
        acoustic.setSelected(preferences.contains("Acoustic"));
        reb.setSelected(preferences.contains("REB"));
        country.setSelected(preferences.contains("Country"));
        alternative.setSelected(preferences.contains("Alternative"));
    }

    public void retrivePlaylist() {
        // TODO
    }

    /** Un tasto visibile solo dal supervisor, utilizzato per accettare playlist*/
    public void approvePlaylist(){
        // TODO
    }

    @FXML
    public void onSaveClick(ActionEvent event) throws IOException {
        // Recupero preferenze aggiornate
        ArrayList<String> preferences = retriveCheckList();
        // Imposto le preferenze sullo user bean
        userBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        accountCtrlApplicativo.updateGenreUser(userBean);

        // Devo aggiornare il bean? Di tutto lo stack però,
        // non va bene, tutti dovrebbero recuperare informazioni per il bean dalla persistenza
        // ##### Mostro pop-up ######

    }
    @FXML
    public void onBackClick(ActionEvent event) {
        SceneController.getInstance().goBack(event);
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event, FxmlFileName.UPLOAD_PLAYLIST_FXML, userBean);
        System.out.println("ACG userBean: " + userBean);
    }

    /** DUPLICATA, DEVE ANDARE IN UNA QUALCHE CLASSE ! */
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
