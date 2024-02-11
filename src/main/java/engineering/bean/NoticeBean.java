package engineering.bean;

public class NoticeBean {

    private String title;
    private String body;

    public NoticeBean(String title, String body){
        setTitle(title);
        setBody(body);
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

}
