    package model;

    import java.util.ArrayList;

    public class User {
        private String username, email, password;
        private ArrayList<String> pref;

        protected boolean supervisor,registered;

        public User(String username, String email, String password, ArrayList<String> preferences){
            setUsername(username);
            setEmail(email);
            setPassword(password);
            setPreferences(preferences);
            supervisor = false;
        }
        public boolean isSupervisor(){ //Restituisce false per User e Guest, true per Supervisor
            return supervisor;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public void setPreferences(ArrayList<String> preferences) {
            this.pref = preferences;
        }

        public String getUsername() {
            return username;
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

