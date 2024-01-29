package engineering.bean;

import java.util.ArrayList;

public class UserBean {
    private String userName;
    private String email;
    private ArrayList<String> preferences;
    private boolean supervisor;
    private boolean registered;

    public UserBean() {
        registered = false;
        supervisor = false;
    }

    public UserBean(String nome, String email, ArrayList<String> preferences, boolean isSupervisor, boolean isRegistered) {
        setUserName(nome);
        setEmail(email);
        setPreferences(preferences);
        supervisor = isSupervisor;
        registered = isRegistered;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String nome) {
        this.userName = nome;
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

    public String getUserName() {
        return userName;
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
