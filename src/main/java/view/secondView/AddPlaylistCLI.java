package view.secondView;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import engineering.exceptions.LinkIsNotValid;
import engineering.exceptions.PlaylistLinkAlreadyInUse;
import view.secondView.utils.ITAStringCLI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AddPlaylistCLI {
    private final String genreListFile = ITAStringCLI.GENERES_FILE_PATH;
    private ClientBean clientBean;
    private final Scanner scanner = new Scanner(System.in);

    public void setClientBean(ClientBean bean){
        this.clientBean = bean;
    }
    public void start() {
        PlaylistBean playlistBean = new PlaylistBean();

        playlistBean.setApproved(clientBean.isSupervisor());
        playlistBean.setUsername(clientBean.getUsername());
        playlistBean.setEmail(clientBean.getEmail());

        // Chiedi all'utente di inserire i dati della playlist
        System.out.print("Inserisci il titolo della playlist: ");
        playlistBean.setPlaylistName(scanner.nextLine());

        boolean linkIsValid = false;
        while (!linkIsValid) {
            System.out.print("Inserisci il link della playlist: ");
            try {
                playlistBean.setLink(scanner.nextLine());
                linkIsValid = true; // Se non viene lanciata l'eccezione, il link è valido e usciamo dal ciclo
            } catch (LinkIsNotValid e) {
                System.out.println("! Link non valido-> Riprova !");
            }
        }

        // Richiedi all'utente di selezionare i generi musicali
        Map<Integer, String> availableGenres = getAvailableGenres();
        printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        System.out.print("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = extractGenres(availableGenres, genreInput);
        playlistBean.setPlaylistGenre(preferences);

        // #################### Devo prendere le emotional da input utente #############################
        playlistBean.setEmotional(Arrays.asList(0.0, 0.0, 0.0, 0.0));

        try {
            // Invocazione del controller applicativo per inserire la playlist
            AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
            addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

            System.out.println("Playlist aggiunta con successo!");
        } catch (PlaylistLinkAlreadyInUse e) {
            System.out.println(STR." ! Il link relativo playlist è già presente nel sistema !");

        }
    }

    private Map<Integer, String> getAvailableGenres() {
        // Restituisci una mappa di generi musicali disponibili letti da un file
        Map<Integer, String> availableGenres = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(genreListFile))) {
            String line;
            int index = 1;
            while ((line = br.readLine()) != null) {
                // Aggiungi il genere alla mappa con un numero di indice
                availableGenres.put(index, line.trim());
                index++;
            }
        } catch (IOException e) {
            // Gestisci l'eccezione qui senza lanciarla di nuovo
            System.err.println(STR."Errore durante la lettura del file: \{e.getMessage()}");
        }

        return availableGenres;
    }
    private void printGenres(Map<Integer, String> genres) {
        // Stampa i generi musicali disponibili
        genres.forEach((key, value) -> System.out.println(STR."\{key}: \{value}"));
    }
    private List<String> extractGenres(Map<Integer, String> availableGenres, String genreInput) {
        // Estrai i generi musicali selezionati dall'utente
        List<String> preferences = new ArrayList<>();
        String[] genreIndices = genreInput.split(",");
        for (String index : genreIndices) {
            try {
                int genreIndex = Integer.parseInt(index.trim());
                if (availableGenres.containsKey(genreIndex)) {
                    preferences.add(availableGenres.get(genreIndex));
                } else {
                    System.out.println(STR." ! Numero genere non valido: \{index} !");
                }
            } catch (NumberFormatException e) {
                System.out.println(STR." ! Input non valido: \{index} !");
            }
        }
        return preferences;
    }

}
