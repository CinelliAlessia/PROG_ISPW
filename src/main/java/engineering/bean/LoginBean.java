package engineering.bean;

public class LoginBean {

    private String email, password;

    public LoginBean(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
