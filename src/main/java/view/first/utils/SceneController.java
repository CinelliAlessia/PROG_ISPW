package view.first.utils;

import engineering.bean.*;
import engineering.others.CLIPrinter;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import view.first.FilterCtrlGrafico;
import view.first.TextPopUp;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

public class SceneController {
    private final Deque<Scene> sceneStack;
    private static final String SET_ATTRIBUTES = "setAttributes";
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
    public <T> void goToScene(ActionEvent event, String fxmlPath, ClientBean clientBean, PlaylistBean playlistBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            setAttributes(controller, clientBean, playlistBean);

            switchScene(event, root);
        } catch (IOException e) {
            handleSceneLoadError(e);
        }
    }

    @FXML
    public void goToScene(ActionEvent event, String fxmlPath, ClientBean clientBean) {
        goToScene(event, fxmlPath, clientBean, null);
    }

    @FXML
    public void goToScene(ActionEvent event, String fxmlPath, PlaylistBean playlistBean) {
        goToScene(event, fxmlPath, null, playlistBean);
    }

    @FXML
    public void goToScene(ActionEvent event, String fxmlPath) {
        goToScene(event, fxmlPath, null, null);
    }

    private void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void setAttributes(Object controller, ClientBean clientBean, PlaylistBean playlistBean) {
        Class<?>[] parameterTypes = {ClientBean.class, PlaylistBean.class};

        for (Class<?> paramType : parameterTypes) { // Prova setAttributes(ClientBean, SceneController) e  Prova setAttributes(PlaylistBean, SceneController)
            try {
                Method setAttributes = controller.getClass().getMethod(SET_ATTRIBUTES, paramType, SceneController.class);
                setAttributes.invoke(controller, paramType == ClientBean.class ? clientBean : playlistBean, this);
                return; // Esce dal metodo se trova una firma valida
            } catch (IllegalAccessException | InvocationTargetException e) {
                handleSceneLoadError(e);
                return; // Esce dal metodo in caso di eccezione
            } catch (NoSuchMethodException ignored) {
                // Ignorato perché cercherà la prossima firma se questa non è presente
            }
        }

        try{
            Method setAttributes = controller.getClass().getMethod(SET_ATTRIBUTES, ClientBean.class, PlaylistBean.class, SceneController.class);
            setAttributes.invoke(controller,clientBean, playlistBean, this);
            return; // Esce dal metodo se trova una firma valida
        } catch (NoSuchMethodException e) {
            // Ignorato di proposito
        } catch (IllegalAccessException | InvocationTargetException e) {
            handleSceneLoadError(e);
        }

        try {
            Method setAttributes = controller.getClass().getMethod(SET_ATTRIBUTES, SceneController.class);
            setAttributes.invoke(controller, this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            handleSceneLoadError(e);
        }

    }


    public void textPopUp(ActionEvent event, String text, boolean back) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlFileName.POP_UP_FXML));
            Parent root = loader.load();

            // Ottieni l'istanza del controller
            TextPopUp controller = loader.getController();
            setAttributes(controller, null,null);

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

    public void goToFilterPopUp(ActionEvent event, ClientBean clientBean, PlaylistBean playlistBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlFileName.POP_UP_FXML_FILTER));
            Parent root = loader.load();

            // Ottieni l'istanza del controller
            FilterCtrlGrafico controller = loader.getController();
            setAttributes(controller, clientBean,playlistBean);

            // Stage di partenza
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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

    private void handleSceneLoadError(Exception e) {
        CLIPrinter.errorPrint(String.format("SceneController: %s", e.getMessage()));
    }

}

