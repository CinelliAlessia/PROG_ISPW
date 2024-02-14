package view.second;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import java.util.List;
import java.util.Scanner;

public class ManagePlaylistsCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = pendingPlaylistCtrlApplicativo.retrievePlaylists();
        System.out.println("\n----- Gestisci Playlists -----");
        int index = 0;
        for (PlaylistBean playlist : playlistsPending) {
            index++;
            handlePlaylist(playlist,index);
        }
        System.out.println("--- Non ci sono più playlist da gestire ---");
    }
    /** // Espone playlist all'utente e attende risposta (accetta o rifiuta) prima di visualizzare la prossima playlist */
    private void handlePlaylist(PlaylistBean playlist, int index) {
        System.out.println(STR."---\{index}---");
        System.out.println(STR."Nome: \{playlist.getPlaylistName()}");
        System.out.println(STR."Creatore: \{playlist.getUsername()}");
        System.out.println(STR."Generi: \{playlist.getPlaylistGenre()}");
        System.out.println(STR."Link: \{playlist.getLink()}");


        System.out.println("\nOpzioni:");
        System.out.println("1. Accetta");
        System.out.println("2. Rifiuta");
        System.out.println("0. Esci");

        int choice = getChoice();

        switch (choice) {
            case 1:
                acceptPlaylist(playlist);
                break;
            case 2:
                rejectPlaylist(playlist);
                break;
            case 0:
                System.out.println("Uscita dalla gestione playlist in attesa.");
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                handlePlaylist(playlist,index);
                break;
        }
    }

    private int getChoice() {
        System.out.print("Inserisci la tua scelta: ");
        return scanner.nextInt();
    }

    private void acceptPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.approvePlaylist(playlist);

        System.out.println("Playlist approvata.");
    }

    private void rejectPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.rejectPlaylist(playlist);

        System.out.println("Playlist rifiutata.");
    }
}
