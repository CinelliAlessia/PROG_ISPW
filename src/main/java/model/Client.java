package model;

import java.util.List;

/** Rappresenta la base di ogni utilizzatore della piattaforma */
public abstract class Client {

    private String email;
    private String username;
    private List<String> preferences;

    protected boolean supervisor;

    protected Client(){
    }
    protected Client(String username, String email, List<String> preferences){
        setUsername(username);
        setEmail(email);
        setPreferences(preferences);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
    public List<String> getPreferences() {
        return preferences;
    }

    public boolean isSupervisor() {
        return supervisor;
    }
}
