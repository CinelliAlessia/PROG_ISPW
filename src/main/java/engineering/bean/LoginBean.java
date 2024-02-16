package engineering.bean;

import engineering.exceptions.InvalidEmailException;

/** Differenziata dalla UserBean per non mantenere la password nel sistema  */
public class LoginBean extends ClientBean {
    private String password;

    public LoginBean(){}

    /** Utilizzato in fase di login */
    public LoginBean(String email, String password) throws InvalidEmailException {
        setEmail(email);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
