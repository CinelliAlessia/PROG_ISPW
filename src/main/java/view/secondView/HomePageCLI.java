package view.secondView;

import controller.applicativo.HomePageCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import engineering.bean.SupervisorBean;

import java.util.List;
import java.util.Scanner;

public class HomePageCLI<T extends ClientBean>{

    private final Scanner scanner = new Scanner(System.in);
    private T clientBean;
    public void setClientBean(T clientBean) {
        this.clientBean = clientBean;
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
                    exit = true;  // Imposta exit a true per uscire dal loop
                    break;
                case 7:
                    // Passa al menu approvazione playlist
                    // TODO completare
                case 0:
                    exit = true;  // Esci dal loop e dal programma
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void printMenu() {
        System.out.println(" ----- Benvenuto nella Home Page ----- ");
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
            System.out.println("6. visualizza il tuo profilo");
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
            System.err.flush();
            System.out.print("Inserisci il numero della playlist per ricevere il link (0 per tornare al menu): ");

            int playlistChoice = scanner.nextInt();

            if (playlistChoice > 0 && playlistChoice <= playlistBeans.size()) {
                PlaylistBean selectedPlaylist = playlistBeans.get(playlistChoice - 1);
                System.out.println(STR."Link: \{selectedPlaylist.getLink()}");
            } else if (playlistChoice == 0) {
                // Torna al menu principale
                break;
            } else {
                System.err.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void addPlaylist() {
        // Passa al controller grafico per aggiungere una playlist
        // Implementa questa parte in base alle tue esigenze
    }

    private void addFilter() {
        // Implementa la logica per applicare un filtro alle playlist
        // Potresti chiedere all'utente di selezionare i generi, l'approvazione, ecc.
        // Usa il controller applicativo per effettuare la ricerca con il filtro
        // e mostra i risultati all'utente
    }

    private void deleteFilter() {
        // Resetta la PlaylistFilter a null o come desideri gestire l'eliminazione dei filtri
        // e mostra un messaggio all'utente
        System.out.println("Filtri eliminati.");
    }

    private void searchPlaylist() {
        // Implementa la logica per cercare una playlist per nome
        // Usa il controller applicativo per effettuare la ricerca
        // e mostra i risultati all'utente
    }
}
