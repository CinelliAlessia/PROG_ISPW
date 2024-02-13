package model;

import java.util.List;

public class Login extends Client {
    private String password;

    /** Utilizzato in fase di login */
    public Login(String email, String password){
        setEmail(email);
        this.password = password;
    }

    /** Utilizzato in fase di registrazione */
    public Login(String username, String email, String password, List<String> preferences){
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
