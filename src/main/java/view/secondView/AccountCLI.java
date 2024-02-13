package view.secondView;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.UserBean;
import engineering.bean.PlaylistBean;
import engineering.exceptions.InvalidEmailException;
import engineering.exceptions.LinkIsNotValid;
import engineering.exceptions.UsernameAlreadyInUse;
import view.secondView.utils.GenreManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccountCLI {
    private final Scanner scanner = new Scanner(System.in);
    private PlaylistBean playlistBean = new PlaylistBean();
    private ClientBean clientBean;

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    public void setPlaylistBean(PlaylistBean playlistBean) {
        this.playlistBean = playlistBean;
    }

    public void start() {
        while (true) {
            System.out.println("----- Profilo -----");
            displayUserInfo(clientBean);
            // Show Menu //
            System.out.println("1. Carica nuova Playlist");
            System.out.println("2. Reimposta le tue preferenze musicali");
            System.out.println("0. Esci");

            System.out.print("Scegli una opzione: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:

                    break;
                case 2:
                    updatePreferences();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Scelta non disponibile !.");
                    break;
            }
        }
    }

    private void displayUserInfo(ClientBean clientBean) {
        if (clientBean != null) {
            System.out.println(STR."Username: \{clientBean.getUsername()}");
            System.out.println(STR."Email: \{clientBean.getEmail()}");

            if (clientBean.getPreferences() != null && !clientBean.getPreferences().isEmpty()) {
                System.out.println(STR."Generi preferiti: \{String.join(", ", clientBean.getPreferences())}");
            } else {
                System.out.println("Nessuna genere preferito impostato.");
            }

            System.out.println("Le tue playlist:");
            displayUserPlaylists();
        }
    }

    private void displayUserPlaylists() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        List<PlaylistBean> userPlaylists = accountCtrlApplicativo.retrivePlaylists(clientBean);
        for (PlaylistBean playlist : userPlaylists) {
            String approvalStatus = playlist.getApproved() ? "Approved" : "In attesa";
            System.out.printf("%s - Playlist: %s, Link: %s%n",
                    approvalStatus, playlist.getPlaylistName(), playlist.getLink());
        }
    }

    private void updatePreferences() {
        // Utilizza la classe GenreManager per gestire gli aggiornamenti delle preferenze musicali
        GenreManager genreManager = new GenreManager();

        // Ottieni i generi musicali disponibili
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();

        // Stampa a schermo i generi musicali disponibili
        System.out.println("Available Genres:");
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di inserire i numeri corrispondenti ai generi che preferisce
        System.out.println("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");

        // Estrai i generi selezionati dall'utente
        String genreInput = scanner.next();
        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        // Aggiorna le preferenze nel bean del cliente (devi avere accesso al bean del cliente)
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        // Aggiorna le preferenze nel backend
        accountCtrlApplicativo.updateGenreUser(clientBean);
        System.out.println("Preferenze aggiornate");
    }

}
