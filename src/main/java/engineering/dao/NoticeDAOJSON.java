package engineering.dao;

import model.*;
import java.util.*;
import java.util.logging.Logger;

public class NoticeDAOJSON implements NoticeDAO{
    private static final Logger logger = Logger.getLogger(NoticeDAOJSON.class.getName());

    public void addNotice(Notice notice) {
        logger.info("Non è stato implementato");
    }

    public void deleteNotice(Notice notice) {
        logger.info("Non è stato implementato");
    }

    public List<Notice> retrieveNotice(Client user) {
        return Collections.emptyList();
    }
}
