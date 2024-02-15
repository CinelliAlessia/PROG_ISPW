package view.second;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.*;
import view.second.utils.GenreManager;

import java.util.*;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per l'account utente.
 */
public class AccountCLI {

    private final Scanner scanner = new Scanner(System.in);
    private ClientBean clientBean;

    /**
     * Imposta il bean del cliente per l'interfaccia utente.
     *
     * @param clientBean Il bean del cliente da impostare.
     */
    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    /**
     * Avvia l'interfaccia a riga di comando per l'account utente.
     */
    public void start() {
        while (true) {
            System.out.println("\u001B[32m" + "----- Profilo -----" + "\u001B[0m");

            displayUserInfo(clientBean);

            // Mostra il menu
            System.out.println("1. Carica nuova Playlist");
            System.out.println("2. Reimposta le tue preferenze musicali");
            System.out.println("0. Esci");

            System.out.print("Scegli un'opzione: ");
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
                    System.out.println("Scelta non disponibile !");
                    break;
            }
        }
    }

    private void displayUserInfo(ClientBean clientBean) {
        if (clientBean != null) {
            System.out.println("Username: " + clientBean.getUsername());
            System.out.println("Email: " + clientBean.getEmail());

            if (clientBean.getPreferences() != null && !clientBean.getPreferences().isEmpty()) {
                System.out.println("Generi preferiti: " + String.join(", ", clientBean.getPreferences()));
            } else {
                System.out.println("Nessun genere preferito impostato.");
            }

            System.out.println("Le tue playlist:");
            displayUserPlaylists();
        }
    }

    private void displayUserPlaylists() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        List<PlaylistBean> userPlaylists = accountCtrlApplicativo.retrievePlaylists(clientBean);
        for (PlaylistBean playlist : userPlaylists) {
            String approvalStatus = playlist.getApproved() ? "Approved" : "In attesa";
            System.out.println(approvalStatus + " - Titolo: " + playlist.getPlaylistName() + ", Link: " + playlist.getLink());
        }
    }

    private void updatePreferences() {
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();

        System.out.println("Generi disponibili:");
        genreManager.printGenres(availableGenres);

        System.out.print("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        accountCtrlApplicativo.updateGenreUser(clientBean);
        System.out.println("Preferenze aggiornate");
    }

    private void addPlaylist() {
        AddPlaylistCLI addPlaylistCLI = new AddPlaylistCLI();
        addPlaylistCLI.setClientBean(clientBean);
        addPlaylistCLI.start();
    }
}
