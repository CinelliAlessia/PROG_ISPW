package engineering.bean;

import java.util.ArrayList;
import java.util.List;

public class PlaylistBean {
    private String username;
    private String email;
    private String link;
    private String playlistName;
    private List<String> playlistGenre;
    private boolean approved = false;


    public PlaylistBean(){

    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre){
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
    }
    public void setLink(String link) {
        this.link = link;
    }

    public void setApproved(){
        this.approved = true;
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

    public void setPlaylistGenre(List<String> playlistGenre) {
        this.playlistGenre = playlistGenre;
    }

    public List<String> getPlaylistGenre() {
        return playlistGenre;
    }
}
