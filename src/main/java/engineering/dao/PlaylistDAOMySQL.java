package engineering.dao;

import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.others.*;
import engineering.query.QueryPlaylist;
import model.Playlist;
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
    @Override
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

    public List<Playlist> retrivePlaylistByUsername(String username) {
        Statement stmt = null;
        Connection conn;
        List<Playlist> playlists = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryPlaylist.retrivePlaylistUser(stmt,username);

            playlists = new ArrayList<>(); // Una lista di playlist

            List<String> genres = null; // Una lista per i generi musicali

            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setUsername(rs.getString("username"));
                playlist.setLink(rs.getString("link"));
                playlist.setEmail(rs.getString("email"));
                playlist.setPlaylistName(rs.getString("nomePlaylist"));

                rs2 = QueryPlaylist.retriveGenrePlaylist(stmt,username);

                if(rs2.next()){
                    genres = GenreManager.retriveGenre(rs2);
                    playlist.setPlaylistGenre(genres);
                    playlists.add(playlist);
                }
                rs2.close();
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
            } catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return playlists;
    }

    /**
     * @return la lista di tutte le playlist
     */
    @Override
    public List<Playlist> retriveAllPlaylistToApprove() {
        Statement stmt = null;
        Connection conn;
        List<Playlist> playlists = new ArrayList<>(); // Initialize the list here
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryPlaylist.retriveAllPlaylistToApprove(stmt);
            int i = 0;

            while (rs.next()) {
                System.out.println("Ciclo " + i);
                i++;

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

    public List<Playlist> retrievePlaylistsByMail(String mail) {
        //TODO
        return Collections.emptyList();
    }

    public List<Playlist> retrievePlaylistByGenre(String genre) {
        //TODO
        return Collections.emptyList();
    }


}