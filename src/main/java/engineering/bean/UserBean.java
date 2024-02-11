package engineering.bean;

import java.util.List;

public class UserBean extends ClientBean{

    private List<NoticeBean> notices;

    public UserBean(String email){
        setEmail(email);
        super.supervisor = false;
    }

    public UserBean(String username, String email, List<String> preferences){
        super(username,email,preferences);
        super.supervisor = false;
    }

    public void setNotices(List<NoticeBean> notices) {
        this.notices = notices;
    }

    public List<NoticeBean> getNotices() {
        return notices;
    }
}