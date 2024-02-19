package model;

import java.util.List;

public class Supervisor extends Client {

    public Supervisor(String username, String email, List<String> preferences){
        super(username, email, preferences);
        super.supervisor = true;
    }

}