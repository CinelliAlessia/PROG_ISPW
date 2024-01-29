package engineering.bean;

import java.util.ArrayList;

public class RegistrationBean extends UserBean {
    private String password;
    public RegistrationBean(String nome, String email, String password, ArrayList<String> preferences, boolean isSupervisor /*,boolean isRegistered */){
        super(nome, email,preferences, isSupervisor /*,boolean isRegistered */);
        setPassword(password);
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
