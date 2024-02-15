package view.second;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import java.util.List;
import java.util.Scanner;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per la gestione delle playlist in attesa di approvazione.
 */
public class ManagePlaylistsCLI {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Avvia l'interfaccia a riga di comando per la gestione delle playlist in attesa.
     */
    public void start() {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = pendingPlaylistCtrlApplicativo.retrievePlaylists();
        System.out.println("\n----- Gestisci Playlists -----");
        int index = 0;
        for (PlaylistBean playlist : playlistsPending) {
            index++;
            handlePlaylist(playlist, index);
        }
        System.out.println("--- Non ci sono più playlist da gestire ---");
    }

    /**
     * Espone la playlist all'utente e attende la risposta (accetta o rifiuta) prima di visualizzare la prossima playlist.
     *
     * @param playlist La playlist da gestire.
     * @param index    L'indice della playlist.
     */
    private void handlePlaylist(PlaylistBean playlist, int index) {
        System.out.println("---" + index + "---");
        System.out.println("Nome: " + playlist.getPlaylistName());
        System.out.println("Creatore: " + playlist.getUsername());
        System.out.println("Generi: " + playlist.getPlaylistGenre());
        System.out.println("Link: " + playlist.getLink());

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
                handlePlaylist(playlist, index);
                break;
        }
    }

    /**
     * Ottiene la scelta dell'utente.
     *
     * @return La scelta dell'utente.
     */
    private int getChoice() {
        System.out.println("Inserisci la tua scelta: ");
        return scanner.nextInt();
    }

    /**
     * Accetta la playlist.
     *
     * @param playlist La playlist da accettare.
     */
    private void acceptPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.approvePlaylist(playlist);

        System.out.println("Playlist approvata.");
    }

    /**
     * Rifiuta la playlist.
     *
     * @param playlist La playlist da rifiutare.
     */
    private void rejectPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.rejectPlaylist(playlist);

        System.out.println("Playlist rifiutata.");
    }
}
