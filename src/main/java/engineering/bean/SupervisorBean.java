package engineering.bean;

import model.User;

import java.util.List;

public class SupervisorBean extends UserBean {
    public SupervisorBean(){}
    public SupervisorBean(String username, String email, List<String> preferences) {
        super(username, email, preferences);
        supervisor = true;
    }
}
