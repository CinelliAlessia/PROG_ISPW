//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package engineering.query;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryLogin {
    private QueryLogin() {
    }

    // Metodo di INSERIMENTO in database
    public static void registerUser(Statement stmt, User user) throws SQLException {
        String name = user.getNome();
        String email = user.getEmail();
        String pw = user.getPass();
        String insertStatement = String.format("INSERT INTO User (username, email, password) VALUES ('%s','%s','%s')", name, email, pw);
        stmt.executeUpdate(insertStatement);
    }

    // Query per prendere la email, utilizzata nella registrazione per vedere se l'email è già registrata
    public static ResultSet loginUser(Statement stmt, String username) throws SQLException {
        String sql = "SELECT * FROM User WHERE Username = '" + username + "';";
        return stmt.executeQuery(sql);
    }

    // Query per prendere la password della email passata come argomento
    public static ResultSet getUserPassword(Statement stmt, String email) throws SQLException {
        String query = "SELECT password FROM user WHERE email = '" + email + "'";
        return stmt.executeQuery(query);
    }
}
