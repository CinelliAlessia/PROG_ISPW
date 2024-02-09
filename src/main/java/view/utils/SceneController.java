package view.utils;

import view.*;

import engineering.bean.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

public class SceneController {
    private final Deque<Scene> sceneStack;

    public SceneController(){
        sceneStack = new LinkedList<>();
    }

    @FXML
    public void goBack(ActionEvent event) {
        if (!sceneStack.isEmpty()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(sceneStack.pop()); // Pop the last scene from stack
            stage.show();
        }
    }

    @FXML
    public void pushCurrentScene(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack
    }

    @FXML
    public <T> void goToScene(ActionEvent event, String fxmlPath, UserBean userBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            setAttributes(controller, userBean);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            sceneStack.push(stage.getScene()); // Push current scene onto stack

            Scene scene = new Scene(root);  // Creo scena a partire dal Parent
            stage.setScene(scene);          // Imposto la scena sullo stage
            stage.show();                   // Mostro la scena (nuova)
        } catch (IOException e) {
            // Gestione dell'errore durante il caricamento della scena
            handleSceneLoadError(e);
        }
    }

    public void textPopUp(ActionEvent event, String text, boolean back) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlFileName.POP_UP_FXML));
            Parent root = loader.load();

            // Ottieni l'istanza del controller
            TextPopUp controller = loader.getController();
            setAttributes(controller, null);

            // Utilizza il controller per chiamare la funzione setText
            controller.setText(text);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            if(back){
                controller.setPreviousEvent(event);
            } else {
                controller.setPreviousEvent(null);
            }

            Stage popupStage = new Stage();
            popupStage.initOwner(stage);
            popupStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (IOException e) {
            // Gestione dell'errore durante il caricamento del popup
            handleSceneLoadError(e);
        }
    }

    public void goToFilterPopUp(ActionEvent event, UserBean userBean, PlaylistBean playlistBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlFileName.POP_UP_FXML_FILTER));
            Parent root = loader.load();

            // Ottieni l'istanza del controller
            FilterCtrlGrafico controller = loader.getController();
            setAttributes(controller, userBean);

            // Stage di partenza
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Utilizza il controller per chiamare la funzione setPlaylistBean
            controller.setPlaylistBean(playlistBean);
            /* ############################# Questa può essere una funzione ################ */
            // Nuovo popUp stage
            Stage popupStage = new Stage();
            popupStage.initOwner(stage);
            popupStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (IOException e) {
            // Gestione dell'errore durante il caricamento del popup
            handleSceneLoadError(e);
        }
    }

    private void setAttributes(Object controller, UserBean userBean) {

        try {
            Method setAttributes = controller.getClass().getMethod("setAttributes", UserBean.class, SceneController.class);
            setAttributes.invoke(controller, userBean, this);
        } catch (NoSuchMethodException e) {
            try {
                Method setAttributes = controller.getClass().getMethod("setAttributes", SceneController.class);
                setAttributes.invoke(controller, this);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException error) {
                error.fillInStackTrace(); // Trattamento dell'eccezione
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.fillInStackTrace();
        }
    }
    private void handleSceneLoadError(IOException e) {
        e.printStackTrace();
    }
}

