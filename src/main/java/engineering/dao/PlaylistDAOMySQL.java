package engineering.dao;

import engineering.exceptions.*;
import engineering.others.*;
import engineering.query.*;

import model.Playlist;
import view.utils.GenreManager;

import java.sql.*;
import java.util.*;

public class PlaylistDAOMySQL implements PlaylistDAO {

    /* Stringhe dei campi nel DB my SQL */
    private static final String USERNAME = "username";
    private static final String LINK = "link";
    private static final String EMAIL = "email";
    private static final String NAME_PLAYLIST = "namePlaylist";
    private static final String APPROVED = "approved";
    private static final String PENDING = "pending";
    private static final String SEARCH_WORD = "searchWord";
    private static final String SLIDER1 = "slider1";
    private static final String SLIDER2 = "slider2";
    private static final String SLIDER3 = "slider3";
    private static final String SLIDER4 = "slider4";


    /** Inserimento di una playlist in db. Viene prima controllato che non ci sia già il link all'interno del DB, successivamente inserisce */
    public boolean insertPlaylist(Playlist playlist) throws PlaylistLinkAlreadyInUse {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        boolean result;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink);

            if (rs.next()) {
                throw new PlaylistLinkAlreadyInUse();
            }

            //Devo anche cercare il titolo per il singolo utente prima di caricare

            rs.close();

            QueryPlaylist.insertPlaylist(stmt, playlist);

            result = true;
        } catch (SQLException e) {
            handleDAOException(e);
            result = false;
        } finally {
            closeResources(stmt,rs);
        }
        return result;
    }

    /**
     * @param playlist, utilizzato per il link per poter fare la retrieve
     * @return playlist modificata, non serve creare una nuova istanza.
     */
    public Playlist approvePlaylist(Playlist playlist) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink); // Cerca in all_playlist

            if (rs.next()) {
                QueryPlaylist.approvePlaylistByLink(stmt, playlist.getLink()); // Rimuove in playlist_utente E in all_playlist
                playlist.setApproved(true); // Non mi piace, non è responsabilità sua
                rs.close();
            }

        } catch (SQLException e){
            handleDAOException(e);
        } finally {
            closeResources(stmt,rs);
        }
        return playlist;
    }

    public List<Playlist> retrievePlaylistsByEmail(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        List<Playlist> playlists = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryPlaylist.retrievePlaylistUserByEmail(stmt,email);

            playlists = new ArrayList<>(); // Una lista di playlist

            List<String> genres; // Una lista per i generi musicali

            while (rs.next()) {
                Playlist playlist = new Playlist();
                fillModelFromResultSet(playlist,rs);
                genres = GenreManager.retriveGenre(rs);
                playlist.setPlaylistGenre(genres);
                playlists.add(playlist);
            }
            rs.close();
        }
        catch (SQLException e){
            handleDAOException(e);
        }
        finally {
            closeResources(stmt,rs);
        }
        return playlists;
    }
    public List<Playlist> retrievePendingPlaylists() {
        return retrievePlaylists(PENDING,null);
    }
    public List<Playlist> retrieveApprovedPlaylists() {
        return retrievePlaylists(APPROVED,null);
    }
    public List<Playlist> searchPlaylistString(Playlist playlist) {
        return retrievePlaylists(SEARCH_WORD,playlist);
    }

    private List<Playlist> retrievePlaylists(String s,Playlist p){
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        List<Playlist> playlists = new ArrayList<>(); // Initialize the list here

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            switch (s){
                case PENDING:  //Recupera tutte le playlist da approvare (Per il gestore delle playlist)
                    rs = QueryPlaylist.retrievePendingPlaylists(stmt);
                    break;
                case APPROVED: //Recupera tutte le playlist già approvare (Per la home page)
                    rs = QueryPlaylist.retrieveApprovedPlaylists(stmt);
                    break;
                case SEARCH_WORD:
                    if(p != null){
                        rs = QueryPlaylist.searchPlaylistString(stmt,p.getPlaylistName());
                    }
                    break;
                default:
                    break;
            }

            while (true) {
                assert rs != null;
                if (!rs.next()) break;

                Playlist playlist = new Playlist();
                fillModelFromResultSet(playlist,rs);
                List<String> genres = GenreManager.retriveGenre(rs);
                playlist.setPlaylistGenre(genres);
                playlists.add(playlist);
            }

            System.err.println("PlaylistDAOMySQL: playlist trovate " + playlists);


        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt,rs);
        }
        return playlists;
    }

    public void deletePlaylist(Playlist playlist) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink); // Cerca in all_playlist

            if (rs.next()) {
                QueryPlaylist.removePlaylistByLink(stmt, playlist.getLink()); // Rimuove in playlist_utente E in all_playlist
                rs.close();
            }

        } catch (SQLException e){
            handleDAOException(e);
        } finally {
            closeResources(stmt,rs);
        }
    }

    public List<Playlist> searchPlaylistByFilters(Playlist playlistSearch) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn;

        List<Playlist> playlists = new ArrayList<>(); // Initialize the list here

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryPlaylist.searchPlaylistsByFilter(stmt,playlistSearch);

            while (true) {
                assert rs != null;
                if (!rs.next()) break;

                Playlist playlist = new Playlist();
                fillModelFromResultSet(playlist,rs);
                playlist.setPlaylistGenre(playlistSearch.getPlaylistGenre());
                playlist.setEmotional(playlistSearch.getEmotional());
                playlists.add(playlist);
            }

            System.err.println("PlaylistDAOMySQL: playlist trovate " + playlists);


        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt,rs);
        }
        return playlists;
    }

    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }

    private void handleDAOException(Exception e) {
        e.printStackTrace();
    }

    /** Imposta i campi username, email, link, nome Playlist e approved */
    private void fillModelFromResultSet(Playlist playlist, ResultSet rs) throws SQLException {
        playlist.setLink(rs.getString(LINK));
        playlist.setUsername(rs.getString(USERNAME));
        playlist.setEmail(rs.getString(EMAIL));
        playlist.setPlaylistName(rs.getString(NAME_PLAYLIST));
        playlist.setApproved(rs.getBoolean(APPROVED));
    }

}