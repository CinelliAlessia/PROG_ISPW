    package model;

    import java.util.ArrayList;

    public class User {
        private String nome, email, password;
        private ArrayList<String> pref;
        public User(String nome, String email, String password, ArrayList<String> preferences){
            setNome(nome);
            setEmail(email);
            setPassword(password);
            setPreferences(preferences);
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setPassword(String password) {
            this.password = password;
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


        public ArrayList<String> getPref() {
            return pref;
        }

        public String getPassword() {
            return password;
        }
    }

