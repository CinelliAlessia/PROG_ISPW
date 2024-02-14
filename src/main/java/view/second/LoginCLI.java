package view.second;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoginCLI {

    private static final Logger logger = Logger.getLogger(LoginCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean continueRunning = true;

        while (continueRunning) {
            printMenu();
            int choice = getUserChoice(); // Attesa bloccante

            switch (choice) {
                case 1:
                    loginChoice();
                    break;
                case 2:
                    registerChoice();
                    break;
                case 3:
                    guestChoice();
                    break;
                case 0:
                    logger.info("Grazie per aver utilizzato l'applicazione. Arrivederci!");
                    continueRunning = false;  // Imposta la condizione di uscita
                    break;
                default:
                    logger.info("Scelta non valida. Riprova.");
            }
        }
    }

    /** Print del menù iniziale per procedere con l'accesso */
    private void printMenu() {
        logger.info("// ------- Seleziona un'opzione: ------- //");
        logger.info("1: Login");
        logger.info("2: Registrazione");
        logger.info("3: Ingresso come Guest");
        logger.info("0: Esci");
    }

    /** Attesa Bloccante della scelta dell'utente */
    private int getUserChoice() {
        logger.info("Scelta: ");
        while (!scanner.hasNextInt()) { // Controllo se il valore inserito dall'utente è un intero
            logger.info("! Inserisci un numero valido !");
            scanner.next(); // Consuma il valore errato non utilizzabile
        }
        return scanner.nextInt();
    }

    /** Funzione per accedere alla home page se le credenziali inserite sono corrette */
    private void loginChoice() {
        logger.info("Inserisci l'indirizzo email: ");
        String email = scanner.next();

        logger.info("Inserisci la password: ");
        String password = scanner.next();

        try {
            LoginBean loginBean = new LoginBean(email, password);
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();

            // ----- Utilizzo controller applicativo -----
            loginCtrlApp.verificaCredenziali(loginBean);

            /* ----- Verrà eseguito se non ci sono eccezioni ----- */
            ClientBean clientBean = loginCtrlApp.loadUser(loginBean);

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */

            if (clientBean instanceof UserBean userBean) {
                HomePageCLI<UserBean> homePageCLI = new HomePageCLI<>();
                homePageCLI.setClientBean(userBean);
                /* ----- Avvia il metodo start del HomePageCLInterface ----- */
                homePageCLI.start();
            } else if (clientBean instanceof SupervisorBean supervisorBean) {
                HomePageCLI<SupervisorBean> homePageCLI = new HomePageCLI<>();
                homePageCLI.setClientBean(supervisorBean);
                /* ----- Avvia il metodo start del HomePageCLInterface ----- */
                homePageCLI.start();
            }

        } catch (IncorrectPassword | UserDoesNotExist | InvalidEmailException e) {
            logger.info(String.format("! %s", e.getMessage()));
        }
    }

    /** Non vengono effettuati controlli, si passa direttamente alla schermata di registrazione */
    private void registerChoice() {
        // ----- Passo al RegisterCLInterface -----
        RegistrationCLI newCLI = new RegistrationCLI();
        newCLI.start();
    }

    /** Non vengono effettuati controlli, si passa direttamente alla home page */
    private void guestChoice() {
        logger.info("Accesso come Guest.");

        //UserBean perché un guest non è sicuramente Supervisor
        HomePageCLI<UserBean> homePageCLI = new HomePageCLI<>();
        homePageCLI.setClientBean(null);
        homePageCLI.start();
    }
}
