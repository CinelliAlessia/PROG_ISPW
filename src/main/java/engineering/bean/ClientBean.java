package engineering.bean;

import java.util.List;

public abstract class ClientBean {

    private String username;
    private String email;
    private List<String> preferences;

    protected boolean supervisor;

    protected ClientBean() {
    }

    protected ClientBean(String username, String email, List<String> preferences) {
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


    public void setUsername(String nome) {
        this.username = nome;
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
