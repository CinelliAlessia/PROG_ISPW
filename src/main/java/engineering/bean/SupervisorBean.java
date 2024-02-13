package engineering.bean;

import engineering.exceptions.InvalidEmailException;

import java.util.List;

public class SupervisorBean extends ClientBean {

    public SupervisorBean(){}

    public SupervisorBean(String email) throws InvalidEmailException {
        setEmail(email);
        super.supervisor = true;
    }

    public SupervisorBean(String username, String email, List<String> preferences) throws InvalidEmailException {
        super(username, email, preferences);
        super.supervisor = true;
    }
}
