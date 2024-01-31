package engineering.bean;

import java.util.List;

public class UserBean {
    private String username, email, password;
    private List<String> preferences;
    private boolean supervisor;

    public UserBean() {
        supervisor = false;
    }

    public UserBean(String username, String email,List<String> preferences, boolean isSupervisor, boolean isRegistered) {
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

    public void setPreferences(List<String> preferences) {
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

    public List<String> getPreferences() {
        return preferences;
    }


}
