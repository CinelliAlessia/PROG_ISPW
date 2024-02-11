package engineering.bean;

import java.util.List;

public class SupervisorBean extends ClientBean {

    public SupervisorBean(){}

    public SupervisorBean(String email) {
        setEmail(email);
        super.supervisor = true;
    }

    public SupervisorBean(String username, String email, List<String> preferences){
        super(username, email, preferences);
        super.supervisor = true;
    }
}
