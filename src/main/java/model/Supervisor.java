package model;

import java.util.ArrayList;
public class Supervisor extends User {

    public Supervisor(String nome, String email, String password, ArrayList<String> preferences) {
        super(nome, email, password, preferences);
        supervisor = true;
        registered = true;
    }
}