package view.second;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;
import engineering.others.CLIPrinter;
import view.second.utils.GenreManager;

import java.util.*;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per l'aggiunta di una playlist.
 */
public class AddPlaylistCLI {
    private ClientBean clientBean;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Imposta il bean del cliente per l'interfaccia utente.
     *
     * @param bean Il bean del cliente da impostare.
     */
    public void setClientBean(ClientBean bean){
        this.clientBean = bean;
    }

    /**
     * Avvia l'interfaccia a riga di comando per l'aggiunta di una playlist.
     */
    public void start() {
        PlaylistBean playlistBean = new PlaylistBean();

        playlistBean.setApproved(clientBean.isSupervisor());
        playlistBean.setUsername(clientBean.getUsername());
        playlistBean.setEmail(clientBean.getEmail());

        // Chiedi all'utente di inserire i dati della playlist
        CLIPrinter.print("Inserisci il titolo della playlist: ");
        playlistBean.setPlaylistName(scanner.nextLine());

        boolean linkIsValid = false;
        while (!linkIsValid) {
            CLIPrinter.print("Inserisci il link della playlist: ");
            try {
                playlistBean.setLink(scanner.nextLine());
                linkIsValid = true; // Se non viene lanciata l'eccezione, il link è valido e usciamo dal ciclo
            } catch (LinkIsNotValid e) {
                CLIPrinter.errorPrint("! Link non valido-> Riprova !");
            }
        }

        // Richiedi all'utente di selezionare i generi musicali
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        CLIPrinter.print("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        playlistBean.setPlaylistGenre(preferences);

        // #################### Devo prendere le emotional da input utente #############################
        playlistBean.setEmotional(Arrays.asList(0, 0, 0, 0));

        try {
            // Invocazione del controller applicativo per inserire la playlist
            AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
            addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

            CLIPrinter.println("Playlist aggiunta con successo!");
        } catch (PlaylistLinkAlreadyInUse e) {
            CLIPrinter.errorPrint(" ! Il link relativo playlist è già presente nel sistema !");
        }
    }
}
