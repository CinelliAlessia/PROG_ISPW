package view.first.utils;

import engineering.bean.PlaylistBean;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ImageButtonTableCell extends TableCell<PlaylistBean, Boolean> {
        private final ImageView imageView = new ImageView();

        public ImageButtonTableCell(){

            // Crea un'ImageView per l'immagine
            imageView.setFitWidth(35);  // Imposta la larghezza desiderata dell'immagine
            imageView.setFitHeight(35); // Imposta l'altezza desiderata dell'immagine

            // Crea un pulsante con l'ImageView
            Button button = new Button();
            button.getStyleClass().clear();
            button.setGraphic(imageView);
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                // Assegna l'immagine in base al valore booleano
                if (Boolean.TRUE.equals(item)) {
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/photo/green_dot.png")).toExternalForm()));
                } else {
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/photo/orange_dot.png")).toExternalForm()));
                }
                setGraphic(imageView);
            }
        }
    }

