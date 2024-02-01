package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engineering.others.ConfigurationJSON;
import model.Playlist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class PlaylistDAOJSON implements PlaylistDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.BASE_DIRECTORY;

    public void insertPlaylistWithID(Playlist playlist) {
        // Costruisco il percorso della directory dell'utente
        Path userDirectory = Paths.get(BASE_DIRECTORY, playlist.getEmail());

        try {
            // Recupera il numero successivo per la playlist
            int nextPlaylistNumber = getNextPlaylistNumber(userDirectory);

            // Costruisci il nome del file playlist.json con il numero ottenuto
            String fileName = "playlist_" + nextPlaylistNumber + ".json";

            // Costruisci il percorso per la playlist
            Path playlistPath = userDirectory.resolve(fileName);

            // Usa Gson per convertire l'oggetto Playlist in una rappresentazione JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(playlist);

            // Scrivi il JSON nel file della playlist
            Files.writeString(playlistPath, json);

            System.out.println("Playlist inserita con successo!");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void insertPlaylist(Playlist playlist) {
        // Costruisco il percorso del file playlist.json per l'utente
        java.nio.file.Path userDirectory = Paths.get(BASE_DIRECTORY, playlist.getEmail());

        try {
            // Costruisco il percorso per la playlist
            Path playlistPath = userDirectory.resolve(formatPlaylistFileName(playlist.getPlaylistName()) + ".json");

            // Verifica se la playlist esiste già
            if (Files.exists(playlistPath)) {
                System.out.println("Playlist con lo stesso nome già esistente. La playlist non sarà aggiunta.");
                return; // Non aggiungere la playlist se esiste già una con lo stesso nome
            }

            // Usa Gson per convertire l'oggetto Playlist in una rappresentazione JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(playlist);

            // Scrivi il JSON nel file playlist.json
            Files.writeString(playlistPath, json);

            System.out.println("Playlist inserita con successo!");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
    private String formatPlaylistFileName(String playlistName) {
        // Sostituisci gli spazi con underscore e convergi tutto in minuscolo
        return playlistName.replaceAll(" ", "_").toLowerCase();
    }

    private int getNextPlaylistNumber(Path userDirectory) throws IOException {
        // Recupera la lista dei file nella directory dell'utente
        List<Path> playlistFiles = Files.list(userDirectory)
                .filter(path -> path.getFileName().toString().startsWith("playlist_"))
                .collect(Collectors.toList());

        // Estrai i numeri delle playlist dai nomi dei file
        Set<Integer> playlistNumbers = playlistFiles.stream()
                .map(path -> Integer.parseInt(path.getFileName().toString().replace("playlist_", "").replace(".json", "")))
                .collect(Collectors.toSet());

        // Trova il prossimo numero disponibile
        int nextPlaylistNumber = 1;
        while (playlistNumbers.contains(nextPlaylistNumber)) {
            nextPlaylistNumber++;
        }

        return nextPlaylistNumber;
    }

    @Override
    public String getPlaylistByUserName(String email) {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlistInstance) {
    }
    @Override
    public void retrievePlaylistByMail(String mail) {
    }
    @Override
    public void retrievePlaylistByGenre(String genre) {    }
    @Override
    public List<Playlist> retrivePlaylistUser() {
        return Collections.emptyList();
    }
}