package view.utils;

import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import view.TextPopUp;

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
    public <T> void goToScene(ActionEvent event, String fxmlPath, UserBean userBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        T controller = loader.getController();
        setAttributes(controller, userBean);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack

        Scene scene = new Scene(root);  // Creo scena a partire dal Parent
        stage.setScene(scene);          // Imposto la scena sullo stage
        stage.show();                   // Mostro la scena (nuova)
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

    public void popUp(ActionEvent event, String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlFileName.POP_UP_FXML));
        Parent root = loader.load();

        // Ottieni l'istanza del controller
        TextPopUp controller = loader.getController();
        setAttributes(controller, null);

        // Utilizza il controller per chiamare la funzione setText
        controller.setText(text);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousEvent(event);

        Stage popupStage = new Stage();
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        popupStage.showAndWait();
    }
}

