package view.second;

import controller.applicativo.HomePageCtrlApplicativo;
import engineering.bean.*;
import engineering.others.Printer;

import java.util.List;
import java.util.Scanner;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) della home page dell'applicazione.
 *
 * @param <T> Tipo del bean del cliente (UserBean, SupervisorBean, ecc.).
 */
public class HomePageCLI<T extends ClientBean> {

    private Scanner scanner = new Scanner(System.in);
    private PlaylistBean playlistBean = new PlaylistBean();
    private T clientBean;

    /**
     * Imposta il bean del cliente per l'interfaccia utente.
     *
     * @param clientBean Il bean del cliente da impostare.
     */
    public void setClientBean(T clientBean) {
        this.clientBean = clientBean;
    }

    /**
     * Imposta il bean della playlist per l'interfaccia utente.
     *
     * @param playlistBean Il bean della playlist da impostare.
     */
    public void setPlaylistBean(PlaylistBean playlistBean) {
        this.playlistBean = playlistBean;
    }

    /**
     * Avvia l'interfaccia a riga di comando della home page.
     */
    public void start() {
        scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenu();

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showAllPlaylists();
                    break;
                case 2:
                    // Devo differenziare user e supervisor da guest
                    addPlaylist();
                    break;
                case 3:
                    addFilter();
                    break;
                case 4:
                    deleteFilter();
                    break;
                case 5:
                    searchPlaylist();
                    break;
                case 6:
                    // Passa al menu del profilo utente
                    if (clientBean == null) {
                        // Vai alla registrazione
                        goToRegistration();
                        exit = true; // Esci dal loop e dal programma
                    } else {
                        account();
                    }
                    break;
                case 7:
                    // Passa al menu approvazione playlist
                    // Verifica per maggiore sicurezza
                    if (clientBean.isSupervisor()) {
                        goToApprovePlaylist();
                    }
                    break;
                case 0:
                    exit = true; // Esci dal loop e dal programma
                    break;
                default:
                    Printer.errorPrint("Scelta non valida. Riprova.");
            }
        }
    }

    /**
     * Passa al menu di gestione delle playlist in attesa di approvazione.
     */
    private void goToApprovePlaylist() {
        ManagePlaylistsCLI manager = new ManagePlaylistsCLI();
        manager.start();
    }

    /**
     * Passa al menu di registrazione utente.
     */
    private void goToRegistration() {
        RegistrationCLI registrationCLI = new RegistrationCLI();
        registrationCLI.start();
    }

    /**
     * Passa al menu dell'account utente.
     */
    private void account() {
        AccountCLI accountCLI = new AccountCLI();
        accountCLI.setClientBean(clientBean);
        accountCLI.start();
    }

    /**
     * Stampa il menu principale dell'interfaccia utente.
     */
    private void printMenu() {
        Printer.println("\n ----- Home Page ----- ");
        Printer.println("1. Visualizza tutte le playlist");
        if (clientBean != null) {
            Printer.println("2. Aggiungi una playlist");
        } else {
            Printer.println("2. !!! Non puoi aggiungere playlist -> Registrati!!!");
        }
        Printer.println("3. Applica un filtro di ricerca");
        Printer.println("4. Elimina il filtro di ricerca");
        Printer.println("5. Cerca una playlist per nome");
        if (clientBean != null) {
            Printer.println("6. Visualizza il tuo profilo");
        } else {
            Printer.println("6. !!! Non hai un profilo da visualizzare -> Registrati !!!");
        }

        if (clientBean != null && clientBean.isSupervisor()) {
            Printer.println("7. Gestisci playlists in attesa di approvazione");
        }
        Printer.println("0. Esci");
        Printer.println("Scegli un'opzione: ");
    }

    /**
     * Visualizza tutte le playlist approvate.
     */
    private void showAllPlaylists() {
        // Utilizza i metodi del controller applicativo per recuperare e stampare le playlist approvate
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        List<PlaylistBean> playlistBeans = homePageController.retrivePlaylistsApproved();

        int playlistNumber = 1;
        for (PlaylistBean playlist : playlistBeans) {
            Printer.println(String.format("%d. Titolo: %s Creatore: %s Generi della Playlist: %s",
                    playlistNumber, playlist.getPlaylistName(), playlist.getUsername(), playlist.getPlaylistGenre()));
            playlistNumber++;
        }

        while (true) {
            // Per copiare il link della playlist, l'utente deve inserire il numero corrispondente
            Printer.print("Inserisci il numero della playlist per ricevere il link (0 per tornare al menu): ");

            int playlistChoice = scanner.nextInt();

            if (playlistChoice > 0 && playlistChoice <= playlistBeans.size()) {
                PlaylistBean selectedPlaylist = playlistBeans.get(playlistChoice - 1);
                Printer.println(String.format("Link: %s", selectedPlaylist.getLink()));
            } else if (playlistChoice == 0) {
                // Torna al menu principale
                break;
            } else {
                Printer.errorPrint("! Scelta non valida ! -> Riprova");
            }
        }
    }

    /**
     * Aggiunge una nuova playlist.
     */
    private void addPlaylist() {
        AddPlaylistCLI addPlaylistCLI = new AddPlaylistCLI();
        addPlaylistCLI.setClientBean(clientBean);
        addPlaylistCLI.start();
    }

    /**
     * Applica un filtro di ricerca alle playlist.
     */
    private void addFilter() {
        // Implementa la logica per applicare un filtro alle playlist
        // Potresti chiedere all'utente di selezionare i generi, l'approvazione, ecc.
        // Usa il controller applicativo per effettuare la ricerca con il filtro
        // e mostra i risultati all'utente
        Printer.errorPrint("Non è ancora stato implementato.");
    }

    /**
     * Elimina i filtri di ricerca applicati alle playlist.
     */
    private void deleteFilter() {
        // Resetta la PlaylistFilter a null o come desideri gestire l'eliminazione dei filtri
        // e mostra un messaggio all'utente
        Printer.errorPrint("Non è ancora stato implementato.");
    }

    /**
     * Cerca una playlist per nome.
     */
    private void searchPlaylist() {
        Printer.print("Inserisci il nome della playlist da cercare: ");
        String playlistName = scanner.nextLine();

        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistBean.setPlaylistName(playlistName);
        List<PlaylistBean> playlistsList = homePageController.searchPlaylistByFilters(playlistBean);

        Printer.println(String.format("Playlist che contengono: \"%s\" nel titolo: ", playlistName));

        if (playlistsList.isEmpty()) {
            Printer.println("Nessuna playlist trovata con il nome specificato.");
        } else {
            int index = 1;
            for (PlaylistBean playlist : playlistsList) {
                Printer.println(String.format("%d. Nome: %s, Username: %s, Generi: %s, Emozionale: %s%n",
                        index, playlist.getPlaylistName(), playlist.getUsername(), playlist.getPlaylistGenre(), playlist.getEmotional()));
                index++;
            }

            int selectedPlaylistIndex;
            while (true) {
                Printer.print("Inserisci il numero corrispondente alla playlist desiderata (inserisci 0 per uscire): ");
                selectedPlaylistIndex = scanner.nextInt();

                if (selectedPlaylistIndex == 0) {
                    break; // Esce dal ciclo interno se l'utente inserisce 0
                }

                if (selectedPlaylistIndex >= 1 && selectedPlaylistIndex <= playlistsList.size()) {
                    PlaylistBean selectedPlaylist = playlistsList.get(selectedPlaylistIndex - 1);
                    Printer.println(String.format("Link della playlist selezionata: %s", selectedPlaylist.getLink()));
                } else {
                    Printer.errorPrint("! Selezione non valida-> Riprova !");
                }
            }
        }
    }
}
