package engineering.dao;

import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.others.*;
import engineering.query.QueryPlaylist;
import model.Playlist;
import java.sql.*;
import java.util.*;

public class PlaylistDAOMySQL implements PlaylistDAO {

    /** Inserimento di una playlist in db. Viene prima controllato che non ci sia già il link all'interno del DB, successivamente inserisce */
    public void insertPlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink);

            if (rs.next()) {
                throw new PlaylistLinkAlreadyInUse("This link is already in use!");
            }

            QueryPlaylist.insertPlaylist(stmt, playlist);

        } catch (PlaylistLinkAlreadyInUse | SQLException e) {
            e.fillInStackTrace();
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
    }

    public List<Playlist> retrivePlaylistByUsername(String username) {
        Statement stmt = null;
        Connection conn;
        List<Playlist> playlists = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            ResultSet rs = QueryPlaylist.retrivePlaylistUser(stmt,username);

            playlists = new ArrayList<>(); // Una lista di playlist

            List<String> genres; // Una lista per i generi musicali

            ResultSet rs2;

            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setUsername(rs.getString("username"));
                playlist.setLink(rs.getString("link"));
                playlist.setEmail(rs.getString("email"));
                playlist.setPlaylistName(rs.getString("nomePlaylist"));

                rs2 = QueryPlaylist.retriveGenrePlaylist(stmt,username);
                genres = GenreManager.retriveGenre(rs2);

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
            } catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return playlists;
    }


    public void deletePlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink); // Cerca in all_playlist

            if (rs.next()) {
                QueryPlaylist.removePlaylistByLink(stmt, playlist.getLink()); // Rimuove in playlist_utente E in all_playlist
            }

        } catch (SQLException e){
            e.fillInStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
    }

    public void retrievePlaylistByMail(String mail) {
        //TODO
    }

    public void retrievePlaylistByGenre(String genre) {
        //TODO
    }


}