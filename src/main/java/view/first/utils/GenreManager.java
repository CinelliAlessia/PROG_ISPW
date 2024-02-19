package view.first.utils;

import javafx.scene.control.CheckBox;
import java.sql.*;
import java.util.*;

public class GenreManager {

    private GenreManager(){}

    private static final String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};


    /** Funzione ausiliare per il retrieve dei generi musicali dell'utente da persistenza
     * è ammessa la non gestione di SQLException dato che verrà gestita da chi usa questo metodo */
    public static List<String> retriveGenre(ResultSet rs) throws SQLException {
        List<String> genre = new ArrayList<>();

        for (String genreName : genres) {
            if (rs.getBoolean(genreName)) {
                genre.add(genreName);
            }
        }

        return genre;
    }

    public static void setCheckList(List<String> preferences, List<CheckBox> checkBoxList){
        for (int i = 0; i < genres.length; i++) {
            CheckBox checkBox = checkBoxList.get(i); // Supponendo che checkBoxList sia una List<CheckBox> inizializzata precedentemente
            checkBox.setSelected(preferences.contains(genres[i]));
        }
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
