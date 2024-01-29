package model;

import java.util.ArrayList;

public class Playlist {
    private String playlistName, link, username, email;
    private ArrayList<String> playlist_genre;

    public Playlist(){

    }
    public Playlist(String playlistName,String link, String username, String email,ArrayList<String> playlist_genre){
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

    public void setPlaylist_genre(ArrayList<String> playlist_genre) {
        this.playlist_genre = playlist_genre;
    }

    public ArrayList<String> getPlaylist_genre() {
        return playlist_genre;
    }
}
