package view.secondView;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;
import view.secondView.utils.GenreManager;
import view.secondView.utils.StringCLI;

import java.io.*;
import java.lang.reflect.GenericArrayType;
import java.util.*;

// In questa interfaccia noto che il modo in cui è stato costruito il controller applicativo,
// mi rende impossibile invocare metodi diversi da loadUser
// questo rende complicata la gestione di questa interfaccia, che essendo diversa ha bisogno di ulteriori controlli,
// che purtroppo vengono effettuati solo quando si prova a inserire l'utente.
// Esempio: non posso verificare se la password è già in uso immediatamente, perché non posseggo il metodo sull'applicativo: searchEmail
public class RegistrationCLI {
    private final String genreListFile = StringCLI.GENERES_FILE_PATH;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("// ------- Registrazione ------- //");
        System.out.print("Nome utente: ");
        String username = scanner.next();

        System.out.print("Email: ");
        String email = scanner.next();

        String password = null;
        String confirmPassword;
        boolean retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            System.out.print("Password: ");
            password = scanner.next();

            System.out.print("Conferma password: ");
            confirmPassword = scanner.next();

            if(!password.equals(confirmPassword)){
                System.out.println(" ! Le password non coincidono -> Riprova !");
            } else {
                retry = false;
            }
        }

        // Richiedi generi musicali disponibili all'utente
        System.out.println("Generi musicali disponibili:");
        GenreManager genreManager = new GenreManager();
        Map<Integer, String> availableGenres = genreManager.getAvailableGenres();
        genreManager.printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        System.out.print("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = genreManager.extractGenres(availableGenres, genreInput);

        try {
            LoginBean regBean = new LoginBean(username, email, password, preferences);
            RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

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
            System.out.println(STR."! \{e.getMessage()} !");
        }
    }

}

