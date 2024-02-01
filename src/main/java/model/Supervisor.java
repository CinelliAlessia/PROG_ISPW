package model;

import java.util.List;

public class Supervisor extends User {

    public Supervisor(String username, String email, String password, List<String> preferences) {
        super(username, email, password, preferences);
        supervisor = true;
    }
}