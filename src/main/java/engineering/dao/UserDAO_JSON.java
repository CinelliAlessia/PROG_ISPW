package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserDAO_JSON implements UserDAO {
    private static final String BASE_DIRECTORY = "src/main/resources/persistence/users";

    @Override
    public void insertUser(User user) {

    }

    @Override
    public String getPasswordByEmail(String email) {
        return null;
    }

    public void saveUser(User user){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);

        Path userDirectory = Paths.get(BASE_DIRECTORY, user.getNome());

        try {
            // Crea la directory con il nome del'utente per l'utente se non esiste
            Files.createDirectories(userDirectory);

            // Crea un file JSON per l'utente (es. "playlist.json")
            String fileName = "info.json";
            Path userFile = userDirectory.resolve(fileName);

            // Scrivi il JSON nel file
            FileWriter fileWriter = new FileWriter(userFile.toString(), true);
            fileWriter.write(json + "\n");
            fileWriter.close();

            System.out.println("Utente registrato con successo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteUser(User userInstance) {
        Path userDirectory = Paths.get(BASE_DIRECTORY, userInstance.getNome());

        try {
            Files.walk(userDirectory)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);

            Files.deleteIfExists(userDirectory); // Rimuove la directory dell'utente
            System.out.println("Utente eliminato con successo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void retreiveUserByUserName(String username) {
        Path userDirectory = Paths.get(BASE_DIRECTORY, username);

        if (Files.exists(userDirectory)) {
            try {
                Files.walk(userDirectory)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                String content = Files.readString(file);
                                // Usa Gson per deserializzare il contenuto JSON e ottenere l'utente
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                User user = gson.fromJson(content, User.class);
                                // Fai qualcosa con l'utente recuperato
                                System.out.println(user);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Utente non trovato!");
        }
    }

    @Override
    public void retreiveUserByUserId(String userId) {
        // Implementazione per recuperare gli utenti per ID se è presente questa logica nel filesystem
        // Questo dipende dalla struttura specifica del tuo sistema di file
    }
}
