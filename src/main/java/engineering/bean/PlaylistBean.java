package engineering.bean;

import model.Playlist;

import java.security.PublicKey;
import java.util.ArrayList;

public class PlaylistBean {
    private String username, email, link, playlistName;
    private ArrayList<String> playlist_genre;

    public PlaylistBean(){

    }

    public PlaylistBean(String email, String username, String playlistName, String link, ArrayList<String> playlist_genre){
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
