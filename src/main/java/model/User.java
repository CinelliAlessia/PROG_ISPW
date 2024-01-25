    package model;

    import java.util.ArrayList;

    public class User {
        private String nome, email,pass;
        private ArrayList<String> pref;
        public User(String nome, String email, String pass, ArrayList preferences){
            setNome(nome);
            setEmail(email);
            setPass(pass);
            setPreferences(preferences);
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
        public void setPreferences(ArrayList<String> preferences) {
            this.pref = preferences;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getPass() {
            return pass;
        }

        public ArrayList<String> getPref() {
            return pref;
        }
    }

