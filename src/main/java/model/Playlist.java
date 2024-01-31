package model;

import java.util.List;

public class Playlist {
    private int id;
    private String playlistName;
    private String link;
    private String username;
    private String email;
    private List<String> playlistGenre;

    public Playlist(){

    }
    public Playlist(String email, String username, String playlistName, String link, List<String> playlistGenre){
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
    }

    public Playlist(String email, String username, String playlistName, String link, List<String> playlistGenre, int id){ //SI DOVRA FARE UN
        // UNICO COSTRUTTORE NON DUE; L?HO FATTO PER VEDERE SE FUNZIONA; L'ID MI SERVE NEL DB
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
        setId(id);
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
