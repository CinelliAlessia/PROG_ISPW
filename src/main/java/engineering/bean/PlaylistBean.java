package engineering.bean;

import engineering.exceptions.LinkIsNotValid;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.List;

public class PlaylistBean {
    private String username;
    private String email;
    private String link;
    private String playlistName;
    private List<String> playlistGenre;
    private List<Integer> emotional;
    private boolean approved = false;
    private String id;

    public PlaylistBean() {
    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved) throws LinkIsNotValid {
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylistGenre(playlistGenre);
        setApproved(approved);
    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved, String id) throws LinkIsNotValid {
        this(email,username,playlistName,link, playlistGenre, approved);
        setId(id);
    }

    public PlaylistBean(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved, List<Integer> emotional) throws LinkIsNotValid {
        this(email,username,playlistName,link, playlistGenre, approved);
        setEmotional(emotional);
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setLink(String link) throws LinkIsNotValid {
        if(isValidLink(link)){
            this.link = link;
        } else {
            throw new LinkIsNotValid();
        }
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPlaylistGenre(List<String> playlistGenre) {
        this.playlistGenre = playlistGenre;
    }
    public void setApproved(boolean approved){
        this.approved = approved;
    }
    public void setEmotional(List<Integer> emotional) {
        this.emotional = emotional;
    }

    public String getLink() {
        return link;
    }
    public String getUsername() {
        return username;
    }
    public String getPlaylistName() {
        return playlistName;
    }
    public String getEmail() {
        return email;
    }
    public List<String> getPlaylistGenre() {
        return playlistGenre;
    }
    public boolean getApproved(){
        return approved;
    }
    public String getId() {
        return id;
    }
    public List<Integer> getEmotional() {
        return emotional;
    }


    private boolean isValidLink(String input) {
        UrlValidator urlValidator = new UrlValidator();
        //return urlValidator.isValid(input);
        return true;
    }
}
