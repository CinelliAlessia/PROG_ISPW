package view.second;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;
import view.second.utils.GenreManager;

import java.util.*;
import java.util.logging.Logger;

public class RegistrationCLI {
    private static final Logger logger = Logger.getLogger(RegistrationCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        logger.info("// ------- Registrazione ------- //");
        logger.info("Nome utente: ");
        String username = scanner.next();

        logger.info("Email: ");
        String email = scanner.next();

        String password = null;
        String confirmPassword;
        boolean retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            logger.info("Password: ");
            password = scanner.next();

            logger.info("Conferma password: ");
            confirmPassword = scanner.next();

            if(!password.equals(confirmPassword)){
                logger.info(" ! Le password non coincidono -> Riprova !");
            } else {
                retry = false;
            }
        }

        // Richiedi generi musicali disponibili all'utente
        logger.info("Generi musicali disponibili:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        logger.info("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);

        try {
            LoginBean regBean = new LoginBean(username, email, password, preferences);
            RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

            // ----- Utilizzo controller applicativo -----
            UserBean userBean = new UserBean(email);
            registrazioneCtrlApp.registerUser(regBean, userBean);
            logger.info("Registrazione utente avvenuta con successo!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(userBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
            logger.info(String.format("! %s !", e.getMessage()));
        }
    }
}
