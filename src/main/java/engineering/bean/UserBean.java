package engineering.bean;

import java.util.ArrayList;

public class UserBean {
    private String nome;
    private String email;
    private String password;
    private ArrayList <String> preferences;

    public UserBean(){}
    public UserBean(String nome, String email, String pass, ArrayList<String> preferences){
        setNome(nome);
        setEmail(email);
        setPassword(pass);
        setPreferences(preferences);
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }
}
