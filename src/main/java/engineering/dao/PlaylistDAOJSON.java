package engineering.dao;

import com.google.gson.*;
import engineering.others.ConfigurationJSON;
import model.Playlist;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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
            Path playlistPath = userDirectory.resolve(playlistFileName + ConfigurationJSON.FILE_EXTENCTION);

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
                    allPlaylistsPath = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + ConfigurationJSON.FILE_EXTENCTION);
                } else {
                    // Altrimenti, salvala nella cartella delle playlist non approvate
                    allPlaylistsPath = Paths.get(ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY, uuidPlaylistFileName + ConfigurationJSON.FILE_EXTENCTION);
                }

                Files.copy(playlistPath, allPlaylistsPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Playlist inserita con successo!");
                result = true;

            } else {
                System.out.println("Una playlist con questo nome esiste già per questo utente.");
            }

        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return result;
    }

    public Playlist approvePlaylist(Playlist playlist) {
        // Passo 1: Aggiornare il campo nella cartella dell'utente proprietario della Playlist
        boolean updatedInUserFolder = updatePlaylistApprovedField(playlist, ConfigurationJSON.USER_BASE_DIRECTORY);
        // Passo 2: Aggiornare il campo nella cartella delle Playlist in attesa di approvazione
        boolean updatedInPendingFolder = updatePlaylistApprovedField(playlist, ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY);

        // Passo 3: Copiare file della Playlist nella cartella delle playlist approvate
        //          Eliminare file della Playlist nella cartella delle playlist in attesa
        if (updatedInUserFolder && updatedInPendingFolder) {
            copyAndDeletePlaylist(playlist);
            playlist.setApproved(true);
            System.out.println(playlist.getPlaylistName() + " " + playlist.getApproved() );
            return playlist;
        }
        return null;
    }
    /**
     *  Imposta il campo Approved a true all'interno del file della playlist
     */
    private boolean updatePlaylistApprovedField(Playlist playlist, String folderPath) {
        String fileName;
        java.nio.file.Path playlistPath;
        if (folderPath.equals(ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY) ||
                folderPath.equals(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY)) {
            fileName = addUuidToPlaylistFileName(formatPlaylistFileName(playlist.getPlaylistName()), playlist.getId());
            playlistPath = Paths.get(folderPath,fileName + ConfigurationJSON.FILE_EXTENCTION);
        } else {
            fileName = formatPlaylistFileName(formatPlaylistFileName(playlist.getPlaylistName()));
            playlistPath = Paths.get(folderPath, playlist.getEmail(),fileName + ConfigurationJSON.FILE_EXTENCTION);
        }
        //################# Possibile duplicazione da evitare ################################
        if (Files.exists(playlistPath)) {
            try {
                // Leggi il contenuto del file
                String content = Files.readString(playlistPath);

                // ########## Usa Gson per de-serializzare il contenuto JSON e ottenere la playlist
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Playlist updatedPlaylist = gson.fromJson(content, Playlist.class);

                // Aggiorna lo stato "approved"
                updatedPlaylist.setApproved(true);

                // ########### Converti l'oggetto Playlist aggiornato in JSON
                String updatedJson = gson.toJson(updatedPlaylist);

                // Sovrascrivi il file con le informazioni aggiornate
                Files.writeString(playlistPath, updatedJson);
                System.out.println(playlist.getPlaylistName() + " " + playlist.getApproved() );
                return true;
            } catch (IOException e) {
                e.fillInStackTrace();
                return false;
            }
        } else {
            System.out.println("File della playlist non trovato.");
            return false;
        }
    }
    private String addUuidToPlaylistFileName(String playlistName, String uuid) {
        // Sostituisci gli spazi con underscore, convergi tutto in minuscolo e aggiungi UUID tra parentesi quadre
        return formatPlaylistFileName(playlistName) + "[" + uuid + "]";
    }
    private String formatPlaylistFileName(String playlistName) {
        // Sostituisci gli spazi con underscore e convergi tutto in minuscolo
        return playlistName.replace(" ", "_").toLowerCase();
    }
    private void copyAndDeletePlaylist(Playlist playlist) {
        String fileNameWithUUID = addUuidToPlaylistFileName(formatPlaylistFileName(playlist.getPlaylistName()), playlist.getId());
        java.nio.file.Path sourcePath = Paths.get(ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY, fileNameWithUUID + ConfigurationJSON.FILE_EXTENCTION);
        java.nio.file.Path destinationPath = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY, fileNameWithUUID + ConfigurationJSON.FILE_EXTENCTION);

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            deletePlaylistFromFolder(sourcePath);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    /**
     * Per eliminare il file della Playlist da una cartella
     */
    private boolean deletePlaylistFromFolder(java.nio.file.Path playlistPath) {
        try {
            Files.deleteIfExists(playlistPath);
            return true; // Ritorna true se l'eliminazione ha avuto successo
        } catch (IOException e) {
            e.fillInStackTrace();
            return false; // Ritorna false se si verifica un'eccezione durante l'eliminazione
        }
    }

    /**
     * Questo metodo elimina il file della playlist dalla cartella dell'utente
     * e dalla cartella globale delle playlist (approvate o non approvate in base al caso).
     */
    public void deletePlaylist(Playlist playlist) {
        // Costruisco il percorso del file playlist.json per l'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, playlist.getEmail());
        // Costruisco il percorso per la playlist SENZA uuid per la cartella dell'utente
        String playlistFileName = formatPlaylistFileName(playlist.getPlaylistName());
        Path playlistPath = userDirectory.resolve(playlistFileName + ConfigurationJSON.FILE_EXTENCTION);

        // Elimina il file della playlist dall'utente
        boolean deletedFromUserFolder = deletePlaylistFromFolder(playlistPath);

        // Nome del file con UUID della playlist
        String uuidPlaylistFileName = playlistFileName + "[" + playlist.getId() + "]";

        // Verifica se la playlist è approvata o meno
        Path allPlaylistsPath;

        if (playlist.getApproved()) {
            // Se la playlist è approvata, elimina il file dalla cartella delle playlist approvate
            allPlaylistsPath = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY, uuidPlaylistFileName + ConfigurationJSON.FILE_EXTENCTION);
        } else {
            // Altrimenti, elimina il file dalla cartella delle playlist non approvate
            allPlaylistsPath = Paths.get(ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY, uuidPlaylistFileName + ConfigurationJSON.FILE_EXTENCTION);
        }

        // Elimina il file dalla cartella globale delle playlist
        boolean deletedFromGlobalFolder = deletePlaylistFromFolder(allPlaylistsPath);

        if (deletedFromUserFolder && deletedFromGlobalFolder) {
            System.out.println("Playlist eliminata con successo!");
        } else {
            System.out.println("Errore durante l'eliminazione della playlist.");
        }
    }

    public List<Playlist> retrievePlaylistsByEmail(String mail) {
        List<Playlist> playlistList = new ArrayList<>();

        // Costruisco il percorso della directory dell'utente
        java.nio.file.Path userDirectory = Paths.get(ConfigurationJSON.USER_BASE_DIRECTORY, mail);

        // Verifica se la directory dell'utente esiste
        if (Files.exists(userDirectory)) {
            playlistList = retrievePlaylistsFromDirectory(userDirectory);
        } else {
            System.out.println("Utente non trovato!");
        }

        return playlistList;
    }

    //############################# DA FARE ###################
    public List<Playlist> searchPlaylistByFilter(Playlist playlist) {
        return Collections.emptyList();
    }
    private List<Playlist> retrievePlaylistsFromDirectory(Path directory) {
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.filter(file ->
                            Files.isRegularFile(file) &&
                                    file.getFileName().toString().endsWith(ConfigurationJSON.FILE_EXTENCTION) &&
                                    !file.getFileName().toString().equals(ConfigurationJSON.USER_INFO_FILE_NAME))
                    .map(file -> {
                        try {
                            String content = Files.readString(file);
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            return gson.fromJson(content, Playlist.class);
                        } catch (IOException e) {
                            // Gestisci l'eccezione in modo appropriato
                            e.fillInStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            // Gestisci l'eccezione in modo appropriato
            e.fillInStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Playlist> retrievePendingPlaylists() {
        Path pendingPlaylistsDirectory = Paths.get(ConfigurationJSON.PENDING_PLAYLISTS_BASE_DIRECTORY);
        return retrievePlaylistsFromDirectory(pendingPlaylistsDirectory);
    }
    public List<Playlist> retrieveApprovedPlaylists() {
        Path approvedPlaylistsDirectory = Paths.get(ConfigurationJSON.APPROVED_PLAYLIST_BASE_DIRECTORY);
        return retrievePlaylistsFromDirectory(approvedPlaylistsDirectory);
    }

    public List<Playlist> searchPlaylistString(Playlist playlist) {
        // Estrai il nome della playlist da cercare
        String targetPlaylistName = playlist.getPlaylistName();

        // Recupera tutte le playlist approvate
        List<Playlist> allApprovedPlaylists = retrieveApprovedPlaylists();

        // Filtra le playlist che contengono il titolo della playlist di destinazione
        return allApprovedPlaylists.stream()
                .filter(p -> p.getPlaylistName().toLowerCase().contains(targetPlaylistName.toLowerCase()))
                .toList();  // Utilizza toList() direttamente su Stream
    }

}