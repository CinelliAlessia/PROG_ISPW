package engineering;
public class UserBean {

    private String userEmail,name,pass;

    public UserBean(){}

    public UserBean(String userEmail, String name, String pass) {
        setName(name);
        setPass(pass);
        setUserEmail(userEmail);
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

