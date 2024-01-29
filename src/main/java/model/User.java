    package model;

    import java.util.ArrayList;

    public class User {
        private String userName, email, password;
        protected boolean supervisor;
        protected boolean registered;
        private ArrayList<String> pref;
        public User(String nome, String email, String password, ArrayList<String> preferences){
            setUserName(nome);
            setEmail(email);
            setPassword(password);
            setPreferences(preferences);
            supervisor = false;
            registered = true;
        }
        public boolean isSupervisor(){ //Restituisce false per User e Guest, true per Supervisor
            return supervisor;
        }
        public boolean isRegistered(){ //Restituisce false Guest, true per User e Supervisor
            return registered;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public void setPreferences(ArrayList<String> preferences) {
            this.pref = preferences;
        }

        public String getUserName() {
            return userName;
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

