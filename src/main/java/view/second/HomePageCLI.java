package view.second;

import controller.applicativo.HomePageCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import engineering.bean.SupervisorBean;

import java.util.List;
import java.util.Scanner;

public class HomePageCLI<T extends ClientBean>{

    private final Scanner scanner = new Scanner(System.in);
    private PlaylistBean playlistBean = new PlaylistBean();
    private T clientBean;
    public void setClientBean(T clientBean) {
        this.clientBean = clientBean;
    }

    public void setPlaylistBean(PlaylistBean playlistBean){
        this.playlistBean = playlistBean;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
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
                    if(clientBean == null) {
                        // Vai alla registrazione
                        goToRegistration();
                        exit = true;  // Esci dal loop e dal programma
                    }
                    else{
                        account();
                    }
                    break;
                case 7:
                    // Passa al menu approvazione playlist
                    // Verifica per maggiore sicurezza
                    if(clientBean instanceof SupervisorBean) {
                        goToApprovePlaylist();
                    }
                    break;
                case 0:
                    exit = true;  // Esci dal loop e dal programma
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void goToApprovePlaylist() {
        ManagePlaylistsCLI manager = new ManagePlaylistsCLI();
        manager.start();
    }

    private void goToRegistration() {
        RegistrationCLI registrationCLI = new RegistrationCLI();
        registrationCLI.start();
    }

    private void account() {
        AccountCLI accountCLI = new AccountCLI();
        accountCLI.setClientBean(clientBean);
        accountCLI.start();
    }

    private void printMenu() {
        System.out.println(" ----- Home Page ----- ");
        System.out.println("1. Visualizza tutte le playlist");
        if (clientBean != null) {
            System.out.println("2. Aggiungi una playlist");
        } else {
            System.out.println("2. !!! Non puoi aggiungere playlist -> Registrati!!!");
        }
        System.out.println("3. Applica un filtro di ricerca");
        System.out.println("4. Elimina il filtro di ricerca");
        System.out.println("5. Cerca una playlist per nome");
        if (clientBean != null) {
            System.out.println("6. Visualizza il tuo profilo");
        } else {
            System.out.println("6. !!! Non hai un profilo da visualizzare -> Registrati !!!");
        }

        if(clientBean instanceof SupervisorBean){
            System.out.println("7. Gestisci playlists in attesa di approvazione");
        }
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }

    private void showAllPlaylists() {
        // Utilizza i metodi del controller applicativo per recuperare e stampare le playlist approvate
        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        List<PlaylistBean> playlistBeans = homePageController.retrivePlaylistsApproved();

        int playlistNumber = 1;
        for (PlaylistBean playlist : playlistBeans) {
            System.out.println(STR."\{playlistNumber}. Titolo:\{playlist.getPlaylistName()} Creatore:\{playlist.getUsername()} \{playlist.getPlaylistGenre()}");
            playlistNumber++;
        }

        while (true) {
            // Per copiare il link della playlist, l'utente deve inserire il numero corrispondente

            System.out.print("Inserisci il numero della playlist per ricevere il link (0 per tornare al menu): ");

            int playlistChoice = scanner.nextInt();

            if (playlistChoice > 0 && playlistChoice <= playlistBeans.size()) {
                PlaylistBean selectedPlaylist = playlistBeans.get(playlistChoice - 1);
                System.out.println(STR."Link: \{selectedPlaylist.getLink()}");
            } else if (playlistChoice == 0) {
                // Torna al menu principale
                break;
            } else {
                System.out.println("! Scelta non valida ! -> Riprova");
            }
        }
    }

    private void addPlaylist() {
        AddPlaylistCLI addPlaylistCLI = new AddPlaylistCLI();
        addPlaylistCLI.setClientBean(clientBean);
        addPlaylistCLI.start();
    }

    private void addFilter() {
        // Implementa la logica per applicare un filtro alle playlist
        // Potresti chiedere all'utente di selezionare i generi, l'approvazione, ecc.
        // Usa il controller applicativo per effettuare la ricerca con il filtro
        // e mostra i risultati all'utente
        //TODO
    }

    private void deleteFilter() {
        // Resetta la PlaylistFilter a null o come desideri gestire l'eliminazione dei filtri
        // e mostra un messaggio all'utente
        System.out.println("Filtri eliminati.");
    }

    private void searchPlaylist() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nInserisci il nome della playlist da cercare: ");
        String playlistName = scanner.nextLine();

        HomePageCtrlApplicativo homePageController = new HomePageCtrlApplicativo();
        playlistBean.setPlaylistName(playlistName);
        List<PlaylistBean> playlistsList = homePageController.searchPlaylistByFilters(playlistBean);

        System.out.println(STR."Playlist che contengono:\{playlistName} nel titolo");

        if (playlistsList.isEmpty()) {
            System.out.println("Nessuna playlist trovata con il nome specificato.");
        } else {
            int index = 1;
            for (PlaylistBean playlist : playlistsList) {
                System.out.println(STR."\{index}. Nome: \{playlist.getPlaylistName()}, Username: \{playlist.getUsername()}, Generi: \{playlist.getPlaylistGenre()}, Emozionale: \{playlist.getEmotional()}");
                index++;
            }

            int selectedPlaylistIndex;
            while (true) {
                System.out.print("Inserisci il numero corrispondente alla playlist desiderata (inserisci 0 per uscire): ");
                selectedPlaylistIndex = scanner.nextInt();

                if (selectedPlaylistIndex == 0) {
                    break; // Esce dal ciclo interno se l'utente inserisce 0
                }

                if (selectedPlaylistIndex >= 1 && selectedPlaylistIndex <= playlistsList.size()) {
                    PlaylistBean selectedPlaylist = playlistsList.get(selectedPlaylistIndex - 1);
                    System.out.println(STR."Link della playlist selezionata: \{selectedPlaylist.getLink()}");
                } else {
                    System.out.println(" ! Selezione non valida-> Riprova !");
                }
            }
        }
    }
}
