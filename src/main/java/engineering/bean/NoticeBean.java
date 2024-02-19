package engineering.bean;

public class NoticeBean {

    private String title;
    private String body;
    private String email;

    public NoticeBean(String title, String body,String email){
        setTitle(title);
        setBody(body);
        setEmail(email);
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public String getEmail() {
        return email;
    }
}
