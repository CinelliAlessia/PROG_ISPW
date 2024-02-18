package view.second;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;
import engineering.others.Printer;
import view.second.utils.*;

import java.util.*;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per la registrazione di un nuovo utente.
 */
public class RegistrationCLI {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Avvia l'interfaccia a riga di comando per la registrazione di un nuovo utente.
     */
    public void start() {
        RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

        Printer.println("\n// ------- Registrazione ------- //");

        String username;
        String email = null;

        LoginBean regBean = new LoginBean();
        boolean retry = true;

        while (retry) {
            try {
                Printer.print("Nome utente: ");
                username = scanner.next();

                Printer.print("Email: ");
                email = scanner.next();

                regBean.setEmail(email);
                regBean.setUsername(username);

                registrazioneCtrlApp.tryCredentialExisting(regBean);

                retry = false;

            } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
                Printer.errorPrint(e.getMessage());
            }
        }

        String password = null;
        String confirmPassword;
        retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            Printer.print("Password: ");
            password = scanner.next();

            Printer.print("Conferma password: ");
            confirmPassword = scanner.next();

            if (!password.equals(confirmPassword)) {
                Printer.errorPrint(" ! Le password non coincidono -> Riprova !");
            } else {
                retry = false;
            }
        }
        regBean.setPassword(password);

        // Richiedi generi musicali disponibili all'utente
        Printer.println("Generi musicali preferiti:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        Printer.print("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        regBean.setPreferences(preferences);

        try {
            // ----- Utilizzo controller applicativo -----
            UserBean userBean = new UserBean(email);
            registrazioneCtrlApp.registerUser(regBean, userBean);
            Printer.println("Registrazione utente avvenuta con successo!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(userBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
            Printer.errorPrint(String.format("! %s !", e.getMessage()));
        }
    }
}
