package model;

public class User {

    private  String email;
    private  String pass;
    private String name;

    protected User(String email, String pass, String name){
        this.email = email;
        this.pass = pass;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public String getPass(){
        return pass;
    }
}

