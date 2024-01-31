package engineering.bean;

import java.util.ArrayList;

public class UserBean {
    private String username, email, password;
    private ArrayList<String> preferences;
    private boolean supervisor;

    public UserBean() {
        supervisor = false;
    }

    public UserBean(String username, String email,ArrayList<String> preferences, boolean isSupervisor, boolean isRegistered) {
        setUsername(username);
        setEmail(email);
        setPreferences(preferences);
        supervisor = isSupervisor;
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
    public boolean isSupervisor() {
        return supervisor;
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


}
