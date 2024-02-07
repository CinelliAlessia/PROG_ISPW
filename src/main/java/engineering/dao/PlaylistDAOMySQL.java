package engineering.dao;

import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.others.*;
import engineering.query.QueryPlaylist;
import model.Playlist;
import view.utils.GenreManager;

import java.sql.*;
import java.util.*;

public class PlaylistDAOMySQL implements PlaylistDAO {

    /** Inserimento di una playlist in db. Viene prima controllato che non ci sia già il link all'interno del DB, successivamente inserisce */
    public boolean insertPlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;
        boolean result;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink);

            if (rs.next()) {
                throw new PlaylistLinkAlreadyInUse("This link is already in use!");
            }
            rs.close();

            QueryPlaylist.insertPlaylist(stmt, playlist);
            result = true;
        } catch (PlaylistLinkAlreadyInUse | SQLException e) {
            e.fillInStackTrace();
            result = false;
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
        return result;
    }

    /**
     * @param playlist, utilizzato per il link per poter fare la retrieve
     * @return playlist modificata, non serve creare una nuova istanza.
     */
    public Playlist approvePlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

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
            e.fillInStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
        return playlist;
    }

    public List<Playlist> retrievePlaylistsByEmail(String email) {
        Statement stmt = null;
        Connection conn;
        List<Playlist> playlists = null;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryPlaylist.retrievePlaylistUserByEmail(stmt,email);

            playlists = new ArrayList<>(); // Una lista di playlist

            List<String> genres; // Una lista per i generi musicali

            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setUsername(rs.getString("username"));
                playlist.setLink(rs.getString("link"));
                playlist.setEmail(rs.getString("email"));
                playlist.setPlaylistName(rs.getString("nomePlaylist"));
                playlist.setApproved(rs.getBoolean("approved"));
                
                genres = GenreManager.retriveGenre(rs);
                playlist.setPlaylistGenre(genres);
                playlists.add(playlist);
            }
            rs.close();
        }
        catch (SQLException e){
            e.fillInStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return playlists;
    }

    public List<Playlist> retrievePendingPlaylists() {
        return retrievePlaylists("pending",null);
    }
    public List<Playlist> retrieveApprovedPlaylists() {
        return retrievePlaylists("approved",null);
    }
    public List<Playlist> searchPlaylistString(Playlist playlist) {
        return retrievePlaylists("searchWord",playlist);
    }

    private List<Playlist> retrievePlaylists(String s,Playlist p){
        Statement stmt = null;
        Connection conn;
        List<Playlist> playlists = new ArrayList<>(); // Initialize the list here
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            switch (s){
                case "pending":  //Recupera tutte le playlist da approvare (Per il gestore delle playlist)
                    rs = QueryPlaylist.retrievePendingPlaylists(stmt);
                    break;
                case "approved": //Recupera tutte le playlist già approvare (Per la home page)
                    rs = QueryPlaylist.retrieveApprovedPlaylists(stmt);
                    break;
                case "searchWord":
                    rs = QueryPlaylist.searchPlaylistString(stmt,p.getPlaylistName());
                    break;
                default:
                    break;
            }

            while (rs.next()) {

                Playlist playlist = new Playlist();

                playlist.setLink(rs.getString("link"));
                playlist.setUsername(rs.getString("username"));
                playlist.setEmail(rs.getString("email"));
                playlist.setPlaylistName(rs.getString("nomePlaylist"));

                List<String> genres = GenreManager.retriveGenre(rs);
                playlist.setPlaylistGenre(genres);
                playlists.add(playlist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return playlists;
    }

    public void deletePlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

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
            e.fillInStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
    }

    public List<Playlist> retrievePlaylistsByGenre(List<String> genres) {
        //TODO
        return Collections.emptyList();
    }


}