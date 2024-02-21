import engineering.dao.ClientDAO;
import engineering.dao.PlaylistDAO;
import engineering.exceptions.*;
import engineering.pattern.abstract_factory.DAOFactory;
import model.Login;
import model.Playlist;
import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.*;

/** Test di Alessia Cinelli */
public class TestGestionePlaylist {

    /* Utente per test */
    private final String USERNAME = "testUser";
    private final String EMAIL = "testUser@gmail.com";
    private final List<String> GENRES_USER = new ArrayList<>(List.of("Pop", "Indie"));

    /* Playlist per test */
    private final String PLAYLIST_TITLE = "testPlaylist";
    private final List<String> GENERI = List.of(
            "Pop", "Indie", "Classic", "Rock", "Electronic",
            "House", "HipHop", "Jazz", "Acoustic", "REB",
            "Country", "Alternative");
    private final int LUNGHEZZA_LINK = 10; // Lunghezza del link


    /** Testa il corretto caricamento della playlist in persistenza */
    @Test
    public void testAddPlaylist() {
        insertUser(); // L'utente della playlist deve esistere
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().createPlaylistDAO();  // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<String> genres_random = popolaGeneriCasuali(); // Popola la lista casuale di generi
        String link_random = generaLinkCasuale(); // Popola il link

        Playlist playlist = new Playlist(EMAIL, USERNAME, PLAYLIST_TITLE, link_random, genres_random, false);
        try {
            // Inserisci la playlist
            playlistDAO.insertPlaylist(playlist);

            // Recupera le playlist dell'utente test
            List<Playlist> retrievedPlaylists = playlistDAO.retrievePlaylistsByEmail(EMAIL);

            // Assert sul numero di playlist attese (1)
            Assert.assertEquals(1, retrievedPlaylists.size());
            // Assert se la playlist attesa abbia il titolo generato -> Il link è univoco nel sistema
            Assert.assertEquals(PLAYLIST_TITLE, retrievedPlaylists.getFirst().getPlaylistName());

            // Elimino per permettere i corretti casi di test futuri
            playlistDAO.deletePlaylist(retrievedPlaylists.getFirst());

        } catch (PlaylistLinkAlreadyInUseException | PlaylistNameAlreadyInUseException e) {
            // Se ci sono eccezioni, il test fallirà
            Assert.fail(String.format("Errore durante l'inserimento della playlist: %s", e.getMessage()));
        }
    }

    /** Testa la corretta eliminazione della playlist dalla persistenza */
    @Test
    public void testDeletePlaylist() {
        insertUser(); // L'utente della playlist deve esistere
        List<String> genres_random = popolaGeneriCasuali(); // Popola la lista casuale di generi
        String link_random = generaLinkCasuale(); // Popola il link

        // Crea una playlist di test
        Playlist playlist = new Playlist(EMAIL, USERNAME, PLAYLIST_TITLE, link_random, genres_random, false);

        try {
            // Inserimento playlist
            PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().createPlaylistDAO();  // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
            playlistDAO.insertPlaylist(playlist);

            // Recupera la playlist appena inserita
            List<Playlist> retrievedPlaylists = playlistDAO.retrievePlaylistsByEmail(EMAIL);
            Assert.assertEquals(1, retrievedPlaylists.size());

            // Elimina la playlist
            playlistDAO.deletePlaylist(retrievedPlaylists.getFirst());

            // Assicurati che la playlist sia stata eliminata
            retrievedPlaylists = playlistDAO.retrievePlaylistsByEmail(EMAIL);

            for(Playlist p: retrievedPlaylists){
                if(Objects.equals(p.getLink(), playlist.getLink())){
                    Assert.assertEquals(0, retrievedPlaylists.size());
                }
            }

        } catch (PlaylistLinkAlreadyInUseException | PlaylistNameAlreadyInUseException e) {
            // Se ci sono eccezioni, il test fallirà
            Assert.fail(String.format("Errore durante l'inserimento della playlist: %s", e.getMessage()));
        }
    }

