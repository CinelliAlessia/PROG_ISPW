package view.second;

import controller.applicativo.AccountCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import view.second.utils.CLIPrinter;
import view.second.utils.GenreManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
            CLIPrinter.println("----- Profilo -----");

            displayUserInfo(clientBean);

            // Mostra il menu
            CLIPrinter.println("1. Carica nuova Playlist");
            CLIPrinter.println("2. Reimposta le tue preferenze musicali");
            CLIPrinter.println("0. Esci");

            CLIPrinter.print("Scegli un'opzione: ");

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
                    CLIPrinter.errorPrint("Scelta non disponibile !");
                    break;
            }
        }
    }

    private void displayUserInfo(ClientBean clientBean) {
        if (clientBean != null) {
            CLIPrinter.println(String.format("Username: %s", clientBean.getUsername()));
            CLIPrinter.println(String.format("Email: %s", clientBean.getEmail()));

            if (clientBean.getPreferences() != null && !clientBean.getPreferences().isEmpty()) {
                CLIPrinter.println(String.format("Generi preferiti: %s", String.join(", ", clientBean.getPreferences())));
            } else {
                CLIPrinter.println("Nessun genere preferito impostato.");
            }

            CLIPrinter.println("Le tue playlist:");
            displayUserPlaylists();
        }
    }

    private void displayUserPlaylists() {
        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        List<PlaylistBean> userPlaylists = accountCtrlApplicativo.retrievePlaylists(clientBean);
        for (PlaylistBean playlist : userPlaylists) {
            String approvalStatus = playlist.getApproved() ? "Approved" : "In attesa";
            CLIPrinter.println(String.format(("%s - Titolo: %s, Link: %s"), approvalStatus, playlist.getPlaylistName(), playlist.getLink()));
        }
    }

    private void updatePreferences() {
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();

        CLIPrinter.println("Generi disponibili:");
        genreManager.printGenres(availableGenres);

        CLIPrinter.print("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        clientBean.setPreferences(preferences);

        AccountCtrlApplicativo accountCtrlApplicativo = new AccountCtrlApplicativo();
        accountCtrlApplicativo.updateGenreUser(clientBean);
        CLIPrinter.println("Preferenze aggiornate");
    }

    private void addPlaylist() {
        AddPlaylistCLI addPlaylistCLI = new AddPlaylistCLI();
        addPlaylistCLI.setClientBean(clientBean);
        addPlaylistCLI.start();
    }
}
