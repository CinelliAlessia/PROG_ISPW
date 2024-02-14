package view.second;

import controller.applicativo.PendingPlaylistCtrlApplicativo;
import engineering.bean.PlaylistBean;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ManagePlaylistsCLI {

    private static final Logger logger = Logger.getLogger(ManagePlaylistsCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        List<PlaylistBean> playlistsPending = pendingPlaylistCtrlApplicativo.retrievePlaylists();
        logger.info("\n----- Gestisci Playlists -----");
        int index = 0;
        for (PlaylistBean playlist : playlistsPending) {
            index++;
            handlePlaylist(playlist, index);
        }
        logger.info("--- Non ci sono più playlist da gestire ---");
    }

    /** Espone playlist all'utente e attende risposta (accetta o rifiuta) prima di visualizzare la prossima playlist */
    private void handlePlaylist(PlaylistBean playlist, int index) {
        logger.info("---"+ index +"---");
        logger.info("Nome: " + playlist.getPlaylistName());
        logger.info("Creatore: " + playlist.getUsername());
        logger.info("Generi: "+ playlist.getPlaylistGenre());
        logger.info("Link: "+ playlist.getLink());

        logger.info("\nOpzioni:");
        logger.info("1. Accetta");
        logger.info("2. Rifiuta");
        logger.info("0. Esci");

        int choice = getChoice();

        switch (choice) {
            case 1:
                acceptPlaylist(playlist);
                break;
            case 2:
                rejectPlaylist(playlist);
                break;
            case 0:
                logger.info("Uscita dalla gestione playlist in attesa.");
                break;
            default:
                logger.info("Scelta non valida. Riprova.");
                handlePlaylist(playlist, index);
                break;
        }
    }

    private int getChoice() {
        logger.info("Inserisci la tua scelta: ");
        return scanner.nextInt();
    }

    private void acceptPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.approvePlaylist(playlist);

        logger.info("Playlist approvata.");
    }

    private void rejectPlaylist(PlaylistBean playlist) {
        PendingPlaylistCtrlApplicativo pendingPlaylistCtrlApplicativo = new PendingPlaylistCtrlApplicativo();
        pendingPlaylistCtrlApplicativo.rejectPlaylist(playlist);

        logger.info("Playlist rifiutata.");
    }
}
