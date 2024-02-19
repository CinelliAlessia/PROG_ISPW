package model;

public class Notice {
    private String email;
    private String title;
    private String body;

    public Notice(String title, String body, String email){
        this.email = email;
        this.title = title;
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }


    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
