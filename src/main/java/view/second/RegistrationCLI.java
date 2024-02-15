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
        RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

        logger.info("// ------- Registrazione ------- //");

        String username;
        String email = null;

        LoginBean regBean = new LoginBean();
        boolean retry = true;

        while (retry){
            try{
                logger.info("Nome utente: ");
                username = scanner.next();

                logger.info("Email: ");
                email = scanner.next();

                regBean.setEmail(email);
                regBean.setUsername(username);

                registrazioneCtrlApp.tryCredentialExisting(regBean);

                retry = false;

            } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e){
                logger.severe(e.getMessage());
            }
        }

        String password = null;
        String confirmPassword;
        retry = true;

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
        regBean.setPassword(password);

        // Richiedi generi musicali disponibili all'utente
        logger.info("Generi musicali disponibili:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        logger.info("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        regBean.setPreferences(preferences);

        try {
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
