package view;

import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountCtrlGrafico{

    @FXML
    public Button saveButton;

    @FXML
    private Label usernameText, supervisorText, emailText;

    @FXML
    private Button back, addButton;

    @FXML
    private CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz,
            acoustic, reb, country, alternative;

    public volatile UserBean userBean;

    public void setUserBean(UserBean user) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        System.out.println("ACG setUserBean: " + userBean);
    }

    public void initializeData(UserBean user){
        this.userBean = user;

        System.out.println("ACG in inizializeData: " + userBean);

        System.out.println(userBean.getEmail()+ " " + userBean.getUsername() +" "+userBean.getPreferences());

        String username = userBean.getUsername();

        System.out.println(usernameText.getText());

        usernameText.setText(username);

        supervisorText.setText("FALSE");
        emailText.setText(userBean.getEmail());

        ArrayList<String> preferences = userBean.getPreferences();

        pop.setSelected(preferences.contains("Pop"));
        indie.setSelected(preferences.contains("Indie"));
        classic.setSelected(preferences.contains("Classic"));
        rock.setSelected(preferences.contains("Rock"));
        electronic.setSelected(preferences.contains("Electronic"));
        house.setSelected(preferences.contains("House"));
        hipHop.setSelected(preferences.contains("Hip Hop"));
        jazz.setSelected(preferences.contains("Jazz"));
        acoustic.setSelected(preferences.contains("Acoustic"));
        reb.setSelected(preferences.contains("REB"));
        country.setSelected(preferences.contains("Country"));
        alternative.setSelected(preferences.contains("Alternative"));
    }


    @FXML
    public void onSaveClick(ActionEvent event) {
    }

    @FXML
    public void onBackClick(ActionEvent event) throws IOException {
        System.out.println("AGC on Back Click: Bean: " + userBean);

        Stage stage = (Stage) back.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Ottieni l'istanza corrente del controller HomePageCtrlGrafico
        HomePageCtrlGrafico homeController = fxmlLoader.getController();

        // Chiamare il metodo start del controller HomePageCtrlGrafico
        //homeController.start(stage, userBean);
    }


    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {

        System.out.println("ACG userBean: " + userBean);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/caricaPlaylist.fxml"));
        Parent root = loader.load();
        loader.<AddPlaylistCtrlGrafico>getController().setUserBean(userBean);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Carica Playlist");
        stage.setScene(scene);
        stage.show();
    }
}
