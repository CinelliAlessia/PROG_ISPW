package engineering.bean;

import java.util.ArrayList;

public class UserBean {
    private String username, email, password;
    private ArrayList<String> preferences;
    private boolean supervisor, registered;

    public UserBean() {
        registered = false;
        supervisor = false;
    }

    public UserBean(String nome, String email,ArrayList<String> preferences, boolean isSupervisor /*,boolean isRegistered */) {
        setUsername(nome);
        setEmail(email);
        setPreferences(preferences);
        supervisor = isSupervisor;
        //registered = isRegistered;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String nome) {
        this.username = nome;
    }

    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    public void setSupervisor() {
        this.supervisor = true;
    }

    public void setRegistered() {
        this.registered = true;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public boolean isRegistered() {
        return registered;
    }
}
