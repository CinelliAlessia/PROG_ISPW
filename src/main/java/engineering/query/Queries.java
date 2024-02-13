package engineering.query;

public class Queries {



    private Queries(){}

    /* ---------- QUERY LOGIN ---------- */
    public static final String INSERT_USER = "INSERT INTO user (email, username, password, supervisor) VALUES ('%s','%s','%s','%d')";
    public static final String INSERT_GENERI_MUSICALI_USER = "INSERT INTO generi_musicali_user (Pop, Indie, Classic, Rock, Electronic, " +
            "House, HipHop, Jazz, Acoustic, REB, Country, Alternative, email) VALUES (%s)";


    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = '%s'";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = '%s'";
    public static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM user WHERE email = '%s'";
    public static final String SELECT_GENRE_USER_QUERY = "SELECT * FROM generi_musicali_user WHERE email = '%s'";

    public static final String SEARCH_USERNAME = "SELECT * FROM user WHERE username = '%s' ";
    public static final String SEARCH_EMAIL = "SELECT * FROM user WHERE email = '%s' ";

    public static final String UPDATE_GENERI_MUSICALI_USER =
            "UPDATE generi_musicali_user SET " +
                    "Pop = %d, Indie = %d, Classic = %d, Rock = %d, Electronic = %d, House = %d, " +
                    "HipHop = %d, Jazz = %d, Acoustic = %d, REB = %d, Country = %d, Alternative = %d " +
                    "WHERE email = '%s'";

    /* ---------- QUERY PLAYLIST ---------- */
    public static final String INSERT_PLAYLIST_USER = "INSERT INTO playlist_utente (namePlaylist, email, username, link, " +
            "approved, slider1, slider2, slider3, slider4, Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, " +
            "Acoustic, REB, Country, Alternative) VALUES ('%s','%s','%s','%s','%d', %s, %s)";
    public static final String INSERT_GENERI_MUSICALI_PLAYLIST = "INSERT INTO playlist_utente (Pop, Indie, Classic, Rock, " +
            "Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative) VALUES (%s)";

    public static final String SELECT_LINK_QUERY = "SELECT * FROM playlist_utente WHERE link = '%s'";
    public static final String SELECT_PLAYLIST_BY_USER = "SELECT * FROM playlist_utente WHERE username = '%s'"; // Recupero tutto ma non uso tutto
    public static final String SELECT_PLAYLIST_BY_EMAIL = "SELECT * FROM playlist_utente WHERE email = '%s'";
    public static final String SELECT_SEARCH_BY_GENRE = "SELECT * FROM playlist_utente WHERE namePlaylist LIKE '%s' AND approved = '1' " +
            "AND Pop = '%d' AND Indie = '%d' AND Classic = '%d' AND Rock = '%d' AND Electronic = '%d' " +
            "AND House = '%d' AND HipHop = '%d' AND Jazz = '%d' AND Acoustic = '%d' AND REB = '%d' AND Country = '%d' AND Alternative = '%d'";
    public static final String SELECT_SEARCH_PLAYLISTS_BY_FILTER = "SELECT * FROM playlist_utente WHERE namePlaylist LIKE '%s' " +
            "AND approved = '1' AND slider1 = '%d' AND slider2 = '%d' AND slider3 = '%d' AND slider4 = '%d' AND Pop = '%d' " +
            "AND Indie = '%d' AND Classic = '%d' AND Rock = '%d' AND Electronic = '%d' AND House = '%d' AND HipHop = '%d' " +
            "AND Jazz = '%d' AND Acoustic = '%d' AND REB = '%d' AND Country = '%d' AND Alternative = '%d'";
    public static final String SELECT_SEARCH_BY_EMOTIONAL = "SELECT * FROM playlist_utente WHERE namePlaylist LIKE '%s' " +
            "AND approved = '1' AND slider1 = '%d' AND slider2 = '%d' AND slider3 = '%d' AND slider4 = '%d'";

    public static final String SELECT_ALL_PLAYLIST = "SELECT * FROM playlist_utente"; // Recupero tutto ma non uso tutto
    public static final String SELECT_PENDING_PLAYLISTS = "SELECT * FROM playlist_utente WHERE approved = '%d'";
    public static final String SELECT_APPROVED_PLAYLISTS = "SELECT * FROM playlist_utente WHERE approved = '%d'";
    public static final String SELECT_GENRE_PLAYLIST_BY_ID = "SELECT * FROM generi_musicali WHERE id = '%d'";
    public static final String SELECT_ID_BY_EMAIL = "SELECT id FROM playlist_utente WHERE email = '%s'";
    public static final String SELECT_GENRE_PLAYLIST = "SELECT Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative " +
            "FROM playlist_utente WHERE username = '?'";
    public static final String SELECT_GENRE_PLAYLIST_BY_LINK = "SELECT Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative " +
            "FROM playlist_utente WHERE link = '?'";
    public static final String SELECT_SEARCH_PLAYLIST = "SELECT * FROM playlist_utente WHERE namePlaylist LIKE '%s' AND approved = '1'";

    public static final String UPDATE_APPROVE_PLAYLIST = "UPDATE playlist_utente SET approved = '%d' WHERE link = '%s' ";

    public static final String DELETE_PLAYLIST_BY_LINK_PLAYLIST_UTENTE = "DELETE * FROM playlist_utente WHERE link = '%s'" ;

    public static final String INSERT_NOTICE_USER = "INSERT INTO notifiche (username, title, body) VALUES ('%s','%s','%s')";
    public static final String SELECT_NOTICE_USER = "SELECT * FROM notifiche WHERE username = '%s'";

}
