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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class PlaylistDAOJSON implements PlaylistDAO {
    /**
     * Questo metodo inserisce la playlist sia sulla cartella del singolo utente
     * Aggiunge inoltre sulle cartelle generali delle playlist approvate e delle playlist in attesa di approvazione
     */
    public boolean insertPlaylist(Playlist playlist) {
        // Costruisco il percorso del file playlist.json per l'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, playlist.getEmail());
        boolean result = false;

        try {
            // Crea la directory utente se non esiste
            Files.createDirectories(userDirectory);

            // Genera un UUID univoco
            String uniqueId = UUID.randomUUID().toString();

            // Imposta l'ID della playlist
            playlist.setId(uniqueId);

            // Costruisco il percorso per la playlist SENZA uuid per la cartella dell'utente
            String playlistFileName = formatPlaylistFileName(playlist.getPlaylistName());
            String fileExtension = ".json";
            Path playlistPath = userDirectory.resolve(playlistFileName + fileExtension);

            // Verifica se la playlist esiste già per l'utente
            if (!Files.exists(playlistPath)) {
                // Usa Gson per convertire l'oggetto Playlist in una rappresentazione JSON
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(playlist);

                // Scrivi il JSON nel file playlistName.json nella cartella dell'utente
                Files.writeString(playlistPath, json);

                // Copia il file JSON nella cartella di tutte le playlist ma stavolta aggiungi UUID
                String uuidPlaylistFileName = playlistFileName + "[" + uniqueId + "]";
                java.nio.file.Path allPlaylistsPath;

                if (playlist.getApproved()) {
                    // Se la playlist è approvata, salvala nella cartella delle playlist approvate
                    allPlaylistsPath = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + fileExtension);
                } else {
                    // Altrimenti, salvala nella cartella delle playlist non approvate
                    allPlaylistsPath = Paths.get(ConfigurationJSON.PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + fileExtension);
                }

                Files.copy(playlistPath, allPlaylistsPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Playlist inserita con successo!");
                result = true;

            } else {
                System.out.println("Una playlist con questo nome esiste già per questo utente.");
            }

        } catch (IOException e) {
            e.fillInStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * @param playlist
     * @return
     */
    @Override
    public Playlist approvePlaylist(Playlist playlist) {
        return null;
    }


    private String formatPlaylistFileName(String playlistName) {
        // Sostituisci gli spazi con underscore e convergi tutto in minuscolo
        return playlistName.replace(" ", "_").toLowerCase();
    }

    /**
     *Questo metodo elimina il file della playlist dalla cartella dell'utente
     * e dalla cartella globale delle playlist (approvate o non approvate in base al caso).
     */
    public void deletePlaylist(Playlist playlist) {
        // Costruisco il percorso del file playlist.json per l'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, playlist.getEmail());
        String fileExtension = ".json";
        try {
            // Costruisco il percorso per la playlist SENZA uuid per la cartella dell'utente
            String playlistFileName = formatPlaylistFileName(playlist.getPlaylistName());
            Path playlistPath = userDirectory.resolve(playlistFileName + fileExtension);

            // Verifica se il file della playlist esiste per l'utente
            if (Files.exists(playlistPath)) {
                // Elimina il file della playlist dall'utente
                Files.deleteIfExists(playlistPath);

                //  Nome del file con UUID della playlist
                String uuidPlaylistFileName = playlistFileName + "[" + playlist.getId() + "]";

                // Verifica se la playlist è approvata o meno
                java.nio.file.Path allPlaylistsPath;

                if (playlist.getApproved()) {
                    // Se la playlist è approvata, elimina il file dalla cartella delle playlist approvate
                    allPlaylistsPath = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + fileExtension);
                } else {
                    // Altrimenti, elimina il file dalla cartella delle playlist non approvate
                    allPlaylistsPath = Paths.get(ConfigurationJSON.PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + fileExtension);
                }

                Files.deleteIfExists(allPlaylistsPath);
                System.out.println("Playlist eliminata con successo!");
            } else {
                System.out.println("La playlist non esiste per questo utente.");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public List<Playlist> retrievePlaylistsByMail(String mail) {
        List<Playlist> playlistList = new ArrayList<>();

        // Costruisco il percorso della directory dell'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, mail);

        // Verifica se la directory dell'utente esiste
        if (Files.exists(userDirectory)) {
            try (Stream<Path> paths = Files.walk(userDirectory)) {
                paths.filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                String content = Files.readString(file);
                                // Usa Gson per deserializzare il contenuto JSON e ottenere la playlist
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                Playlist playlist = gson.fromJson(content, Playlist.class);
                                // Aggiungi la playlist alla lista
                                playlistList.add(playlist);
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
        return playlistList;
    }

    public List<Playlist> retrievePlaylistByGenre(String genre) {
        return Collections.emptyList();
    }

    public List<Playlist> retrivePlaylistByUsername(String username) {
        return Collections.emptyList();
    }

    /**
     * @return null
     */
    @Override
    public List<Playlist> retriveAllPlaylistToApprove() {
        return Collections.emptyList();
    }

    public List<Playlist> retrivePlaylistUser() {
        return Collections.emptyList();
    }
}