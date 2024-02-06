package engineering.bean;

import java.util.List;

public class UserBean {
    private String username;
    private String email;
    private List<String> preferences;

    protected boolean supervisor = false;

    public UserBean() {

    }
    public UserBean(String username, String email, List<String> preferences) {
        setUsername(username);
        setEmail(email);
        setPreferences(preferences);
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