    /** Testa il corretto filtraggio di playlist, ne vengono caricate in un numero casuale
     * e ci si aspetta che tutte le playlist recuperate abbiano lo stesso genere musicale */
    @Test
    public void testRicercaPlaylistPerGenere() {
        insertUser(); // L'utente della playlist deve esistere

        // Inserisce un numero casuale di playlist di test (da 1 a 10) nel sistema con la stessa lista di playlist
        List<String> genres_random = popolaGeneriCasuali();
        List<Playlist> playlistInserted = inserisciPlaylistCasuali(genres_random);

        // Effettua una ricerca utilizzando il filtro per genere
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().createPlaylistDAO();

        // Creo la playlist da cercare
        Playlist playlistFiltrata = new Playlist();
        playlistFiltrata.setPlaylistGenre(genres_random);

        List<Playlist> risultatoRicerca = playlistDAO.searchPlaylistByGenre(playlistFiltrata);

        // Mi assicuro che il risultato contenga solo playlist con il genere specificato
        for (Playlist playlist : risultatoRicerca) {
            Assert.assertEquals(genres_random, playlist.getPlaylistGenre());
        }

        // Elimina le playlist di test
        eliminaPlaylistDiTest(playlistInserted);
    }

    /** Carica un numero random (max 10) di playlist con lo stesso genere musicale per valutare la correttezza
     * del filtraggio per genere musicale */
    private List<Playlist> inserisciPlaylistCasuali(List<String> genres_random) {
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().createPlaylistDAO();
        int numberPlaylistInserted = getRandomNumberInRange();
        List<Playlist> insertedPlaylists = new ArrayList<>();

        for (int i = 0; i < numberPlaylistInserted; i++) {
            String playlistTitle = generaTitoloPlaylistCasuale();
            String link = generaLinkCasuale();

            Playlist playlist = new Playlist(EMAIL, USERNAME, playlistTitle, link, genres_random, true);

            try {
                playlistDAO.insertPlaylist(playlist);
                insertedPlaylists.add(playlist);
            } catch (PlaylistLinkAlreadyInUseException | PlaylistNameAlreadyInUseException e) {
                e.fillInStackTrace();
            }
        }

        return insertedPlaylists;
    }

    /** Crea un link random per la playlist */
    private String generaLinkCasuale() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[LUNGHEZZA_LINK];

        secureRandom.nextBytes(bytes);

        // Converte i byte generati in una stringa usando Base64
        // L'encoder Base64 con URL-safe encoding, è comunemente utilizzato per generare link web.
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /** Crea una lista di generi musicali random */
    private List<String> popolaGeneriCasuali() {
        Random random = new Random();
        int numeroGeneri = random.nextInt(GENERI.size()) + 1;  // Genera una lunghezza casuale tra 1 e la lunghezza di GENRES inclusa

        List<String> genresRandom = new ArrayList<>(GENERI);

        // Mescola la lista per ottenere una sequenza casuale
        for (int i = 0; i < genresRandom.size(); i++) {
            int index = random.nextInt(genresRandom.size());
            String temp = genresRandom.get(i);
            genresRandom.set(i, genresRandom.get(index));
            genresRandom.set(index, temp);
        }

        // Mantieni solo i primi 'numeroGeneri' generi
        return genresRandom.subList(0, numeroGeneri);
    }

    /** Inserisce l'utente a cui sono collegate le playlist di test */
    private void insertUser() {
        ClientDAO clientDAO = DAOFactory.getDAOFactory().createClientDAO();

        // Uso come password lo username
        Login registrationUser = new Login(USERNAME, EMAIL, USERNAME, GENRES_USER);

        try {
            clientDAO.insertClient(registrationUser);
        } catch (UsernameAlreadyInUseException | EmailAlreadyInUseException e) {
            e.fillInStackTrace(); // Ignoro
        }
    }

    /** Elimina la lista di playlist inserite */
    private void eliminaPlaylistDiTest(List<Playlist> playlistDiTest) {
        PlaylistDAO playlistDAO = DAOFactory.getDAOFactory().createPlaylistDAO();
        for (Playlist playlist : playlistDiTest) {
            playlistDAO.deletePlaylist(playlist);
        }
    }

    /** Titolo random */
    private String generaTitoloPlaylistCasuale() {
        Random random = new Random();
        int numeroCasuale = random.nextInt(1000);  // Puoi regolare il range del numero a seconda delle tue preferenze
        return "MyPlaylist_" + numeroCasuale;
    }

    /** Numero random da 1 a 10*/
    private int getRandomNumberInRange() {
        return (int) (Math.random() * ((10 - 1) + 1)) + 1;
    }

}
