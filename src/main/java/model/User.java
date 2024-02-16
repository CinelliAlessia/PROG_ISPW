package model;

import java.util.List;

public class User extends Client {

    public User(String username, String email, List<String> preferences){
        super(username, email, preferences);
        super.supervisor = false;
    }
}

