package engineering.bean;

import java.util.List;

public class PlaylistBean {
    private String username;
    private String email;
    private String link;
    private String playlistName;
    private List<String> playlistGenre;
    private boolean approved = false;

    private String id;

    public PlaylistBean(){

    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved, String id) {
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
        setApproved(approved);
        setId(id);
    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved) {
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
        setApproved(approved);
    }

    private void setId(String id) {
        this.id = id;
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

    public void setPlaylistGenre(List<String> playlistGenre) {
        this.playlistGenre = playlistGenre;
    }

    public List<String> getPlaylistGenre() {
        return playlistGenre;
    }

    public void setApproved(boolean approved){
        this.approved = approved;
    }

    public boolean getApproved(){
        return approved;
    }

    public String getId() {
        return id;
    }
}
