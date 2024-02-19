package engineering.bean;

import engineering.exceptions.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public abstract class ClientBean {

    private String username;
    private String email;
    private List<String> preferences;

    protected boolean supervisor;

    protected ClientBean() {
    }

    protected ClientBean(String username, String email, List<String> preferences) throws InvalidEmailException {
        setUsername(username);
        setEmail(email);
        setPreferences(preferences);
    }

    public void setEmail(String email) throws InvalidEmailException {
        if(checkMailCorrectness(email)){
            this.email = email;
        } else {
            throw new InvalidEmailException();
        }
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

    /** Verifica se la email inserita rispetta i canoni per essere una email */
    private boolean checkMailCorrectness(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
