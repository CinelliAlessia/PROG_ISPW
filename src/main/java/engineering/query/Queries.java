package engineering.query;

public class Queries {



    private Queries(){}

    /* ---------- QUERY LOGIN ---------- */
    public static final String INSERT_USER = "INSERT INTO user (email, username, password, supervisor) VALUES ('%s','%s','%s','%d')";
    public static final String INSERT_GENERI_MUSICALI_USER = "INSERT INTO generi_musicali_user (Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative, email) VALUES (%s)";


    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = '%s'";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = '%s'";
    public static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM user WHERE email = '%s'";
    public static final String SELECT_GENRE_USER_QUERY = "SELECT * FROM generi_musicali_user WHERE email = '%s'";


    public static final String UPDATE_GENERI_MUSICALI_USER =
            "UPDATE generi_musicali_user SET " +
                    "Pop = %d, Indie = %d, Classic = %d, Rock = %d, Electronic = %d, " +
                    "House = %d, HipHop = %d, Jazz = %d, Acoustic = %d, REB = %d, " +
                    "Country = %d, Alternative = %d " +
                    "WHERE email = '%s'";



    /* ---------- QUERY PLAYLIST ---------- */
    public static final String INSERT_PLAYLIST_USER = "INSERT INTO playlist_utente (nomePlaylist, email, username, link, approved, Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative) VALUES ('%s','%s','%s','%s','%d', %s)";
    public static final String INSERT_GENERI_MUSICALI_PLAYLIST = "INSERT INTO playlist_utente (Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative) VALUES (%s)";
    public static final String INSERT_ALL_PLAYLIST_QUERY = "INSERT INTO all_playlist (namePlaylist, link, approved) VALUES ('%s','%s','%d')";


    public static final String SELECT_LINK_QUERY = "SELECT * FROM all_playlist WHERE link = '%s'";
    public static final String SELECT_PLAYLIST_BY_USER = "SELECT * FROM playlist_utente WHERE username = '%s'"; // Recupero tutto ma non uso tutto
    public static final String SELECT_ALL_PLAYLIST = "SELECT * FROM playlist_utente"; // Recupero tutto ma non uso tutto
    public static final String SELECT_PLAYLIST_TO_APPROVE = "SELECT * FROM playlist_utente WHERE approved = '%d'";
    public static final String SELECT_GENRE_PLAYLIST_BY_ID = "SELECT * FROM generi_musicali WHERE id = '%d'";
    public static final String SELECT_ID_BY_EMAIL = "SELECT id FROM playlist_utente WHERE email = '%s'";
    public static final String SELECT_GENRE_PLAYLIST = "SELECT Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative " +
            "FROM playlist_utente " +
            "WHERE username = '?'";
    public static final String SELECT_GENRE_PLAYLIST_BY_LINK = "SELECT Pop, Indie, Classic, Rock, Electronic, House, HipHop, Jazz, Acoustic, REB, Country, Alternative " +
            "FROM playlist_utente " +
            "WHERE link = '?'";


    public static final String UPDATE_APPROVE_PLAYLIST = "UPDATE playlist_utente SET approved = '%d' WHERE link = '%s' ";
    public static final String UPDATE_APPROVE_PLAYLIST_IN_ALL = "UPDATE all_playlist SET approved = '%d' WHERE link = '%s' ";


    public static final String DELETE_PLAYLIST_BY_LINK_PLAYLIST_UTENTE = "DELETE * FROM playlist_utente WHERE link = '%s'" ;
    public static final String DELETE_PLAYLIST_BY_LINK_ALL_PLAYLIST = "DELETE * FROM all_playlist WHERE link = '%s'" ;


}
