package model;

import java.util.List;

public class User extends Client{
    private List<Notice> notices;

    public User(String email){
        setEmail(email);
        super.supervisor = false;
    }

    public User(String username, String email, List<String> preferences){
        super(username, email, preferences);
        super.supervisor = false;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<Notice> getNotices() {
        return notices;
    }
}

