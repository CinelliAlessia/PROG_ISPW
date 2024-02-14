package engineering.dao;

import model.Notice;
import model.User;

import java.util.List;

public interface NoticeDAO {

    void addNotice(Notice notice);

    void deleteNotice(Notice notice);

    List<Notice> retrieveNotice(User user);
}
