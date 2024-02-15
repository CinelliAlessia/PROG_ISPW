package engineering.dao;

import model.Client;
import model.Notice;
import model.User;

import java.util.List;

public interface NoticeDAO {

    void addNotice(Notice notice);

    void deleteNotice(Notice notice);

    List<Notice> retrieveNotice(Client user);
}
