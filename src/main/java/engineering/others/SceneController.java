package engineering.others;

import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import view.SaveGenrePopUp;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/** SINGLETON */
//############### VERIFICARE SE E' GIUSTO CHE SIA SINGLETON -----> NO SBAGLIATO ###################
public class SceneController {
    private static SceneController sceneController = null;

    private static final Deque<Scene> sceneStack = new LinkedList<>();
    // Sonar dice che non va bene: private static final Stack<Scene> sceneStack = new Stack<>(); la modifichiamo?

    public static SceneController getInstance() {
        //Pattern Singleton
        if (sceneController == null) {
            sceneController = new SceneController();
        }
        return sceneController;
    }

    @FXML
    public void goBack(ActionEvent event) {
        if (!sceneStack.isEmpty()) {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(sceneStack.pop()); // Pop the last scene from stack
            stage.show();
        }
    }

    @FXML
    public void pushCurrentScene(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack
    }

    @FXML
    public void goToScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack

        Scene scene = new Scene(root);  // Creo scena a partire dal Parent
        stage.setScene(scene);      // Imposto la scena sullo stage
        stage.show();      // Mostro la scena (nuova)
    }

    @FXML
    public <T> void goToScene(ActionEvent event, String fxmlPath, UserBean userBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        T controller = loader.getController();
        setUserBean(controller, userBean);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene()); // Push current scene onto stack

        Scene scene = new Scene(root);  // Creo scena a partire dal Parent
        stage.setScene(scene);          // Imposto la scena sullo stage
        stage.show();                   // Mostro la scena (nuova)
    }

    private void setUserBean(Object controller, UserBean userBean) {
        try {
            Method setUserBeanMethod = controller.getClass().getMethod("setUserBean", UserBean.class);
            setUserBeanMethod.invoke(controller, userBean);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.fillInStackTrace(); // Trattamento dell'eccezione
        }
    }

    public void popUp(ActionEvent event, String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/saveGenrePopUp.fxml"));
        Parent root = loader.load();

        // Ottieni l'istanza del controller
        SaveGenrePopUp controller = loader.getController();

        // Utilizza il controller per chiamare la funzione setText
        controller.setText(text);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Stage popupStage = new Stage();
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        popupStage.showAndWait();
    }
}

