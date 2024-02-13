package engineering.bean;

public class NoticeBean {

    private String title;
    private String body;

    private String usernameAuthor;

    public NoticeBean(String title, String body,String usernameAuthor){
        setTitle(title);
        setBody(body);
        setUsernameAuthor(usernameAuthor);
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
