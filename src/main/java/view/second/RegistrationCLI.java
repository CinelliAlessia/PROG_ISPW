package view.second;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import engineering.exceptions.EmailAlreadyInUse;
import engineering.exceptions.InvalidEmailException;
import engineering.exceptions.UsernameAlreadyInUse;
import view.second.utils.GenreManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per la registrazione di un nuovo utente.
 */
public class RegistrationCLI {

    private static final Logger logger = Logger.getLogger(RegistrationCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Avvia l'interfaccia a riga di comando per la registrazione di un nuovo utente.
     */
    public void start() {
        RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

        System.out.println("// ------- Registrazione ------- //");

        String username;
        String email = null;

        LoginBean regBean = new LoginBean();
        boolean retry = true;

        while (retry) {
            try {
                System.out.println("Nome utente: ");
                username = scanner.next();

                System.out.println("Email: ");
                email = scanner.next();

                regBean.setEmail(email);
                regBean.setUsername(username);

                registrazioneCtrlApp.tryCredentialExisting(regBean);

                retry = false;

            } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
                logger.severe(e.getMessage());
            }
        }

        String password = null;
        String confirmPassword;
        retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            System.out.println("Password: ");
            password = scanner.next();

            System.out.println("Conferma password: ");
            confirmPassword = scanner.next();

            if (!password.equals(confirmPassword)) {
                System.out.println(" ! Le password non coincidono -> Riprova !");
            } else {
                retry = false;
            }
        }
        regBean.setPassword(password);

        // Richiedi generi musicali disponibili all'utente
        System.out.println("Generi musicali disponibili:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        System.out.println("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        regBean.setPreferences(preferences);

        try {
            // ----- Utilizzo controller applicativo -----
            UserBean userBean = new UserBean(email);
            registrazioneCtrlApp.registerUser(regBean, userBean);
            System.out.println("Registrazione utente avvenuta con successo!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(userBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
            System.out.println(String.format("! %s !", e.getMessage()));
        }
    }
}
