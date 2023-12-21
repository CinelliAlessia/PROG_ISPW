package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class UserDAO_JSON {
    private static final String BASE_DIRECTORY = "src/main/resources/persistence/users";
    public void registerUserAndrea(User user){

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
}
