package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engineering.others.ConfigurationJSON;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UserDAOJSON implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;
    @Override
    public boolean insertUser(User user) {
        // Utilizzato per aggiungere un utente al file system
        // Costruisci il percorso della directory dell'utente (presumibilmente basandoti sulla mail come nome utente)
        Path userDirectory = Paths.get(BASE_DIRECTORY, user.getEmail() );
        boolean result;
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

            result = true;
            System.out.println("Utente inserito con successo!");
        } catch (IOException e) {
            e.fillInStackTrace();
            result = false;
        }
        return result;
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
            System.out.println("Utente non trovato o file userInfo.json mancante.");
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

    @Override
    public void deleteUser(User userInstance) {
        Path userDirectory = Paths.get(BASE_DIRECTORY, userInstance.getEmail());

        try {
            Files.walk(userDirectory)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);

            Files.deleteIfExists(userDirectory); // Rimuove la directory dell'utente
            System.out.println("Utente eliminato con successo!");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    /* Questo metodo non da problemi con SonarCloud, ma devi vedere se funziona

        @Override
        public void deleteUser(User userInstance) {
            Path userDirectory = Paths.get(BASE_DIRECTORY, userInstance.getEmail());

            try (Stream<Path> paths = Files.walk(userDirectory)) {
                paths.filter(Files::isRegularFile)
                     .map(Path::toFile)
                     .forEach(File::delete);

                Files.deleteIfExists(userDirectory); // Rimuove la directory dell'utente
                System.out.println("Utente eliminato con successo!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

    // questa funzione assume di avere un FS dove le cartelle sono nominate tramite username
    public void retrieveUserByUsername(String username) {
        Path userDirectory = Paths.get(BASE_DIRECTORY, username);

        if (Files.exists(userDirectory)) {
            try {
                Files.walk(userDirectory)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                String content = Files.readString(file);
                                // Usa Gson per de-serializzare il contenuto JSON e ottenere l'utente
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                User user = gson.fromJson(content, User.class);
                                // Fai qualcosa con l'utente recuperato
                                System.out.println(user);
                            } catch (IOException e) {
                                e.fillInStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            System.out.println("Utente non trovato!");
        }
    }

    /**
     * @param email
     * @param preferences
     */
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
