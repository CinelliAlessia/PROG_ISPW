package view.second;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;
import engineering.others.Printer;

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
        Printer.println("\n----- Gestisci Playlists -----");
        int index = 0;
        for (PlaylistBean playlist : playlistsPending) {
            index++;
            handlePlaylist(playlist, index);
        }
        Printer.println("--- Non ci sono più playlist da gestire ---");
    }

    /**
     * Espone la playlist all'utente e attende la risposta (accetta o rifiuta) prima di visualizzare la prossima playlist.
     *
     * @param playlist La playlist da gestire.
     * @param index    L'indice della playlist.
     */
    private void handlePlaylist(PlaylistBean playlist, int index) {
        Printer.println("---" + index + "---");
        Printer.println("Nome: " + playlist.getPlaylistName());
        Printer.println("Creatore: " + playlist.getUsername());
        Printer.println("Generi: " + playlist.getPlaylistGenre());
        Printer.println("Link: " + playlist.getLink());

        Printer.println("\nOpzioni:");
        Printer.println("1. Accetta");
        Printer.println("2. Rifiuta");
        Printer.println("0. Esci");

        int choice = getChoice();

        switch (choice) {
            case 1:
                acceptPlaylist(playlist);
                break;
            case 2:
                rejectPlaylist(playlist);
                break;
            case 0:
                Printer.println("Uscita dalla gestione playlist in attesa.");
                break;
            default:
                Printer.errorPrint("Scelta non valida. Riprova.");
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
        Printer.print("Inserisci la tua scelta: ");
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

        Printer.println("Playlist approvata.");
    }

    /**
     * Rifiuta la playlist.
     *
     * @param playlist La playlist da rifiutare.
     */
    private void rejectPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.rejectPlaylist(playlist);

        Printer.println("Playlist rifiutata.");
    }
}
