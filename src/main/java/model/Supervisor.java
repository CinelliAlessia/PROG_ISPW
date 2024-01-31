package model;

import java.util.ArrayList;
public class Supervisor extends User {

    public Supervisor(String username, String email, String password, ArrayList<String> preferences) {
        super(username, email, password, preferences);
        supervisor = true;
    }
}