package engineering.bean;

import java.util.List;

public class RegistrationBean extends UserBean {
    private String password;
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
