package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engineering.others.ConfigurationJSON;
import model.Playlist;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlaylistDAOJSON implements PlaylistDAO {

    public void insertPlaylist(Playlist playlist) {
        // Costruisco il percorso del file playlist.json per l'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, playlist.getEmail());

        try {
            // Crea la directory utente se non esiste
            Files.createDirectories(userDirectory);

            // Genera un UUID univoco
            String uniqueId = UUID.randomUUID().toString();

            // Imposta l'ID della playlist
            playlist.setId(uniqueId);

            // Costruisco il percorso per la playlist SENZA uuid per la cartella dell'utente
            String playlistFileName = formatPlaylistFileName(playlist.getPlaylistName());
            Path playlistPath = userDirectory.resolve(playlistFileName + ".json");

            // Verifica se la playlist esiste già per l'utente
            if (!Files.exists(playlistPath)) {
                // Usa Gson per convertire l'oggetto Playlist in una rappresentazione JSON
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(playlist);

                // Scrivi il JSON nel file playlistName.json nella cartella dell'utente
                Files.writeString(playlistPath, json);

                // Copia il file JSON nella cartella di tutte le playlist ma stavolta aggiungo UUID
                String uuidPlaylistFileName = playlistFileName + "[" + uniqueId + "]";
                java.nio.file.Path allPlaylistsPath = Paths.get(ConfigurationJSON.PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + ".json");

                Files.copy(playlistPath, allPlaylistsPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Playlist inserita con successo!");
            } else {
                System.out.println("Una playlist con questo nome esiste già per questo l'utente.");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }


    private String formatPlaylistFileName(String playlistName) {
        // Sostituisci gli spazi con underscore e convergi tutto in minuscolo
        return playlistName.replace(" ", "_").toLowerCase();
    }


    public void deletePlaylist(Playlist playlist) {
        //TODO
    }

    public void retrievePlaylistByMail(String mail) {
        //TODO
    }
    public void retrievePlaylistByGenre(String genre) {
        //TODO
    }

    public List<Playlist> retrivePlaylistByUsername(String username) {
        return null;
    }

    public List<Playlist> retrivePlaylistUser() {
        return Collections.emptyList();
    }
}