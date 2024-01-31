package engineering.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    /* ---------- QUERY LOGIN ---------- */
    public static final String INSERT_USER_QUERY = "INSERT INTO user (email, username, password) VALUES ('%s','%s','%s')";
    public static final String INSERT_GENERI_MUSICALI_QUERY = "INSERT INTO generi_musicali (id, Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative, email) VALUES (0, %s)";
    public static final String SELECT_EMAIL_USER_QUERY = "SELECT * FROM user WHERE email = '%s'";
    public static final String SELECT_PASSWORD_QUERY = "SELECT password FROM user WHERE email = '%s'";

    /* ---------- QUERY PLAYLIST ---------- */
    public static final String INSERT_PLAYLIST_QUERY = "INSERT INTO playlist_utente (email, nome_utente, nome_playlist, link, id_playlist_genre) VALUES ('%s','%s','%s','%s','%d')";
    public static final String INSERT_ALL_PLAYLIST_QUERY = "INSERT INTO all_playlist (nome_playlist, link) VALUES ('%s','%s')";

    public static final String INSERT_GENERI_MUSICALI_PLAYLIST_QUERY = "INSERT INTO generi_musicali (id, Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative, email) VALUES (%s)";
    public static final String SELECT_LINK_QUERY = "SELECT * FROM all_playlist WHERE link = '%s'";

    public static final String SELECT_PLAYLIST_BY_USER = "SELECT * FROM playlist_utente";

    public static final String SELECT_GENRED_USER_QUERY = "SELECT * FROM generi_musicali WHERE email = '%s'";
}
