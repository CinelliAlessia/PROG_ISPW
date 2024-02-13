package model;

public class Notice {
    private String usernameAuthor;
    private String title;
    private String body;


    public Notice(String title, String body, String usernameAuthor){
        this.usernameAuthor = usernameAuthor;
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

    public void setUsernameAuthor(String usernameAuthor) {
        this.usernameAuthor = usernameAuthor;
    }

    public String getUsernameAuthor() {
        return usernameAuthor;
    }
}
