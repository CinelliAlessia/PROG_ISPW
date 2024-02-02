    package model;

    import java.util.List;

    public class User {
        private String username;
        private String email;
        private String password;
        private List<String> pref;

        protected boolean supervisor;
        protected boolean registered;

        public User(String username, String email, String password, List<String> preferences){
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
        public void setPreferences(List<String> preferences) {
            this.pref = preferences;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public List<String> getPref() {
            return pref;
        }

        public String getPassword() {
            return password;
        }

        public void setPref(List<String> preferences) {
            pref = preferences;
        }
    }

