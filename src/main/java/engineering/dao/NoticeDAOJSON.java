package engineering.dao;

import engineering.others.CLIPrinter;
import model.*;
import java.util.*;

public class NoticeDAOJSON implements NoticeDAO{

    private static final String ERROR_IMPLEMENTATION = "Non è stato implementato in JSON";
    public void addNotice(Notice notice) {
        CLIPrinter.logPrint(ERROR_IMPLEMENTATION);
    }

    public void deleteNotice(Notice notice) {
        CLIPrinter.logPrint(ERROR_IMPLEMENTATION);
    }

    public List<Notice> retrieveNotice(Client user) {
        CLIPrinter.logPrint(ERROR_IMPLEMENTATION);
        return Collections.emptyList();
    }
}
