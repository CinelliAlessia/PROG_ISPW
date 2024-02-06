package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engineering.others.ConfigurationJSON;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserDAOJSON3 implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;

    /**
     *  Aggiunge un utente al file system (Crea una cartella nominata come l'email dell'utente, e crea un file userIfo.json
     */
    public boolean insertUser(User user) {
        // Costruisci il percorso della directory dell'utente (presumibilmente basandoti sulla mail come nome utente)
        Path userDirectory = Paths.get(BASE_DIRECTORY, user.getEmail());
        boolean result = true;

        // Verifica se l'utente esiste già
        if (checkIfUserExistsByEmail(user.getEmail())) {
            System.out.println("Utente con la stessa email esiste già. Inserimento non riuscito.");
            return false;
        }
        if (retrieveUserByUsername(user.getUsername()) != null){
            System.out.println("Utente con stesso Username esiste già. Inserimento non riuscito.");
            return false;
        }
        try {
            // Crea la directory con il nome dell'utente se non esiste
            Files.createDirectories(userDirectory);

            // Costruisci il percorso del file userInfo.json
            Path userInfoFile = userDirectory.resolve(ConfigurationJSON.USER_INFO_FILE_NAME);

            // Usa Gson per convertire l'oggetto User in una rappresentazione JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(user);

            // Scrivi il JSON nel file userInfo.json
            Files.writeString(userInfoFile, json);

            System.out.println("Utente inserito con successo (insert User DAO)!");
        } catch (IOException e) {
            e.fillInStackTrace();
            result = false;
        }
        return result;
    }
    private boolean checkIfUserExistsByEmail(String userEmail) {
        // Costruisci il percorso della directory dell'utente basandoti sulla mail come nome utente
        Path userDirectory = Paths.get(BASE_DIRECTORY, userEmail);

        // Verifica se la directory dell'utente esiste
        return Files.exists(userDirectory);
    }
    @Override
    public User loadUser(String userEmail) {
        // Costruisci il percorso del file userInfo.json per l'utente
        Path userInfoFile = Paths.get(BASE_DIRECTORY, userEmail, ConfigurationJSON.USER_INFO_FILE_NAME);

        // Verifica se il file userInfo.json esiste
        if (Files.exists(userInfoFile)) {
            try {
                // Leggi il contenuto del file
                String content = Files.readString(userInfoFile);

                // Usa Gson per de-serializzare il contenuto JSON e ottenere l'utente
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                User user = gson.fromJson(content, User.class);

                System.out.println("Email: " + user.getEmail() + ", Username: " + user.getUsername() + ", Preferences: " + user.getPref());

                return user;
            } catch (IOException e) {
                e.fillInStackTrace(); // Gestisci l'eccezione in modo appropriato
            }
        } else {
            System.out.println("Utente non trovato");
        }

        // Se qualcosa va storto o l'utente non esiste, restituisci un utente vuoto
        return null;
    }
    @Override
    public String getPasswordByEmail(String email) {
        // Costruisci il percorso del file userInfo.json per l'utente (presumibilmente basandoti sulla mail come nome utente)
        Path userInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.USER_INFO_FILE_NAME);

        // Verifica se il file userInfo.json esiste
        if (Files.exists(userInfoFile)) {
            try {
                // Leggi il contenuto del file
                String content = Files.readString(userInfoFile);

                // Usa Gson per de-serializzare il contenuto JSON e ottenere l'utente
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                User user = gson.fromJson(content, User.class);

                // Restituisci la password dell'utente
                return user.getPassword();
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            System.out.println("Utente non trovato o file userInfo.json mancante.");
        }
        // Se qualcosa va storto o l'utente non esiste, restituisci null
        return null;
    }

    // questa funzione assume di avere un FS dove le cartelle sono nominate tramite username
    public User retrieveUserByUsername(String username) {
        Path baseDirectory = Paths.get(BASE_DIRECTORY);

        try (Stream<Path> userDirectories = Files.list(baseDirectory)) {
            Optional<User> matchingUser = userDirectories
                    .filter(Files::isDirectory)
                    .map(this::getUserFromDirectory)
                    .filter(user -> user != null && user.getUsername().equals(username))
                    .findFirst();

            return matchingUser.orElse(null);
        } catch (IOException e) {
            e.fillInStackTrace(); // o gestisci l'eccezione a seconda delle tue esigenze
        }

        return null; // Nessun utente trovato o errore durante la ricerca
    }

    private User getUserFromDirectory(Path userDirectory) {
        try {
            Path userFilePath = userDirectory.resolve("userInfo.json");

            if (Files.exists(userFilePath)) {
                String content = Files.readString(userFilePath);
                return parseUser(content);
            }
        } catch (IOException e) {
            e.fillInStackTrace(); // o gestisci l'eccezione a seconda delle tue esigenze
        }

        return null; // Nessun file userInfo.json trovato o errore durante la lettura
    }

    private User parseUser(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(content, User.class);
    }

    public void updateGenreUser(String email, List<String> preferences) {
        // Costruisci il percorso del file userInfo.json per l'utente
        Path userInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.USER_INFO_FILE_NAME);

        // Verifica se il file userInfo.json esiste
        if (Files.exists(userInfoFile)) {
            try {
                // Leggi il contenuto del file
                String content = Files.readString(userInfoFile);

                // Usa Gson per de-serializzare il contenuto JSON e ottenere l'utente
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                User user = gson.fromJson(content, User.class);

                // Aggiorna le preferenze dell'utente
                user.setPref(preferences);
                System.out.println("Nuove preferenze" + user.getPref());

                // Converti l'oggetto User aggiornato in JSON
                String updatedJson = gson.toJson(user);

                // Sovrascrivi il file userInfo.json con le informazioni aggiornate
                Files.writeString(userInfoFile, updatedJson);

                System.out.println("Preferenze utente aggiornate con successo!");
            } catch (IOException e) {
                e.fillInStackTrace(); // Gestisci l'eccezione in modo appropriato
            }
        } else {
            System.out.println("Utente non trovato o file userInfo.json mancante.");
        }
    }
}
