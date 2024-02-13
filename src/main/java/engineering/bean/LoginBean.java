package engineering.bean;

import engineering.exceptions.InvalidEmailException;

import java.util.List;

/** Differenziata dalla UserBean per non mantenere la password nel sistema  */
public class LoginBean extends ClientBean {
    private String password;

    public LoginBean(){}

    /** Utilizzato in fase di login */
    public LoginBean(String email, String password) throws InvalidEmailException {
        setEmail(email);
        setPassword(password);
    }

    /** Utilizzato in fase di registrazione */
    public LoginBean(String username, String email, String password, List<String> preferences) throws InvalidEmailException {
        super(username, email, preferences);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
