package view.utils;

import javafx.scene.control.CheckBox;
import java.sql.*;
import java.util.*;

public class GenreManager {

    private GenreManager(){}

    /** Funzione ausiliare per il retrieve dell'utente da persistenza
     * è ammessa la non gestione di SQLException dato che verrà gestita da chi usa questo metodo */
    public static List<String> retriveGenre(ResultSet rs) throws SQLException {
        List<String> genre = new ArrayList<>();
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};

        for (String genreName : genres) {
            if (rs.getBoolean(genreName)) {
                genre.add(genreName);
            }
        }

        return genre;
    }


    public static List<String> retrieveCheckList(List<CheckBox> checkBoxList) {
        ArrayList<String> selectedGenres = new ArrayList<>();

        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                selectedGenres.add(checkBox.getText());
            }
        }

        return selectedGenres;
    }

}
