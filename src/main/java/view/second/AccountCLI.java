package view.second;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import view.second.utils.GenreManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountCLI {
    private static final Logger logger = Logger.getLogger(AccountCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);
    private ClientBean clientBean;
    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    public void start() {
        while (true) {
            logger.info("\u001B[32m" + "----- Profilo -----" + "\u001B[0m" );

            displayUserInfo(clientBean);
            // Show Menu //
            logger.info("1. Carica nuova Playlist");
            logger.info("2. Reimposta le tue preferenze musicali");
            logger.info("0. Esci");

            logger.info("Scegli una opzione: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addPlaylist();
                    break;
                case 2:
                    updatePreferences();
                    break;
                case 0:
                    return;
                default:
                    logger.info("Scelta non disponibile !");
                    break;
            }
        }
    }

    private void displayUserInfo(ClientBean clientBean) {
        if (clientBean != null) {
            logger.info("Username: " + clientBean.getUsername());
            logger.info("Email: " + clientBean.getEmail());

            if (clientBean.getPreferences() != null && !clientBean.getPreferences().isEmpty()) {
                logger.info("Generi preferiti: " + String.join(", ", clientBean.getPreferences()));
            } else {
                logger.info("Nessuna genere preferito impostato.");
            }

            logger.info("Le tue playlist:");
            displayUserPlaylists();
        }
    }

    private void displayUserPlaylists() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        List<PlaylistBean> userPlaylists = accountCtrlApplicativo.retrievePlaylists(clientBean);
        for (PlaylistBean playlist : userPlaylists) {
            String approvalStatus = playlist.getApproved() ? "Approved" : "In attesa";
            logger.info(approvalStatus + " - Titolo: "+ playlist.getPlaylistName() +", Link: "+playlist.getLink());
        }
    }

    private void updatePreferences() {
        // Utilizza la classe GenreManager per gestire gli aggiornamenti delle preferenze musicali
        GenreManager genreManager = new GenreManager();

        // Ottieni i generi musicali disponibili
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();

        // Stampa a schermo i generi musicali disponibili
        logger.info("Available Genres:");
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di inserire i numeri corrispondenti ai generi che preferisce
        logger.info("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");

        // Estrai i generi selezionati dall'utente
        String genreInput = scanner.next();
        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        // Aggiorna le preferenze nel bean del cliente (devi avere accesso al bean del cliente)
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Aggiorna le preferenze nel backend
        accountCtrlApplicativo.updateGenreUser(clientBean);
        logger.info("Preferenze aggiornate");
    }

    private void addPlaylist() {
        AddPlaylistCLI addPlaylistCLI = new AddPlaylistCLI();
        addPlaylistCLI.setClientBean(clientBean);
        addPlaylistCLI.start();
    }
}
