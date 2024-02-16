package view.second;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;
import engineering.others.CLIPrinter;
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

        CLIPrinter.println("\n// ------- Registrazione ------- //");

        String username;
        String email = null;

        LoginBean regBean = new LoginBean();
        boolean retry = true;

        while (retry) {
            try {
                CLIPrinter.print("Nome utente: ");
                username = scanner.next();

                CLIPrinter.print("Email: ");
                email = scanner.next();

                regBean.setEmail(email);
                regBean.setUsername(username);

                registrazioneCtrlApp.tryCredentialExisting(regBean);

                retry = false;

            } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
                CLIPrinter.errorPrint(e.getMessage());
            }
        }

        String password = null;
        String confirmPassword;
        retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            CLIPrinter.print("Password: ");
            password = scanner.next();

            CLIPrinter.print("Conferma password: ");
            confirmPassword = scanner.next();

            if (!password.equals(confirmPassword)) {
                CLIPrinter.errorPrint(" ! Le password non coincidono -> Riprova !");
            } else {
                retry = false;
            }
        }
        regBean.setPassword(password);

        // Richiedi generi musicali disponibili all'utente
        CLIPrinter.println("Generi musicali disponibili:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        CLIPrinter.print("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);
        regBean.setPreferences(preferences);

        try {
            // ----- Utilizzo controller applicativo -----
            UserBean userBean = new UserBean(email);
            registrazioneCtrlApp.registerUser(regBean, userBean);
            CLIPrinter.println("Registrazione utente avvenuta con successo!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(userBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
            CLIPrinter.errorPrint(String.format("! %s !", e.getMessage()));
        }
    }
}
