package view;

import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageCtrlGrafico implements Initializable {

    @FXML
    private Button account, addButton;

    private UserBean userBean;

    public void setUserBean(UserBean user) {
        this.userBean = user;
        System.out.println("HCG impostato user bean: " + userBean);
    }

    @FXML
    protected void onAccountClick(ActionEvent event) throws IOException {

        System.out.println("HCG userBean: " + userBean);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/account.fxml"));
        Parent root = loader.load();
        loader.<AccountCtrlGrafico>getController().initializeData(userBean);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Account");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addPlaylistClick(ActionEvent event) throws IOException {

        System.out.println("HCG userBean: " + userBean);

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

    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
