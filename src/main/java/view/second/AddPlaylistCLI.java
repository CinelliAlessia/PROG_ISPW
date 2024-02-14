package view.second;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.PlaylistBean;
import engineering.exceptions.LinkIsNotValid;
import engineering.exceptions.PlaylistLinkAlreadyInUse;
import view.second.utils.GenreManager;
import java.util.*;
import java.util.logging.Logger;

public class AddPlaylistCLI {
    private static final Logger logger = Logger.getLogger(AddPlaylistCLI.class.getName());

    private ClientBean clientBean;
    private final Scanner scanner = new Scanner(System.in);

    public void setClientBean(ClientBean bean){
        this.clientBean = bean;
    }

    public void start() {
        PlaylistBean playlistBean = new PlaylistBean();

        playlistBean.setApproved(clientBean.isSupervisor());
        playlistBean.setUsername(clientBean.getUsername());
        playlistBean.setEmail(clientBean.getEmail());

        // Chiedi all'utente di inserire i dati della playlist
        logger.info("Inserisci il titolo della playlist: ");
        playlistBean.setPlaylistName(scanner.nextLine());

        boolean linkIsValid = false;
        while (!linkIsValid) {
            logger.info("Inserisci il link della playlist: ");
            try {
                playlistBean.setLink(scanner.nextLine());
                linkIsValid = true; // Se non viene lanciata l'eccezione, il link è valido e usciamo dal ciclo
            } catch (LinkIsNotValid e) {
                logger.info("! Link non valido-> Riprova !");
            }
        }

        // Richiedi all'utente di selezionare i generi musicali
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        logger.info("Inserisci i numeri corrispondenti ai generi musicali contenuti nella Playlist (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        playlistBean.setPlaylistGenre(preferences);

        // #################### Devo prendere le emotional da input utente #############################
        playlistBean.setEmotional(Arrays.asList(0, 0, 0, 0));

        try {
            // Invocazione del controller applicativo per inserire la playlist
            AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
            addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

            logger.info("Playlist aggiunta con successo!");
        } catch (PlaylistLinkAlreadyInUse e) {
            logger.info(" ! Il link relativo playlist è già presente nel sistema !");
        }
    }
}
