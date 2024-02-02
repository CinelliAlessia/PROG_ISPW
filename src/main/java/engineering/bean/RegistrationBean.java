package engineering.bean;

import java.util.List;

public class RegistrationBean extends UserBean {
    private String password;

    /** Differenziata dalla UserBean per non mantenere la password nel sistema  */
    public RegistrationBean(String username, String email, String password, List<String> preferences, boolean isSupervisor){
        super(username, email, preferences, isSupervisor);
        setPassword(password);
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
