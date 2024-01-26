//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package engineering.query;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryLogin {
    private QueryLogin() {
    }

    // Metodo di INSERIMENTO in database
    public static void registerUser(Statement stmt, User user) throws SQLException {
        String name = user.getNome();
        String email = user.getEmail();
        String pw = user.getPass();
        String insertStatement = String.format("INSERT INTO User (username, email, password) VALUES ('%s','%s','%s')", name, email, pw);
        insertGeneriMusicali(stmt,email,user.getPref());
        stmt.executeUpdate(insertStatement);
    }

    public static void insertGeneriMusicali(Statement stmt, String userEmail, ArrayList<String> generiMusicali) throws SQLException {
        // Costruisci la query di inserimento
        StringBuilder query = new StringBuilder("INSERT INTO generimusicali (user_email, Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, Reb, Country, Alternative) VALUES ");
        query.append(String.format("('%s',", userEmail));

        // Aggiungi i valori booleani alla query
        for (String genere : generiMusicali) {
            query.append(String.format(" '%d',", generiMusicali.contains(genere) ? 1 : 0));
        }

        // Rimuovi l'ultima virgola
        query.deleteCharAt(query.length() - 1);

        // Completa la query
        query.append(")");

        // Esegui la query
        stmt.executeUpdate(query.toString());
    }


    // Query per prendere la email, utilizzata nella registrazione per vedere se l'email è già registrata
    public static ResultSet loginUser(Statement stmt, String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE Username = '" + username + "';";
        return stmt.executeQuery(sql);
    }


    // Query per prendere la password della email passata come argomento
    public static ResultSet getUserPassword(Statement stmt, String email) throws SQLException {
        String query = "SELECT password FROM user WHERE email = '" + email + "'";
        return stmt.executeQuery(query);
    }
}
