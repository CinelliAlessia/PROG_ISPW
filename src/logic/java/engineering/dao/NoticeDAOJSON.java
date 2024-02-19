package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import engineering.others.ConfigurationJSON;
import engineering.others.Printer;
import model.Client;
import model.Notice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoticeDAOJSON implements NoticeDAO {

    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;
    private static final String FILE_NAME = ConfigurationJSON.NOTICE_FILE_NAME;

    private static final String ERROR_IMPLEMENTATION = "Non è stato implementato in JSON";

    public void addNotice(Notice notice) {
        try {
            String userEmail = notice.getEmail();  // Assuming getUsernameAuthor() returns user email

            // Check if the user directory exists, create it if not
            Path userDirectory = Paths.get(BASE_DIRECTORY, userEmail);

            Path noticesFile = userDirectory.resolve(FILE_NAME);
            // Load existing notices or create a new list
            List<Notice> notices = loadNotices(noticesFile);

            // Add the new notice
            notices.add(notice);

            // Save the updated notices list
            saveNotices(notices, noticesFile);

        } catch (IOException e) {
            handleDAOException(e);
        }
    }

    private List<Notice> loadNotices(Path noticesFile) throws IOException {
        if (Files.exists(noticesFile)) {
            String content = Files.readString(noticesFile);
            Printer.println("error");
            return parseNotices(content);
        } else {
            Files.createFile(noticesFile);
            return new ArrayList<>();
        }
    }

    private void saveNotices(List<Notice> notices, Path noticesFile) throws IOException {
        // Serializza la lista di notifiche in formato JSON e scrivi nel file
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(notices);
        Files.writeString(noticesFile, json);
    }

    private List<Notice> parseNotices(String content) {
        Gson gson = new Gson();
        TypeToken<List<Notice>> token = new TypeToken<>() {};

        // Verifica se il contenuto è nullo o vuoto
        if (content == null || content.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return gson.fromJson(content, token.getType());
    }


    private void handleDAOException(IOException e) {
        Printer.errorPrint(String.format("NoticeDAOJSON: %s", e.getMessage()));
    }

    public void deleteNotice(Notice notice) {
        Printer.logPrint(ERROR_IMPLEMENTATION);
    }

    public List<Notice> retrieveNotice(Client user) {
        try {
            Path userDirectory = Paths.get(BASE_DIRECTORY, user.getEmail());
            Path noticesFile = userDirectory.resolve(ConfigurationJSON.NOTICE_FILE_NAME);

            if (Files.exists(noticesFile)) {
                String content = Files.readString(noticesFile);
                return parseNotices(content);
            } else {
                Files.createFile(noticesFile);
                return Collections.emptyList();
            }
        } catch (IOException e) {
            handleDAOException(e);
            return Collections.emptyList();
        }
    }

}