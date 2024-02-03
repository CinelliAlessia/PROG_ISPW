package engineering.others;

import javafx.scene.control.CheckBox;
import java.sql.*;
import java.util.*;

public class GenreManager {

    private GenreManager(){}

    /** Funzione ausiliare per il retrieve dell'utente da persistenza
     * è ammessa la non gestione di SQLException dato che viene utilizzata solo da questa classe */
    public static List<String> retriveGenre(ResultSet rs) throws SQLException {

        List<String> genre = new ArrayList<>();
        if (rs.getBoolean("Pop")) genre.add("Pop");
        if (rs.getBoolean("Indie")) genre.add("Indie");
        if (rs.getBoolean("Classic")) genre.add("Classic");
        if (rs.getBoolean("Rock")) genre.add("Rock");
        if (rs.getBoolean("Electronic")) genre.add("Electronic");
        if (rs.getBoolean("House")) genre.add("House");
        if (rs.getBoolean("HipHop")) genre.add("HipHop");
        if (rs.getBoolean("Jazz")) genre.add("Jazz");
        if (rs.getBoolean("Acoustic")) genre.add("Acoustic");
        if (rs.getBoolean("REB")) genre.add("REB");
        if (rs.getBoolean("Country")) genre.add("Country");
        if (rs.getBoolean("Alternative")) genre.add("Alternative");

        rs.close();
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
